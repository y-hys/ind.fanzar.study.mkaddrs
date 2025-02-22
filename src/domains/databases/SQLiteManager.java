package domains.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// このクラスは、SQLiteを使用した住所データベース管理サービスを提供します。
public final class SQLiteManager extends DatabaseManager {
    @Override
    public Connection getConnection() throws DatabaseManagerException {
        try {
            var url = "jdbc:sqlite:mkaddrs.db";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
}
