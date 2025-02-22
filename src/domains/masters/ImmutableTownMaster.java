package domains.masters;

import java.time.LocalDate;

// このクラスは、不変な都道府県マスターを表します。
// このクラスは public ではありませんが、TownMaster.of() メソッドを使用することで、
// インスタンスを取得できます。
final class ImmutableTownMaster implements TownMaster {

    private final String key;
    @Override
    public String getKey() { return this.key; }

    private final String lgCode;
    @Override
    public String getLgCode() { return this.lgCode; }

    private final String machiazaId;
    @Override
    public String getMachiazaId() { return this.machiazaId; }

    private final String machiazaType;
    @Override
    public String getMachiazaType() { return this.machiazaType; }

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

    private final String oazaCho;
    @Override
    public String getOazaCho() { return this.oazaCho; }

    private final String oazaChoKana;
    @Override
    public String getOazaChoKana() { return this.oazaChoKana; }

    private final String oazaChoRoma;
    @Override
    public String getOazaChoRoma() { return this.oazaChoRoma; }

    private final String chome;
    @Override
    public String getChome() { return this.chome; }

    private final String chomeKana;
    @Override
    public String getChomeKana() { return this.chomeKana; }

    private final String chomeNumber;
    @Override
    public String getChomeNumber() { return this.chomeNumber; }

    private final String koaza;
    @Override
    public String getKoaza() { return this.koaza; }

    private final String koazaKana;
    @Override
    public String getKoazaKana() { return this.koazaKana; }

    private final String koazaRoma;
    @Override
    public String getKoazaRoma() { return this.koazaRoma; }

    private final String machiazaDist;
    @Override
    public String getMachiazaDist() { return this.machiazaDist; }

    private final String rsdtAddrFlg;
    @Override
    public String getRsdtAddrFlg() { return this.rsdtAddrFlg; }

    private final String rsdtAddrMtdCode;
    @Override
    public String getRsdtAddrMtdCode() { return this.rsdtAddrMtdCode; }

    private final String oazaChoAkaFlg;
    @Override
    public String getOazaChoAkaFlg() { return this.oazaChoAkaFlg; }

    private final String koazaAkaCode;
    @Override
    public String getKoazaAkaCode() { return this.koazaAkaCode; }

    private final String oazaChoGsiUncmn;
    @Override
    public String getOazaChoGsiUncmn() { return this.oazaChoGsiUncmn; }

    private final String koazaGsiUncmn;
    @Override
    public String getKoazaGsiUncmn() { return this.koazaGsiUncmn; }

    private final String statusFlg;
    @Override
    public String getStatusFlg() { return this.statusFlg; }

    private final String wakeNumFlg;
    @Override
    public String getWakeNumFlg() { return this.wakeNumFlg; }

    private final LocalDate efctDate;
    @Override
    public LocalDate getEfctDate() { return this.efctDate; }

    private final LocalDate abltDate;
    @Override
    public LocalDate getAbltDate() { return this.abltDate; }

    private final String srcCode;
    @Override
    public String getSrcCode() { return this.srcCode; }

    private final String postCode;
    @Override
    public String getPostCode() { return this.postCode; }

    private final String remarks;
    @Override
    public String getRemarks() { return this.remarks; }

    static ImmutableTownMaster of(TownMaster source) throws InvalidMasterException {
        return new ImmutableTownMaster(source);
    }

