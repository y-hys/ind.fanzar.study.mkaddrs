package domains.databases;

import java.sql.Connection;
import java.util.Collection;

import domains.masters.CityMaster;
import domains.masters.PrefMaster;
import domains.masters.TownMaster;

// このインターフェースは、データベース管理サービスに実装する各種データベース操作メソッドを定義します。
public interface DatabaseManageable {

    // 実装クラスでは、JDBC接続を取得するメソッドを実装してください。
    Connection getConnection() throws DatabaseManagerException;

    // INSERT 
    // 実装クラスでは、入力された都道府県マスターデータと接続を使用して、
    // マスターデータをと都道府県テーブルへ新規作成するメソッドを実装してください。
    void createPrefMasters(Connection connection, Collection<PrefMaster> masters) throws DatabaseManagerException;

    // INSERT
    // 実装クラスでは、入力された市区町村マスターデータと接続を使用して、
    // マスターデータを市区町村テーブルへ新規作成するメソッドを実装してください。
    void createCityMasters(Connection connection, Collection<CityMaster> masters) throws DatabaseManagerException;

    // INSERT
    // 実装クラスでは、入力された町字マスターデータと接続を使用して、
    // マスターデータを町字テーブルへ新規作成するメソッドを実装してください。
    void createTownMasters(Connection connection, Collection<TownMaster> masters) throws DatabaseManagerException;

    // DELETE
    // 実装クラスでは、入力された接続を使用して、
    // 都道府県テーブルの全件削除を行うメソッドを実装してください。
    void clearPrefMasters(Connection connection) throws DatabaseManagerException;

    // DELETE
    // 実装クラスでは、入力された接続を使用して、
    // 市区町村テーブルの全件削除を行うメソッドを実装してください。
    void clearCityMasters(Connection connection) throws DatabaseManagerException;

    // DELETE
    // 実装クラスでは、入力された接続を使用して、
    // 町字テーブルの全件削除を行うメソッドを実装してください。
    void clearTownMasters(Connection connection) throws DatabaseManagerException;
}
