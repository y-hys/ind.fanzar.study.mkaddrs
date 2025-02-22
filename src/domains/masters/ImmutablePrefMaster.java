package domains.masters;

import java.time.LocalDate;

// このクラスは、不変な都道府県マスターを表します。
// このクラスは public ではありませんが、PrefMaster.of() メソッドを使用することで、
// インスタンスを取得できます。
final class ImmutablePrefMaster implements PrefMaster {
    private final String key;
    @Override
    public String getKey() { return this.key; }

    private final String lgCode;
    @Override
    public String getLgCode() { return this.lgCode; }

    private final String pref;
    @Override
    public String getPref() { return this.pref; }

    private final String prefKana;
    @Override
    public String getPrefKana() { return this.prefKana; }

    private final String prefRoma;
    @Override
    public String getPrefRoma() { return this.prefRoma; }

    private final LocalDate efctDate;
    @Override
    public LocalDate getEfctDate() { return this.efctDate; }

    private final LocalDate abltDate;
    @Override
    public LocalDate getAbltDate() { return this.abltDate; }

    private final String remarks;
    @Override
    public String getRemarks() { return this.remarks; }

    static ImmutablePrefMaster of(PrefMaster source) throws InvalidMasterException {
        return new ImmutablePrefMaster(source);
    }

    private ImmutablePrefMaster(PrefMaster source) throws InvalidMasterException {
        var key  = source.getKey();
        this.key = key;

        var lgCode = source.getLgCode();
        ValidationHelper.validateLgCode(lgCode, key);
        this.lgCode = lgCode;

        var pref = source.getPref();
        ValidationHelper.validatePref(pref, key);
        this.pref = pref;

        var prefKana = source.getPrefKana();
        ValidationHelper.validatePrefKana(prefKana, key);
        this.prefKana = prefKana;

        var prefRoma = source.getPrefRoma();
        ValidationHelper.validatePrefRoma(prefRoma, key);
        this.prefRoma = prefRoma;

        var efctDate = source.getEfctDate();
        this.efctDate = efctDate;

        var abltDate = source.getAbltDate();
        this.abltDate = abltDate;
        
        var remarks = source.getRemarks();
        ValidationHelper.validateRemarks(remarks, key);
        this.remarks = remarks;
    }
}
