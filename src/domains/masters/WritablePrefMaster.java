package domains.masters;

import java.time.LocalDate;

public interface WritablePrefMaster extends PrefMaster {
    // 都道府県名
    void setPref(String pref);

    // 都道府県名カナ
    void setPrefKana(String prefKana);

    // 都道府県名英字
    void setPrefRoma(String prefRoma);

    // 効力発生日
    void setEfctDate(LocalDate efctDate);

    // 廃止日
    void setAbltDate(LocalDate abltDate);

    // 備考
    void setRemarks(String remarks);
}
