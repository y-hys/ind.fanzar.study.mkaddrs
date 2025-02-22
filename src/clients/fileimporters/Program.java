package clients.fileimporters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import domains.parsers.FileParsable;
import domains.parsers.FileParserException;
import domains.databases.DatabaseManageable;
import domains.databases.DatabaseManagerException;
import domains.masters.CityMaster;
import domains.masters.PrefMaster;
import domains.masters.TownMaster;

// ファイルを読み込んでデータベースに書き込むクラス
public class Program {
    private final FileParsable parser;
    
    private final DatabaseManageable dbManager;

    private Path prefFile = null;

    private Path cityFile = null;

    private Path townFile = null;

    public static void main(String[] args) throws Exception {
        try {
            if (args.length < 3) {
                throw new ProgramException("実行時の引数が不足しています。");
            }
            var prefFile = Paths.get(args[0]);
            var cityFile = Paths.get(args[1]);
            var townFile = Paths.get(args[2]);

            var parser = ServiceLoader.load(FileParsable.class)
                .findFirst()
                .orElseThrow(() -> new ProgramException("FileParsable の実装が見つかりません。"));
            
            var dbManager = ServiceLoader.load(DatabaseManageable.class)
                .findFirst()
                .orElseThrow(() -> new ProgramException("DatabaseManageable の実装が見つかりません。"));
            
            new Program.Builder(parser, dbManager)
                .prefFile(prefFile)
                .cityFile(cityFile)
                .townFile(townFile)
                .build()
                .run();
            
        } catch (Exception e) {

            // デバッグ時にスタックトレースを表示する
            throw e;
            
            // 本番時こちらを有効にする
            //System.err.println(e);
            //System.exit(1);
        }
    }

    public void run() 
        throws SQLException,
        InterruptedException,
        ExecutionException,
        ProgramException, 
        FileParserException,
        DatabaseManagerException {
        
        Collection<PrefMaster> prefMasters = null;
        Collection<CityMaster> cityMasters = null;
        Collection<TownMaster> townMasters = null;
        var executor = Executors.newCachedThreadPool();
        try {
            var future1 = executor.submit(() -> {
                if (this.prefFile != null) {
                    return parser.parsePrefFile(this.prefFile);
                } else {
                    return null;
                }
            });
            var future2 = executor.submit(() -> {
                if (this.cityFile != null) {
                    return parser.parseCityFile(this.cityFile);
                } else {
                    return null;
                }
            });
            var future3 = executor.submit(() -> {
                if (this.townFile != null) {
                    return parser.parseTownFile(this.townFile);
                } else {
                    return null;
                }
            });
            prefMasters = future1.get();
            cityMasters = future2.get();
            townMasters = future3.get();
        } catch (Exception e) {
            throw e;
        } finally {
            executor.shutdownNow();
        }

        try (var connection = dbManager.getConnection()) {
            try {
                connection.setAutoCommit(false);
                if (prefMasters != null) {
                    dbManager.clearPrefMasters(connection);
                    dbManager.createPrefMasters(connection, prefMasters);
                }
                if (cityMasters != null) {
                    dbManager.clearCityMasters(connection);
                    dbManager.createCityMasters(connection, cityMasters);
                }
                if (townMasters != null) {
                    dbManager.clearTownMasters(connection);
                    dbManager.createTownMasters(connection, townMasters);
                }
                connection.commit();

            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
    
    public static class Builder {
        private final FileParsable parser;

        private final DatabaseManageable dbManager;

        private Path prefFile;
        public Builder prefFile(Path file) throws IOException, ProgramException {
            // 問題ないパスかを検証する。
            var validPath = validatePath(file);

            // ファイルサイズの確認
            if (Files.size(validPath) > 4500) {
                var message = String.format("ファイルサイズが大きすぎます。(%s)", validPath);
                throw new ProgramException(message);
            }

            // 同じファイルを指定していないかを確認する
            if (this.cityFile != null && Files.isSameFile(validPath, this.cityFile)) {
                var message = String.format("'%s' は設定済みの市区町村ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }
            if (this.townFile != null && Files.isSameFile(validPath, this.townFile)) {
                var message = String.format("'%s' は設定済みの町字ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }

            this.prefFile = validPath;
            return this;
        }

        private Path cityFile;
        public Builder cityFile(Path file) throws IOException, ProgramException {
            // 問題ないパスかを検証する。
            var validPath = validatePath(file);

            // ファイルサイズの確認
            if (Files.size(validPath) > 348000) {
                var message = String.format("ファイルサイズが大きすぎます。(%s)", validPath);
                throw new ProgramException(message);
            }

            // 同じファイルを指定していないかを確認する
            if (this.prefFile != null && Files.isSameFile(validPath, this.prefFile)) {
                var message = String.format("'%s' は設定済みの都道府県ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }
            if (this.townFile != null && Files.isSameFile(validPath, this.townFile)) {
                var message = String.format("'%s' は設定済みの町字ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }

            this.cityFile = validPath;
            return this;
        }

        private Path townFile;
        public Builder townFile(Path file) throws IOException, ProgramException {
            // 問題ないパスかを検証する。
            var validPath = validatePath(file);

            // ファイルサイズの確認
            if (Files.size(validPath) > 233952000) {
                var message = String.format("ファイルサイズが大きすぎます。(%s)", validPath);
                throw new ProgramException(message);
            }

            // 同じファイルを指定していないかを確認する
            if (this.prefFile != null && Files.isSameFile(validPath, this.prefFile)) {
                var message = String.format("'%s' は設定済みの都道府県ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }
            if (this.cityFile != null && Files.isSameFile(validPath, this.cityFile)) {
                var message = String.format("'%s' は設定済みの市区町村ファイルと同じです。", validPath);
                throw new ProgramException(message);
            }

            this.townFile = validPath;
            return this;
        }
        
        private Path validatePath(Path path) throws IOException, ProgramException {
            // LinkOption.NOFOLLOW_LINKS を指定してリンクをたどらないようにする
            // また、入力されたパスを標準のパスオブジェクトに変換する
            var realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
            
            if (Files.isDirectory(realPath)) {
                throw new ProgramException("指定されたパスはディレクトリです。");
            }
            
            // ユーザーホームディレクトリ以下のファイルであることを確認する。
            // このメソッドでは、入力パスがユーザーホームディレクトリ以下のファイルであることを確認している。
            // その為、ユーザーホームのシステムプロパティを変更されると、このメソッドは正しく動作しなくなる。
            var homePath = Paths.get(System.getProperty("user.home"));
            if (!realPath.startsWith(homePath)) {
                throw new ProgramException("指定されたパスはユーザーホームディレクトリ以下のファイルではありません。");
            }

            return realPath;
        }

        public Program build() {
            var program      = new Program(this.parser, this.dbManager);
            program.prefFile = this.prefFile;
            program.cityFile = this.cityFile;
            program.townFile = this.townFile;
            return program;
        }
        
        public Builder(FileParsable parser, DatabaseManageable dbManager) {
            this.parser    = parser;
            this.dbManager = dbManager;
        }
    }

    private Program(FileParsable parser, DatabaseManageable dbManager) {
        this.parser    = parser;
        this.dbManager = dbManager;
    }
}
