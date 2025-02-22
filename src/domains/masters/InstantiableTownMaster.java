package domains.masters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// このクラスは、生成可能な町字マスターを表します。
// コンストラクタの代わりに、ビルダーを使用してインスタンスを生成可能です。
// 各フィールドに対するセッターメソッドは提供されておらず、生成後は基本的に変更できません。
public final class InstantiableTownMaster implements TownMaster {
    private final String key;
    public String getKey() { return this.key; }

    private final String lgCode;
    public String getLgCode() { return this.lgCode; }

    private final String machiazaId;
    public String getMachiazaId() { return this.machiazaId; }

    private String machiazaType;
    public String getMachiazaType() { return this.machiazaType; }

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

    private String oazaCho;
    public String getOazaCho() { return this.oazaCho; }

    private String oazaChoKana;
    public String getOazaChoKana() { return this.oazaChoKana; }

    private String oazaChoRoma;
    public String getOazaChoRoma() { return this.oazaChoRoma; }

    private String chome;
    public String getChome() { return this.chome; }

    private String chomeKana;
    public String getChomeKana() { return this.chomeKana; }

    private String chomeNumber;
    public String getChomeNumber() { return this.chomeNumber; }

    private String koaza;
    public String getKoaza() { return this.koaza; }

    private String koazaKana;
    public String getKoazaKana() { return this.koazaKana; }

    private String koazaRoma;
    public String getKoazaRoma() { return this.koazaRoma; }

    private String machiazaDist;
    public String getMachiazaDist() { return this.machiazaDist; }

    private final String rsdtAddrFlg;
    public String getRsdtAddrFlg() { return this.rsdtAddrFlg; }

    private String rsdtAddrMtdCode;
    public String getRsdtAddrMtdCode() { return this.rsdtAddrMtdCode; }

    private String oazaChoAkaFlg;
    public String getOazaChoAkaFlg() { return this.oazaChoAkaFlg; }

    private String koazaAkaCode;
    public String getKoazaAkaCode() { return this.koazaAkaCode; }

    private String oazaChoGsiUncmn;
    public String getOazaChoGsiUncmn() { return this.oazaChoGsiUncmn; }

    private String koazaGsiUncmn;
    public String getKoazaGsiUncmn() { return this.koazaGsiUncmn; }

    private String statusFlg;
    public String getStatusFlg() { return this.statusFlg; }

    private String wakeNumFlg;
    public String getWakeNumFlg() { return this.wakeNumFlg; }

    private final LocalDate efctDate;
    public LocalDate getEfctDate() { return this.efctDate; }

    private LocalDate abltDate;
    public LocalDate getAbltDate() { return this.abltDate; }

    private String srcCode;
    public String getSrcCode() { return this.srcCode; }

    private String postCode;
    public String getPostCode() { return this.postCode; }

    private String remarks;
    public String getRemarks() { return this.remarks; }

    public static class Builder {
        private final String key;

        private final String lgCode;

        private final String machiazaId;

        private final String machiazaType;

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
            ValidationHelper.validateCountyKana(countyKana, this.key);
            this.countyKana = countyKana;
            return this;
        }

