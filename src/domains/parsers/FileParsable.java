package domains.parsers;

import java.nio.file.Path;
import java.util.Collection;

import domains.masters.CityMaster;
import domains.masters.PrefMaster;
import domains.masters.TownMaster;

// このインターフェースは、ファイルパースサービスに実装する各種パースメソッドを定義します。
public interface FileParsable {
    // 実装クラスでは、入力されたファイルパスのファイルを読み込み、
    // 都道府県マスターデータを生成するメソッドを実装してください。
    Collection<PrefMaster> parsePrefFile(Path path) throws FileParserException;

    // 実装クラスでは、入力されたファイルパスのファイルを読み込み、
    // 市区町村マスターデータを生成するメソッドを実装してください。
    Collection<CityMaster> parseCityFile(Path path) throws FileParserException;

    // 実装クラスでは、入力されたファイルパスのファイルを読み込み、
    // 町字マスターデータを生成するメソッドを実装してください。
    Collection<TownMaster> parseTownFile(Path path) throws FileParserException;
}
