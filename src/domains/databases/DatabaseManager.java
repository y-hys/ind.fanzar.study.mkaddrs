package domains.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import domains.masters.CityMaster;
import domains.masters.PrefMaster;
import domains.masters.TownMaster;

// このクラスは、住所データベース管理サービスのデフォルト実装クラスです。
// 住所データは、アドレスベースレジストリに基づいた各種マスターデータを管理します。
// 各データベースプロバイダ毎に、このクラスを継承したクラスを作成します。
// 
// 例) class OracleDBManager extends DatabaseManager
//
// 使用方法
// 接続を取得する getConnection() メソッドを各データベースプロバイダに合わせて実装してください。
// データベースへの各種操作は、このクラスにデフォルトの実装が用意されています。必要に応じて、オーバーライドしてください。
abstract class DatabaseManager implements DatabaseManageable {

    @Override
    public abstract Connection getConnection() throws DatabaseManagerException;

    // このメソッドは、都道府県マスターをデータベースに登録するためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void createPrefMasters(Connection connection, Collection<PrefMaster> masters) throws DatabaseManagerException {
        this.createPrefMastersInternal(connection, masters);
    }
    private void createPrefMastersInternal(Connection connection, Collection<PrefMaster> masters) throws DatabaseManagerException {
        try {
            this.createPrefMastersExecute(connection, masters);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void createPrefMastersExecute(Connection connection, Collection<PrefMaster> masters) throws SQLException {
        var sql = "";
        sql += "INSERT INTO mt_pref (\n";
        sql += "  lg_code, pref, pref_kana, pref_roma, efct_date, ablt_date, remarks\n";
        sql += ") VALUES (\n";
        sql += "  ?, ?, ?, ?, ?, ?, ?\n";
        sql += ")";
        try (var statement = connection.prepareStatement(sql)) {
            for (var master : masters) {
                // setObject() は方がコードが揃うので美しいが、
                // 想定外のデータをセットする可能性があるので望ましくない。
                statement.setString(1, master.getLgCode());
                statement.setString(2, master.getPref());
                if (master.getPrefKana() != null) {
                    statement.setString(3, master.getPrefKana());
                } else {
                    statement.setNull(3, java.sql.Types.VARCHAR);
                }
                if (master.getPrefRoma() != null) {
                    statement.setString(4, master.getPrefRoma());
                } else {
                    statement.setNull(4, java.sql.Types.VARCHAR);
                }
                if (master.getEfctDate() != null) {
                    statement.setDate(5, java.sql.Date.valueOf(master.getEfctDate()));
                } else {
                    statement.setNull(5, java.sql.Types.DATE);
                }
                if (master.getAbltDate() != null) {
                    statement.setDate(6, java.sql.Date.valueOf(master.getAbltDate()));
                } else {
                    statement.setNull(6, java.sql.Types.DATE);
                }
                if (master.getRemarks() != null) {
                    statement.setString(7, master.getRemarks());
                } else {
                    statement.setNull(7, java.sql.Types.VARCHAR);
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    // このメソッドは、市区町村マスターをデータベースに登録するためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void createCityMasters(Connection connection, Collection<CityMaster> masters) throws DatabaseManagerException {
        this.createCityMastersInternal(connection, masters);
    }
    private void createCityMastersInternal(Connection connection, Collection<CityMaster> masters) throws DatabaseManagerException {
        try {
            this.createCityMastersExecute(connection, masters);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void createCityMastersExecute(Connection connection, Collection<CityMaster> masters) throws SQLException {
        var sql = "";
        sql += "INSERT INTO mt_city (\n";
        sql += "  lg_code, pref, pref_kana, pref_roma, county, county_kana, county_roma, city, city_kana, city_roma, ward, ward_kana, ward_roma, efct_date, ablt_date, remarks\n";
        sql += ") VALUES (\n";
        sql += "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?\n";
        sql += ")";
        try (var statement = connection.prepareStatement(sql)) {
            for (var master : masters) {
                // setObject() は方がコードが揃うので美しいが、
                // 想定外のデータをセットする可能性があるので望ましくない。
                statement.setString(1, master.getLgCode());
                statement.setString(2, master.getPref());
                if (master.getPrefKana() != null) {
                    statement.setString(3, master.getPrefKana());
                } else {
                    statement.setNull(3, java.sql.Types.VARCHAR);
                }
                if (master.getPrefRoma() != null) {
                    statement.setString(4, master.getPrefRoma());
                } else {
                    statement.setNull(4, java.sql.Types.VARCHAR);
                }
                if (master.getCounty() != null) {
                    statement.setString(5, master.getCounty());
                } else {
                    statement.setNull(5, java.sql.Types.VARCHAR);
                }
                if (master.getCountyKana() != null) {
                    statement.setString(6, master.getCountyKana());
                } else {
                    statement.setNull(6, java.sql.Types.VARCHAR);
                }
                if (master.getCountyRoma() != null) {
                    statement.setString(7, master.getCountyRoma());
                } else {
                    statement.setNull(7, java.sql.Types.VARCHAR);
                }
                statement.setString(8, master.getCity());
                if (master.getCityKana() != null) {
                    statement.setString(9, master.getCityKana());
                } else {
                    statement.setNull(9, java.sql.Types.VARCHAR);
                }
                if (master.getCityRoma() != null) {
                    statement.setString(10, master.getCityRoma());
                } else {
                    statement.setNull(10, java.sql.Types.VARCHAR);
                }
                if (master.getWard() != null) {
                    statement.setString(11, master.getWard());
                } else {
                    statement.setNull(11, java.sql.Types.VARCHAR);
                }
                if (master.getWardKana() != null) {
                    statement.setString(12, master.getWardKana());
                } else {
                    statement.setNull(12, java.sql.Types.VARCHAR);
                }
                if (master.getWardRoma() != null) {
                    statement.setString(13, master.getWardRoma());
                } else {
                    statement.setNull(13, java.sql.Types.VARCHAR);
                }
                if (master.getEfctDate() != null) {
                    statement.setDate(14, java.sql.Date.valueOf(master.getEfctDate()));
                } else {
                    statement.setNull(14, java.sql.Types.DATE);
                }
                if (master.getAbltDate() != null) {
                    statement.setDate(15, java.sql.Date.valueOf(master.getAbltDate()));
                } else {
                    statement.setNull(15, java.sql.Types.DATE);
                }
                if (master.getRemarks() != null) {
                    statement.setString(16, master.getRemarks());
                } else {
                    statement.setNull(16, java.sql.Types.VARCHAR);
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    // このメソッドは、町字マスターをデータベースに登録するためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void createTownMasters(Connection connection, Collection<TownMaster> masters) throws DatabaseManagerException {
        this.createTownMastersInternal(connection, masters);
    }
    private void createTownMastersInternal(Connection connection, Collection<TownMaster> masters) throws DatabaseManagerException {
        try {
            this.createTownMastersExecute(connection, masters);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void createTownMastersExecute(Connection connection, Collection<TownMaster> masters) throws SQLException {
        var sql = "";
        sql += "INSERT INTO mt_town (\n";
        sql += "  lg_code, machiaza_id, machiaza_type, pref, pref_kana, pref_roma, county, county_kana, county_roma, city,"; 
        sql += "  city_kana, city_roma, ward, ward_kana, ward_roma, oaza_cho, oaza_cho_kana, oaza_cho_roma, chome, chome_kana," ;
        sql += "  chome_number, koaza, koaza_kana, koaza_roma, machiaza_dist, rsdt_addr_flg, rsdt_addr_mtd_code, oaza_cho_aka_flg, koaza_aka_code, oaza_cho_gsi_uncmn,";
        sql += "  koaza_gsi_uncmn, status_flg, wake_num_flg, efct_date, ablt_date, src_code, post_code, remarks\n";
        sql += ") VALUES (\n";
        sql += "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,";
        sql += "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,";
        sql += "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,";
        sql += "  ?, ?, ?, ?, ?, ?, ?, ?";
        sql += ")";

        try (var statement = connection.prepareStatement(sql)) {
            for (var master : masters) {
                // setObject() は方がコードが揃うので美しいが、
                // 想定外のデータをセットする可能性があるので望ましくない。
                statement.setString(1, master.getLgCode());
                statement.setString(2, master.getMachiazaId());
                statement.setString(3, master.getMachiazaType());
                statement.setString(4, master.getPref());
                if (master.getPrefKana() != null) {
                    statement.setString(5, master.getPrefKana());
                } else {
                    statement.setNull(5, java.sql.Types.VARCHAR);
                }
                if (master.getPrefRoma() != null) {
                    statement.setString(6, master.getPrefRoma());
                } else {
                    statement.setNull(6, java.sql.Types.VARCHAR);
                }
                if (master.getCounty() != null) {
                    statement.setString(7, master.getCounty());
                } else {
                    statement.setNull(7, java.sql.Types.VARCHAR);
                }
                if (master.getCountyKana() != null) {
                    statement.setString(8, master.getCountyKana());
                } else {
                    statement.setNull(8, java.sql.Types.VARCHAR);
                }
                if (master.getCountyRoma() != null) {
                    statement.setString(9, master.getCountyRoma());
                } else {
                    statement.setNull(9, java.sql.Types.VARCHAR);
                }
                statement.setString(10, master.getCity());
                if (master.getCityKana() != null) {
                    statement.setString(11, master.getCityKana());
                } else {
                    statement.setNull(11, java.sql.Types.VARCHAR);
                }
                if (master.getCityRoma() != null) {
                    statement.setString(12, master.getCityRoma());
                } else {
                    statement.setNull(12, java.sql.Types.VARCHAR);
                }
                if (master.getWard() != null) {
                    statement.setString(13, master.getWard());
                } else {
                    statement.setNull(13, java.sql.Types.VARCHAR);
                }
                if (master.getWardKana() != null) {
                    statement.setString(14, master.getWardKana());
                } else {
                    statement.setNull(14, java.sql.Types.VARCHAR);
                }
                if (master.getWardRoma() != null) {
                    statement.setString(15, master.getWardRoma());
                } else {
                    statement.setNull(15, java.sql.Types.VARCHAR);
                }
                if (master.getOazaCho() != null) {
                    statement.setString(16, master.getOazaCho());
                } else {
                    statement.setNull(16, java.sql.Types.VARCHAR);
                }
                if (master.getOazaChoKana() != null) {
                    statement.setString(17, master.getOazaChoKana());
                } else {
                    statement.setNull(17, java.sql.Types.VARCHAR);
                }
                if (master.getOazaChoRoma() != null) {
                    statement.setString(18, master.getOazaChoRoma());
                } else {
                    statement.setNull(18, java.sql.Types.VARCHAR);
                }
                if (master.getChome() != null) {
                    statement.setString(19, master.getChome());
                } else {
                    statement.setNull(19, java.sql.Types.VARCHAR);
                }
                if (master.getChomeKana() != null) {
                    statement.setString(20, master.getChomeKana());
                } else {
                    statement.setNull(20, java.sql.Types.VARCHAR);
                }
                if (master.getChomeNumber() != null) {
                    statement.setString(21, master.getChomeNumber());
                } else {
                    statement.setNull(21, java.sql.Types.VARCHAR);
                }
                if (master.getKoaza() != null) {
                    statement.setString(22, master.getKoaza());
                } else {
                    statement.setNull(22, java.sql.Types.VARCHAR);
                }
                if (master.getKoazaKana() != null) {
                    statement.setString(23, master.getKoazaKana());
                } else {
                    statement.setNull(23, java.sql.Types.VARCHAR);
                }
                if (master.getKoazaRoma() != null) {
                    statement.setString(24, master.getKoazaRoma());
                } else {
                    statement.setNull(24, java.sql.Types.VARCHAR);
                }
                if (master.getMachiazaDist() != null) {
                    statement.setString(25, master.getMachiazaDist());
                } else {
                    statement.setNull(25, java.sql.Types.VARCHAR);
                }
                statement.setString(26, master.getRsdtAddrFlg());
                if (master.getRsdtAddrMtdCode() != null) {
                    statement.setString(27, master.getRsdtAddrMtdCode());
                } else {
                    statement.setNull(27, java.sql.Types.VARCHAR);
                }
                if (master.getOazaChoAkaFlg() != null) {
                    statement.setString(28, master.getOazaChoAkaFlg());
                } else {
                    statement.setNull(28, java.sql.Types.VARCHAR);
                }
                if (master.getKoazaAkaCode() != null) {
                    statement.setString(29, master.getKoazaAkaCode());
                } else {
                    statement.setNull(29, java.sql.Types.VARCHAR);
                }
                if (master.getOazaChoGsiUncmn() != null) {
                    statement.setString(30, master.getOazaChoGsiUncmn());
                } else {
                    statement.setNull(30, java.sql.Types.VARCHAR);
                }
                if (master.getKoazaGsiUncmn() != null) {
                    statement.setString(31, master.getKoazaGsiUncmn());
                } else {
                    statement.setNull(31, java.sql.Types.VARCHAR);
                }
                if (master.getStatusFlg() != null) {
                    statement.setString(32, master.getStatusFlg());
                } else {
                    statement.setNull(32, java.sql.Types.VARCHAR);
                }
                if (master.getWakeNumFlg() != null) {
                    statement.setString(33, master.getWakeNumFlg());
                } else {
                    statement.setNull(33, java.sql.Types.VARCHAR);
                }
                statement.setDate(34, java.sql.Date.valueOf(master.getEfctDate()));
                if (master.getAbltDate() != null) {
                    statement.setDate(35, java.sql.Date.valueOf(master.getAbltDate()));
                } else {
                    statement.setNull(35, java.sql.Types.DATE);
                }
                if (master.getSrcCode() != null) {
                    statement.setString(36, master.getSrcCode());
                } else {
                    statement.setNull(36, java.sql.Types.VARCHAR);
                }
                if (master.getPostCode() != null) {
                    statement.setString(37, master.getPostCode());
                } else {
                    statement.setNull(37, java.sql.Types.VARCHAR);
                }
                if (master.getRemarks() != null) {
                    statement.setString(38, master.getRemarks());
                } else {
                    statement.setNull(38, java.sql.Types.VARCHAR);
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
    
    // このメソッドは、都道府県マスターをクリアするためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void clearPrefMasters(Connection connection) throws DatabaseManagerException {
        this.clearPrefMastersInternal(connection);
    }
    private void clearPrefMastersInternal(Connection connection) throws DatabaseManagerException {
        try {
            this.clearPrefMastersExecute(connection);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void clearPrefMastersExecute(Connection connection) throws SQLException {
        var sql = "DELETE FROM mt_pref";
        try (var statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    // このメソッドは、市区町村マスターをクリアするためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void clearCityMasters(Connection connection) throws DatabaseManagerException {
        this.clearCityMastersInternal(connection);
    }
    private void clearCityMastersInternal(Connection connection) throws DatabaseManagerException {
        try {
            this.clearCityMastersExecute(connection);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void clearCityMastersExecute(Connection connection) throws SQLException {
        var sql = "DELETE FROM mt_city";
        try (var statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    // このメソッドは、町字マスターをクリアするためのデフォルトの実装です。
    // 必要に応じて、オーバーライドしてください。
    @Override
    public void clearTownMasters(Connection connection) throws DatabaseManagerException {
        this.clearTownMastersInternal(connection);
    }
    private void clearTownMastersInternal(Connection connection) throws DatabaseManagerException {
        try {
            this.clearTownMastersExecute(connection);
        } catch (SQLException e) {
            throw new DatabaseManagerException(e.getMessage());
        }
    }
    private void clearTownMastersExecute(Connection connection) throws SQLException {
        var sql = "DELETE FROM mt_town";
        try (var statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
}
