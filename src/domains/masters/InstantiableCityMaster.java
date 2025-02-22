package domains.masters;

import java.time.LocalDate;

// このクラスは、生成可能な市区町村マスターを表します。
// コンストラクタの代わりに、ビルダーを使用してインスタンスを生成可能です。
// 各フィールドに対するセッターメソッドは提供されておらず、生成後は基本的に変更できません。
public final class InstantiableCityMaster implements CityMaster {
    private final String key;
    public String getKey() { return this.key; }

    private final String lgCode;
    public String getLgCode() { return this.lgCode; }

    private String pref;
    public String getPref() { return this.pref; }

    private String prefKana;
    public String getPrefKana() { return this.prefKana; }

    private String prefRoma;
    public String getPrefRoma() { return this.prefRoma; }

    private String county;
    public String getCounty() { return this.county; }

    private String countyKana;
    public String getCountyKana() { return this.countyKana; }

    private String countyRoma;
    public String getCountyRoma() { return this.countyRoma; }

    private String city;
    public String getCity() { return this.city; }

    private String cityKana;
    public String getCityKana() { return this.cityKana; }

    private String cityRoma;
    public String getCityRoma() { return this.cityRoma; }

    private String ward;
    public String getWard() { return this.ward; }

    private String wardKana;
    public String getWardKana() { return this.wardKana; }

    private String wardRoma;
    public String getWardRoma() { return this.wardRoma; }

    private LocalDate efctDate;
    public LocalDate getEfctDate() { return this.efctDate; }

    private LocalDate abltDate;
    public LocalDate getAbltDate() { return this.abltDate; }

    private String remarks;
    public String getRemarks() { return this.remarks; }

    public final static class Builder {
        private final String key;

        private final String lgCode;

        private final String pref;

        private String prefKana;
        public Builder prefKana(String prefKana) throws InvalidMasterException {
            ValidationHelper.validatePrefKana(prefKana, this.key);
            this.prefKana = prefKana;
            return this;
        }

        private String prefRoma;
        public Builder prefRoma(String prefRoma) throws InvalidMasterException {
            ValidationHelper.validatePrefRoma(prefRoma, this.key);
            this.prefRoma = prefRoma;
            return this;
        }

        private String county;
        public Builder county(String county) throws InvalidMasterException {
            ValidationHelper.validateCounty(county, this.key);
            this.county = county;
            return this;
        }

        private String countyKana;
        public Builder countyKana(String countyKana) throws InvalidMasterException {
            ValidationHelper.validateCounty(countyKana, this.key);
            this.countyKana = countyKana;
            return this;
        }

        private String countyRoma;
        public Builder countyRoma(String countyRoma) throws InvalidMasterException {
            ValidationHelper.validateCounty(countyRoma, this.key);
            this.countyRoma = countyRoma;
            return this;
        }

        private final String city;

        private String cityKana;
        public Builder cityKana(String cityKana) throws InvalidMasterException {
            ValidationHelper.validateCityKana(cityKana, this.key);
            this.cityKana = cityKana;
            return this;
        }

        // 山梨県西八代郡市川三郷町 は、編集ミスで市区町村英字の最後が '-' で終わっているので、
        // このデータに限り、データ長と文字種類のみチェックする。
        private String cityRoma;
        public Builder cityRoma(String cityRoma) throws InvalidMasterException {
            if (this.lgCode.equals("193461")) {
                var field = "cityRoma";
                ValidationHelper.validateValue(cityRoma, field, this.key, 
                    ValidationType.Nullable,
                    ValidationType.Alphabet,
                    ValidationType.Sign);
                ValidationHelper.validateLength(cityRoma, 0, 100, field, this.key);
            } else {
                ValidationHelper.validateCityRoma(cityRoma, this.key);
            }
            this.cityRoma = cityRoma;
            return this;
        }

        private String ward;
        public Builder ward(String ward) throws InvalidMasterException {
            ValidationHelper.validateWard(ward, this.key);
            this.ward = ward;
            return this;
        }

        private String wardKana;
        public Builder wardKana(String wardKana) throws InvalidMasterException {
            ValidationHelper.validateWardKana(wardKana, this.key);
            this.wardKana = wardKana;
            return this;
        }

        private String wardRoma;
        public Builder wardRoma(String wardRoma) throws InvalidMasterException {
            ValidationHelper.validateWardRoma(wardRoma, this.key);
            this.wardRoma = wardRoma;
            return this;
        }

        private LocalDate efctDate;
        public Builder efctDate(LocalDate efctDate) {
            this.efctDate = efctDate;
            return this;
        }

        private LocalDate abltDate;
        public Builder abltDate(LocalDate abltDate) {
            this.abltDate = abltDate;
            return this;
        }

        private String remarks;
        public Builder remarks(String remarks) throws InvalidMasterException {
            ValidationHelper.validateRemarks(remarks, this.key);
            this.remarks = remarks;
            return this;
        }

        public InstantiableCityMaster build() {
            var master        = new InstantiableCityMaster(this.key, this.lgCode, this.pref, this.city);
            master.prefKana   = this.prefKana;
            master.prefRoma   = this.prefRoma;
            master.county     = this.county;
            master.countyKana = this.countyKana;
            master.countyRoma = this.countyRoma;
            master.cityKana   = this.cityKana;
            master.cityRoma   = this.cityRoma;
            master.ward       = this.ward;
            master.wardKana   = this.wardKana;
            master.wardRoma   = this.wardRoma;
            master.efctDate   = this.efctDate;
            master.abltDate   = this.abltDate;
            master.remarks    = this.remarks;
            return master;
        }

        public Builder(String lgCode, String pref, String city) throws InvalidMasterException {
            var key = lgCode;

            ValidationHelper.validateLgCode(lgCode, key);
            this.lgCode = lgCode;

            ValidationHelper.validatePref(pref, key);
            this.pref = pref;

            ValidationHelper.validateCity(city, key);
            this.city = city;
            
            this.key = key;
        }
    }

    private InstantiableCityMaster(String key, String lgCode, String pref, String city) {
        this.key    = key;
        this.lgCode = lgCode;
        this.pref   = pref;
        this.city   = city;
    }

    public static InstantiableCityMaster of(CityMaster source) throws InvalidMasterException {
        return new Builder(source.getLgCode(), source.getPref(), source.getCity())
            .prefKana(source.getPrefKana())
            .prefRoma(source.getPrefRoma())
            .county(source.getCounty())
            .countyKana(source.getCountyKana())
            .countyRoma(source.getCountyRoma())
            .cityKana(source.getCityKana())
            .cityRoma(source.getCityRoma())
            .ward(source.getWard())
            .wardKana(source.getWardKana())
            .wardRoma(source.getWardRoma())
            .efctDate(source.getEfctDate())
            .abltDate(source.getAbltDate())
            .remarks(source.getRemarks())
            .build();
    }
}