    private ImmutableTownMaster(TownMaster source) throws InvalidMasterException {
        var key  = source.getKey();
        this.key = key;

        var lgCode = source.getLgCode();
        ValidationHelper.validateLgCode(lgCode, key);
        this.lgCode = lgCode;

        var machiazaId = source.getMachiazaId();
        ValidationHelper.validateMachiazaId(machiazaId, key);
        this.machiazaId = machiazaId;

        var machiazaType = source.getMachiazaType();
        ValidationHelper.validateMachiazaType(machiazaType, key);
        this.machiazaType = machiazaType;

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

        var oazaCho = source.getOazaCho();
        ValidationHelper.validateOazaCho(oazaCho, key);
        this.oazaCho = oazaCho;

        var oazaChoKana = source.getOazaChoKana();
        ValidationHelper.validateOazaChoKana(oazaChoKana, key);
        this.oazaChoKana = oazaChoKana;

        var oazaChoRoma = source.getOazaChoRoma();
        ValidationHelper.validateOazaChoRoma(oazaChoRoma, key);
        this.oazaChoRoma = oazaChoRoma;

        var chome = source.getChome();
        ValidationHelper.validateChome(chome, key);
        this.chome = chome;

        var chomeKana = source.getChomeKana();
        ValidationHelper.validateChomeKana(chomeKana, key);
        this.chomeKana = chomeKana;

        var chomeNumber = source.getChomeNumber();
        ValidationHelper.validateChomeNumber(chomeNumber, key);
        this.chomeNumber = chomeNumber;

        var koaza = source.getKoaza();
        ValidationHelper.validateKoaza(koaza, key);
        this.koaza = koaza;

        // 栃木県宇都宮市上田下は、編集ミスのため、全角カナ項目に漢字が含まれている。
        // このデータに限り、データ長のみチェックする。
        var koazaKana = source.getKoazaKana();
        if (lgCode.equals("092011") && machiazaId.equals("0000230")) {
            var field = "koazaKana";
            ValidationHelper.validateLength(koazaKana, 0, 240, field, key);
        } else {
            ValidationHelper.validateKoazaKana(koazaKana, key);
        }
        this.koazaKana = koazaKana;

        var koazaRoma = source.getKoazaRoma();
        ValidationHelper.validateKoazaRoma(koazaRoma, key);
        this.koazaRoma = koazaRoma;

        var machiazaDist = source.getMachiazaDist();
        ValidationHelper.validateMachiazaDist(machiazaDist, key);
        this.machiazaDist = machiazaDist;

        var rsdtAddrFlg = source.getRsdtAddrFlg();
        ValidationHelper.validateRsdtAddrFlg(rsdtAddrFlg, key);
        this.rsdtAddrFlg = rsdtAddrFlg;

        var rsdtAddrMtdCode = source.getRsdtAddrMtdCode();
        ValidationHelper.validateRsdtAddrMtdCode(rsdtAddrMtdCode, key);
        this.rsdtAddrMtdCode = rsdtAddrMtdCode;

        var oazaChoAkaFlg = source.getOazaChoAkaFlg();
        ValidationHelper.validateOazaChoAkaFlg(oazaChoAkaFlg, key);
        this.oazaChoAkaFlg = oazaChoAkaFlg;

        var koazaAkaCode = source.getKoazaAkaCode();
        ValidationHelper.validateKoazaAkaCode(koazaAkaCode, key);
        this.koazaAkaCode = koazaAkaCode;

        var oazaChoGsiUncmn = source.getOazaChoGsiUncmn();
        ValidationHelper.validateOazaChoGsiUncmn(oazaChoGsiUncmn, key);
        this.oazaChoGsiUncmn = oazaChoGsiUncmn;

        var koazaGsiUncmn = source.getKoazaGsiUncmn();
        ValidationHelper.validateKoazaGsiUncmn(koazaGsiUncmn, key);
        this.koazaGsiUncmn = koazaGsiUncmn;

        var statusFlg = source.getStatusFlg();
        ValidationHelper.validateStatusFlg(statusFlg, key);
        this.statusFlg = statusFlg;

        var wakeNumFlg = source.getWakeNumFlg();
        ValidationHelper.validateWakeNumFlg(wakeNumFlg, key);
        this.wakeNumFlg = wakeNumFlg;

        var efctDate = source.getEfctDate();
        ValidationHelper.validateEfctDate(efctDate, key);
        this.efctDate = efctDate;
        
        var abltDate = source.getAbltDate();
        this.abltDate = abltDate;

        var srcCode = source.getSrcCode();
        ValidationHelper.validateSrcCode(srcCode, key);
        this.srcCode = srcCode;

        var postCode = source.getPostCode();
        ValidationHelper.validatePostCode(postCode, key);
        this.postCode = postCode;

        var remarks = source.getRemarks();
        ValidationHelper.validateRemarks(remarks, key);
        this.remarks = remarks;
    }
}