        private String countyRoma;
        public Builder countyRoma(String countyRoma) throws InvalidMasterException {
            ValidationHelper.validateCountyRoma(countyRoma, this.key);
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

        private String oazaCho;
        public Builder oazaCho(String oazaCho) throws InvalidMasterException {
            ValidationHelper.validateOazaCho(oazaCho, this.key);
            this.oazaCho = oazaCho;
            return this;
        }

        private String oazaChoKana;
        public Builder oazaChoKana(String oazaChoKana) throws InvalidMasterException {
            ValidationHelper.validateOazaChoKana(oazaChoKana, this.key);
            this.oazaChoKana = oazaChoKana;
            return this;
        }

        private String oazaChoRoma;
        public Builder oazaChoRoma(String oazaChoRoma) throws InvalidMasterException {
            ValidationHelper.validateOazaChoRoma(oazaChoRoma, this.key);
            this.oazaChoRoma = oazaChoRoma;
            return this;
        }

        private String chome;
        public Builder chome(String chome) throws InvalidMasterException {
            ValidationHelper.validateChome(chome, this.key);
            this.chome = chome;
            return this;
        }
        
        private String chomeKana;
        public Builder chomeKana(String chomeKana) throws InvalidMasterException {
            ValidationHelper.validateChomeKana(chomeKana, this.key);
            this.chomeKana = chomeKana;
            return this;
        }

        private String chomeNumber;
        public Builder chomeNumber(String chomeNumber) throws InvalidMasterException {
            ValidationHelper.validateChomeNumber(chomeNumber, this.key);
            this.chomeNumber = chomeNumber;
            return this;
        }

        private String koaza;
        public Builder koaza(String koaza) throws InvalidMasterException {
            ValidationHelper.validateKoaza(koaza, this.key);
            this.koaza = koaza;
            return this;
        }

        // 栃木県宇都宮市上田下は、編集ミスのため、全角カナ項目に漢字が含まれている。
        // このデータに限り、データ長のみチェックする。
        private String koazaKana;
        public Builder koazaKana(String koazaKana) throws InvalidMasterException {
            if (this.lgCode.equals("092011") && this.machiazaId.equals("0000230")) {
                var field = "koazaKana";
                ValidationHelper.validateLength(koazaKana, 0, 240, field, this.key);
            } else {
                ValidationHelper.validateKoazaKana(koazaKana, this.key);
            }
            this.koazaKana = koazaKana;
            return this;
        }

        private String koazaRoma;
        public Builder koazaRoma(String koazaRoma) throws InvalidMasterException {
            ValidationHelper.validateKoazaRoma(koazaRoma, this.key);
            this.koazaRoma = koazaRoma;
            return this;
        }

        private String machiazaDist;
        public Builder machiazaDist(String machiazaDist) throws InvalidMasterException {
            ValidationHelper.validateMachiazaDist(machiazaDist, this.key);
            this.machiazaDist = machiazaDist;
            return this;
        }

        private final String rsdtAddrFlg;

        private String rsdtAddrMtdCode;
        public Builder rsdtAddrMtdCode(String rsdtAddrMtdCode) throws InvalidMasterException {
            ValidationHelper.validateRsdtAddrMtdCode(rsdtAddrMtdCode, this.key);
            this.rsdtAddrMtdCode = rsdtAddrMtdCode;
            return this;
        }

        private String oazaChoAkaFlg;
        public Builder oazaChoAkaFlg(String oazaChoAkaFlg) throws InvalidMasterException {
            ValidationHelper.validateOazaChoAkaFlg(oazaChoAkaFlg, this.key);
            this.oazaChoAkaFlg = oazaChoAkaFlg;
            return this;
        }

        private String koazaAkaCode;
        public Builder koazaAkaCode(String koazaAkaCode) throws InvalidMasterException {
            ValidationHelper.validateKoazaAkaCode(koazaAkaCode, this.key);
            this.koazaAkaCode = koazaAkaCode;
            return this;
        }

        private String oazaChoGsiUncmn;
        public Builder oazaChoGsiUncmn(String oazaChoGsiUncmn) throws InvalidMasterException {
            ValidationHelper.validateOazaChoGsiUncmn(oazaChoGsiUncmn, this.key);
            this.oazaChoGsiUncmn = oazaChoGsiUncmn;
            return this;
        }

        private String koazaGsiUncmn;
        public Builder koazaGsiUncmn(String koazaGsiUncmn) throws InvalidMasterException {
            ValidationHelper.validateKoazaGsiUncmn(koazaGsiUncmn, this.key);
            this.koazaGsiUncmn = koazaGsiUncmn;
            return this;
        }

        private String statusFlg;
        public Builder statusFlg(String statusFlg) throws InvalidMasterException {
            ValidationHelper.validateStatusFlg(statusFlg, this.key);
            this.statusFlg = statusFlg;
            return this;
        }

        private String wakeNumFlg;
        public Builder wakeNumFlg(String wakeNumFlg) throws InvalidMasterException {
            ValidationHelper.validateWakeNumFlg(wakeNumFlg, this.key);
            this.wakeNumFlg = wakeNumFlg;
            return this;
        }

        private final LocalDate efctDate;

        private LocalDate abltDate;
        public Builder abltDate(LocalDate abltDate) {
            this.abltDate = abltDate;
            return this;
        }

        private String srcCode;
        public Builder srcCode(String srcCode) throws InvalidMasterException {
            ValidationHelper.validateSrcCode(srcCode, this.key);
            this.srcCode = srcCode;
            return this;
        }

        private String postCode;
        public Builder postCode(String postCode) throws InvalidMasterException {
            ValidationHelper.validatePostCode(postCode, this.key);
            this.postCode = postCode;
            return this;
        }

        private String remarks;
        public Builder remarks(String remarks) throws InvalidMasterException {
            ValidationHelper.validateRemarks(remarks, this.key);
            this.remarks = remarks;
            return this;
        }

        public InstantiableTownMaster build() throws InvalidMasterException {
            var master  = new InstantiableTownMaster(
                this.key,
                this.lgCode, 
                this.machiazaId, 
                this.machiazaType, 
                this.pref,
                this.city,
                this.rsdtAddrFlg, 
                this.efctDate);
            master.prefKana        = this.prefKana;
            master.prefRoma        = this.prefRoma;
            master.county          = this.county;
            master.countyKana      = this.countyKana;
            master.countyRoma      = this.countyRoma;
            master.cityKana        = this.cityKana;
            master.cityRoma        = this.cityRoma;
            master.ward            = this.ward;
            master.wardKana        = this.wardKana;
            master.wardRoma        = this.wardRoma;
            master.oazaCho         = this.oazaCho;
            master.oazaChoKana     = this.oazaChoKana;
            master.oazaChoRoma     = this.oazaChoRoma;
            master.chome           = this.chome;
            master.chomeKana       = this.chomeKana;
            master.chomeNumber     = this.chomeNumber;
            master.koaza           = this.koaza;
            master.koazaKana       = this.koazaKana;
            master.koazaRoma       = this.koazaRoma;
            master.machiazaDist    = this.machiazaDist;
            master.rsdtAddrMtdCode = this.rsdtAddrMtdCode;
            master.oazaChoAkaFlg   = this.oazaChoAkaFlg;
            master.koazaAkaCode    = this.koazaAkaCode;
            master.oazaChoGsiUncmn = this.oazaChoGsiUncmn;
            master.koazaGsiUncmn   = this.koazaGsiUncmn;
            master.statusFlg       = this.statusFlg;
            master.wakeNumFlg      = this.wakeNumFlg;
            master.abltDate        = this.abltDate;
            master.srcCode         = this.srcCode;
            master.postCode        = this.postCode;
            master.remarks         = this.remarks;

            ValidationHelper.validateMaster(master, this.key);
            return master;
        }

        public Builder(String lgCode, 
            String machiazaId, 
            String machiazaType, 
            String pref,
            String city,
            String rsdtAddrFlg, 
            LocalDate efctDate) throws InvalidMasterException {

            var formatter = DateTimeFormatter.BASIC_ISO_DATE;
            var key = lgCode 
                + "-" + machiazaId
                + "-" + rsdtAddrFlg
                + "-" + formatter.format(efctDate);

            ValidationHelper.validateLgCode(lgCode, key);
            this.lgCode = lgCode;

            ValidationHelper.validateMachiazaId(machiazaId, key);
            this.machiazaId = machiazaId;

            ValidationHelper.validateMachiazaType(machiazaType, key);
            this.machiazaType = machiazaType;

            ValidationHelper.validatePref(pref, key);
            this.pref = pref;

            ValidationHelper.validateCity(city, key);
            this.city = city;

            ValidationHelper.validateRsdtAddrFlg(rsdtAddrFlg, key);
            this.rsdtAddrFlg = rsdtAddrFlg;

            ValidationHelper.validateEfctDate(efctDate, key);
            this.efctDate = efctDate;

            this.key = key;
        }
    }

