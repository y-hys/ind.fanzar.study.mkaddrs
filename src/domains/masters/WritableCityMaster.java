package domains.masters;

import java.time.LocalDate;

// 02 市区町村
public interface WritableCityMaster extends CityMaster {
    // 全国地方公共団体コード

    // 都道府県名
    void setPref(String pref);

    // 都道府県名カナ
    void setPrefKana(String prefKana);

    // 都道府県名英字
    void setPrefRoma(String prefRoma);

    // 郡名
    void setCounty(String county);

    // 郡名カナ
    void setCountyKana(String countyKana);

    // 郡名英字
    void setCountyRoma(String countyRoma);

    // 市区町村名
    void setCity(String city);

    // 市区町村名カナ
    void setCityKana(String citykana);

    // 市区町村名英字
    void setCityRoma(String cityRoma);

    // 政令市区名
    void setWard(String ward);

    // 政令市区名カナ
    void setWardKana(String wardKana);

    // 政令市区名英字
    void setWardRoma(String wardRoma);

    // 効力発生日
    void setEfctDate(LocalDate efctDate);

    // 廃止日
    void setAbltDate(LocalDate abltDate);

    // 備考
    void setRemarks(String remarks);
}
