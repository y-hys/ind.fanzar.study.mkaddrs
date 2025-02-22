package domains.masters;

import java.time.LocalDate;

// このクラスは、不変な市区町村マスターを表します。
// このクラスは public ではありませんが、CityMaster.of() メソッドを使用することで、
// インスタンスを取得できます。
public final class ImmutableCityMaster implements CityMaster {

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

    private final String county;
    @Override
    public String getCounty() { return this.county; }

    private final String countyKana;
    @Override
    public String getCountyKana() { return this.countyKana; }

    private final String countyRoma;
    @Override
    public String getCountyRoma() { return this.countyRoma; }

    private final String city;
    @Override
    public String getCity() { return this.city; }

    private final String cityKana;
    @Override
    public String getCityKana() { return this.cityKana; }

    private final String cityRoma;
    @Override
    public String getCityRoma() { return this.cityRoma; }

    private final String ward;
    @Override
    public String getWard() { return this.ward; }

    private final String wardKana;
    @Override
    public String getWardKana() { return this.wardKana; }

    private final String wardRoma;
    @Override
    public String getWardRoma() { return this.wardRoma; }

    private final LocalDate efctDate;
    @Override
    public LocalDate getEfctDate() { return this.efctDate; }

    private final LocalDate abltDate;
    @Override
    public LocalDate getAbltDate() { return this.abltDate; }

    private final String remarks;
    @Override
    public String getRemarks() { return this.remarks; }

    static ImmutableCityMaster of(CityMaster source) throws InvalidMasterException {
        return new ImmutableCityMaster(source);
    }

    private ImmutableCityMaster(CityMaster source) throws InvalidMasterException {
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

        var county = source.getCounty();
        ValidationHelper.validateCounty(county, key);
        this.county = county;

        var countyKana = source.getCountyKana();
        ValidationHelper.validateCountyKana(countyKana, key);
        this.countyKana = countyKana;

        var countyRoma = source.getCountyRoma();
        ValidationHelper.validateCountyRoma(countyRoma, key);
        this.countyRoma = countyRoma;

        var city = source.getCity();
        ValidationHelper.validateCity(city, key);
        this.city = city;

        var cityKana = source.getCityKana();
        ValidationHelper.validateCityKana(cityKana, key);
        this.cityKana = source.getCityKana();

        // 山梨県西八代郡市川三郷町 は、編集ミスで市区町村英字の最後が '-' で終わっているので、
        // このデータに限り、データ長と文字種類のみチェックする。
        var cityRoma = source.getCityRoma();
        if (lgCode.equals("193461")) {
            var field = "cityRoma";
            ValidationHelper.validateValue(cityRoma, field, key, 
                ValidationType.Nullable,
                ValidationType.Alphabet,
                ValidationType.Sign);
            ValidationHelper.validateLength(cityRoma, 0, 100, field, key);
        } else {
            ValidationHelper.validateCityRoma(cityRoma, key);
        }
        this.cityRoma = cityRoma;

        var ward = source.getWard();
        ValidationHelper.validateWard(ward, key);
        this.ward = ward;

        var wardKana = source.getWardKana();
        ValidationHelper.validateWardKana(wardKana, key);
        this.wardKana = wardKana;

        var wardRoma = source.getWardRoma();
        ValidationHelper.validateWardRoma(wardRoma, key);
        this.wardRoma = wardRoma;

        var efctDate = source.getEfctDate();
        this.efctDate = efctDate;

        var abltDate = source.getAbltDate();
        this.abltDate = abltDate;

        var remarks = source.getRemarks();
        ValidationHelper.validateRemarks(remarks, key);
        this.remarks = remarks;
    }
}