    private InstantiableTownMaster(
        String key,
        String lgCode, 
        String machiazaId, 
        String machiazaType, 
        String pref,
        String city,
        String rdstAddrFlg, 
        LocalDate efctDate) {
        this.key          = key;
        this.lgCode       = lgCode;
        this.machiazaId   = machiazaId;
        this.machiazaType = machiazaType;
        this.pref         = pref;
        this.city         = city;
        this.rsdtAddrFlg  = rdstAddrFlg;
        this.efctDate     = efctDate;
    }

    // 町字マスターデータから、このクラスのインスタンスを生成します。
    public static InstantiableTownMaster of(TownMaster source) throws InvalidMasterException {
        return new Builder(source.getLgCode(), 
            source.getMachiazaId(), 
            source.getMachiazaType(), 
            source.getPref(),
            source.getCity(),
            source.getRsdtAddrFlg(), 
            source.getEfctDate())
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
            .oazaCho(source.getOazaCho())
            .oazaChoKana(source.getOazaChoKana())
            .oazaChoRoma(source.getOazaChoRoma())
            .chome(source.getChome())
            .chomeKana(source.getChomeKana())
            .chomeNumber(source.getChomeNumber())
            .koaza(source.getKoaza())
            .koazaKana(source.getKoazaKana())
            .koazaRoma(source.getKoazaRoma())
            .machiazaDist(source.getMachiazaDist())
            .rsdtAddrMtdCode(source.getRsdtAddrMtdCode())
            .oazaChoAkaFlg(source.getOazaChoAkaFlg())
            .koazaAkaCode(source.getKoazaAkaCode())
            .oazaChoGsiUncmn(source.getOazaChoGsiUncmn())
            .koazaGsiUncmn(source.getKoazaGsiUncmn())
            .statusFlg(source.getStatusFlg())
            .wakeNumFlg(source.getWakeNumFlg())
            .abltDate(source.getAbltDate())
            .srcCode(source.getSrcCode())
            .postCode(source.getPostCode())
            .remarks(source.getRemarks())
            .build();
    }
}
