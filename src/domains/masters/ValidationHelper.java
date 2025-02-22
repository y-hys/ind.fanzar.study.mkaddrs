package domains.masters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ValidationHelper {

    // 対象データが指定された桁数であることを検証します。
    public static void validateLength(String value, int length, String fieldName, String key) throws InvalidMasterException {
        ValidationHelper.validateLengthInternal(value, length, fieldName, key);
    }
    private static void validateLengthInternal(String value, int length, String fieldName, String key) throws InvalidMasterException {
        ValidationHelper.validateLengthInternal(value, length, length, fieldName, key);
    }
    // 対象データが指定された範囲の桁数であることを検証します。
    public static void validateLength(String value, int min, int max, String fieldName, String key) throws InvalidMasterException {
        ValidationHelper.validateLengthInternal(value, min, max, fieldName, key);
    }
    private static void validateLengthInternal(String value, int min, int max, String fieldName, String key) throws InvalidMasterException {
        if (ValidationHelper.isValidLengthInternal(value, min, max)) {
            return;
        }
        var message = String.format("[%s:%s] データ桁数が想定の範囲外になっています。", key, fieldName);
        throw new InvalidMasterException(message);
    }
    // 対象データが指定された範囲の桁数であることを判定します。
    private static boolean isValidLengthInternal(String value, int min, int max) {
        if (value == null && min > 0) {
            return false;
        } else if (value != null && value.length() < min) {
            return false;
        } else if (value != null && value.length() > max) {
            return false;
        } else {
            return true;
        }
    }

    // 基本ラテン文字（数字のみ）
    private static final char[][] RANGE_NUMBER        = {{'\u0030', '\u0039'}};
    // 基本ラテン文字（英字のみ）
    private static final char[][] RANGE_ALPHABET      = {{'\u0041', '\u005A'}, {'\u0061', '\u007A'}};
    // 基本ラテン文字（記号のみ）
    private static final char[][] RANGE_SIGN          = {{'\u0020', '\u002F'}, {'\u003A', '\u0040'}, {'\u0058', '\u0060'}, {'\u007B', '\u007E'}};
    // ＣＪＫ記号・句読点（全角ひらがな除く）＋半角・全角形（半角カナ除く）
    // 半角・全角形のローマ字を含んでいる。
    private static final char[][] RANGE_FULL_KATAKANA = {{'\u3000', '\u303F'}, {'\u3099', '\u30FF'}, {'\uFF01', '\uFF5E'}};

    // 入力されたデータの文字種類を検証します。
    public static void validateValue(String value, String fieldName, String key, ValidationType... types) throws InvalidMasterException {
        ValidationHelper.validateValueInternal(value, fieldName, key, types);
    }
    private static void validateValueInternal(String value, String fieldName, String key, ValidationType... types) throws InvalidMasterException {
        if (value == null) {
            ValidationHelper.validateNull(value, fieldName, key, types);
        } else {
            ValidationHelper.validateNotNullValue(value, fieldName, key, types);
        }
    }
    // 入力されたデータが Null の場合を検証します。
    // ValidationType.Nullable を指定せずこの処理が呼び出された場合は、エラーになります。
    private static void validateNull(String value, String fieldName, String key, ValidationType[] types) throws InvalidMasterException {
        var typeList = List.of(types);
        if (typeList.contains(ValidationType.Nullable)) {
            return;
        }
        var message = String.format("[%s:%s] このデータ項目は Not Null です。", key, fieldName);
        throw new InvalidMasterException(message);
    }
    // 入力されたデータが Null 以外の場合を検証します。
    // ValidationType.AllCharcters を指定していない場合、実際のチェックが行われます。
    private static void validateNotNullValue(String value, String fieldName, String key, ValidationType[] types) throws InvalidMasterException {
        var typeList = List.of(types);
        if (typeList.contains(ValidationType.AllCharcters)) {
            return;
        }

        // 文字種別の範囲を取得
        var rangeGroups = new ArrayList<char[][]>();
        if (typeList.contains(ValidationType.Number)) {
            rangeGroups.add(RANGE_NUMBER);
        }
        if (typeList.contains(ValidationType.Alphabet)) {
            rangeGroups.add(RANGE_ALPHABET);
        }
        if (typeList.contains(ValidationType.Sign)) {
            rangeGroups.add(RANGE_SIGN);
        }
        if (typeList.contains(ValidationType.FullKatakana)) {
            rangeGroups.add(RANGE_FULL_KATAKANA);
        }
        // Java SE 11 以降では、Stream<E>.toList() が利用できる。
        // Stream<E>.collect(Collectors.toList())よりすっきりしている。
        var ranges = rangeGroups.stream()
            .flatMap(Stream::of)
            .collect(Collectors.toList());

        ValidationHelper.validateRange(value, fieldName, key, ranges);
    }
    private static void validateRange(String value, String fieldName, String key, Collection<char[]> ranges) throws InvalidMasterException {
        if (ValidationHelper.isValidRange(value, ranges)) {
            return;
        }
        var message = String.format("[%s:%s] データが想定範囲外の文字を含んでいます。", key, fieldName);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRange(String value, Collection<char[]> ranges) throws InvalidMasterException {
        for (var c : value.toCharArray()) {
            var hit = false;
            for (var range : ranges) {
                if (c >= range[0] && c <= range[1]) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                return false;
            }
        }
        return true;
    }
    
    // 検証エラー時に表示するエラー発生フィールド名
    private static final String FIELD_LG_CODE            = "lgCode";
    private static final String FIELD_MACHIAZA_ID        = "machiazaId";
    private static final String FIELD_MACHIAZA_TYPE      = "machiazaType";
    private static final String FIELD_PREF               = "pref";
    private static final String FIELD_PREF_KANA          = "prefKana";
    private static final String FIELD_PREF_ROMA          = "prefRoma";
    private static final String FIELD_COUNTY             = "county";
    private static final String FIELD_COUNTY_KANA        = "countyKana";
    private static final String FIELD_COUNTY_ROMA        = "countyRoma";
    private static final String FIELD_CITY               = "city";
    private static final String FIELD_CITY_KANA          = "cityKana";
    private static final String FIELD_CITY_ROMA          = "cityRoma";
    private static final String FIELD_WARD               = "ward";
    private static final String FIELD_WARD_KANA          = "wardKana";
    private static final String FIELD_WARD_ROMA          = "wardRoma";
    private static final String FIELD_OAZA_CHO           = "oazaCho";
    private static final String FIELD_OAZA_CHO_KANA      = "oazaChoKana";
    private static final String FIELD_OAZA_CHO_ROMA      = "oazaChoRoma";
    private static final String FIELD_CHOME              = "chome";
    private static final String FIELD_CHOME_KANA         = "chomeKana";
    private static final String FIELD_CHOME_NUMBER       = "chomeNumber";
    private static final String FIELD_KOAZA              = "koaza";
    private static final String FIELD_KOAZA_KANA         = "koazaKana";
    private static final String FIELD_KOAZA_ROMA         = "koazaRoma";
    private static final String FIELD_MACHIAZA_DIST      = "machiazaDist";
    private static final String FIELD_RSDT_ADD_FLG       = "rsdtAddrFlg";
    private static final String FIELD_RSDT_ADDR_MTD_CODE = "rsdtAddrCode";
    private static final String FIELD_OAZA_CHO_AKA_FLG   = "oazaChoAkaFlg";
    private static final String FIELD_KOAZA_AKA_CODE     = "koazaAkaCode";
    private static final String FIELD_OAZA_CHO_GSI_UNCMN = "oazaChoGsiUncmn";
    private static final String FIELD_KOAZA_GSI_UNCMN    = "koazaGsiUncmn";
    private static final String FIELD_STATUS_FLG         = "statusFlg";
    private static final String FIELD_WAKE_NUM_FLG       = "wakeNumFlg";
    private static final String FIELD_EFCT_DATE          = "efctDate";
    private static final String FIELD_SRC_CODE           = "srcCode";
    private static final String FIELD_POST_CODE          = "postCode";
    private static final String FIELD_REMARKS            = "remarks";

    // 入力された地方公共団体コードを検証します。
    public static void validateLgCode(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateLgCodeInternal(value, key);
    }
    private static void validateLgCodeInternal(String value, String key) throws InvalidMasterException {
        var field = FIELD_LG_CODE;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 6, field, key);
    }
    
    // 入力された町字IDを検証します。
    public static void validateMachiazaId(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateMachiazaIdInternal(value, key);
    }
    private static void validateMachiazaIdInternal(String value, String key) throws InvalidMasterException {
        var field = FIELD_MACHIAZA_ID;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 7, field, key);
    }
    
    // 入力された町字区分コードを検証します。
    public static void validateMachiazaType(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateMachiazaTypeInternal(value, key);
    }
    private static void validateMachiazaTypeInternal(String value, String key) throws InvalidMasterException {
        var field = FIELD_MACHIAZA_TYPE;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 1, field, key);
        
        if (ValidationHelper.isValidMachiazaType(value)) {
            return;
        }
        var message = String.format("[%s:%s] 町字区分コード は '%s', '%s', '%s', '%s', '%s' のいずれかです。", key, field,
            MachiazaType.OazaCho.getId(),
            MachiazaType.Chome.getId(),
            MachiazaType.Koaza.getId(),
            MachiazaType.None.getId(),
            MachiazaType.Street.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidMachiazaType(String value) {
        if (value == null) {
            return false;
        }

        var type = MachiazaType.valueOfId(value);
        return type == MachiazaType.OazaCho
            || type == MachiazaType.Chome
            || type == MachiazaType.Koaza
            || type == MachiazaType.None
            || type == MachiazaType.Street;
    }
    
    // 入力されたと都道府県名を検証します。
    public static void validatePref(String value, String key) throws InvalidMasterException {
        ValidationHelper.validatePrefInternal(value, key);
    }
    private static void validatePrefInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_PREF;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 10, field, key);
    }
    
    // 入力された都道府県名カナを検証します。
    public static void validatePrefKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validatePrefKanaInternal(value, key);
    }
    private static void validatePrefKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_PREF_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    // 入力された都道府県名英字を検証します。
    public static void validatePrefRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validatePrefRomaInternal(value, key);
    }
    private static void validatePrefRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_PREF_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    // 入力された郡名を検証します。
    public static void validateCounty(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCountyInternal(value, key);
    }
    private static void validateCountyInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_COUNTY;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 24, field, key);
    }
    
    // 入力された郡名カナを検証します。
    public static void validateCountyKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCountyKanaInternal(value, key);
    }
    private static void validateCountyKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_COUNTY_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    private static final String COUNTY_ROMA_SUFFIX = "-gun";
    
    // 入力された郡名英字を検証します。
    public static void validateCountyRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCountyRomaInternal(value, key);
    }
    private static void validateCountyRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_COUNTY_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 100, field, key);

        if (ValidationHelper.isValidCountyRomaInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 郡名（英字） は固有名称部分の後に '%s' が必要です。", key, field, COUNTY_ROMA_SUFFIX);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidCountyRomaInternal(String value) {
        return value == null || value.endsWith(COUNTY_ROMA_SUFFIX);
    }
    
    // 入力された市区町村名を検証します。
    public static void validateCity(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCityInternal(value, key);
    }
    private static void validateCityInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CITY;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 24, field, key);
    }
    
    // 入力された市区町村名カナを検証します。
    public static void validateCityKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCityKanaInternal(value, key);
    }
    private static void validateCityKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CITY_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    private static final String CITY_ROMA_SUFFIX_SHI   = "-shi";
    private static final String CITY_ROMA_SUFFIX_KU    = "-ku";
    private static final String CITY_ROMA_SUFFIX_MACHI = "-machi";
    private static final String CITY_ROMA_SUFFIX_CHO   = "-cho";
    private static final String CITY_ROMA_SUFFIX_MURA  = "-mura";
    private static final String CITY_ROMA_SUFFIX_SON   = "-son";

    // 入力された市区町村名英字を検証します。
    public static void validateCityRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateCityRomaInternal(value, key);
    }
    private static void validateCityRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CITY_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 100, field, key);

        if (ValidationHelper.isValidCityRomaInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 市区町村名（英字） は固有名称部分の後に '%s', '%s', '%s', '%s', '%s', '%s' のいずれかが必要です。", key, field,
            CITY_ROMA_SUFFIX_SHI,
            CITY_ROMA_SUFFIX_KU,
            CITY_ROMA_SUFFIX_MACHI,
            CITY_ROMA_SUFFIX_CHO,
            CITY_ROMA_SUFFIX_MURA,
            CITY_ROMA_SUFFIX_SON);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidCityRomaInternal(String value) {
        return value == null 
            || value.endsWith(CITY_ROMA_SUFFIX_SHI) 
            || value.endsWith(CITY_ROMA_SUFFIX_KU) 
            || value.endsWith(CITY_ROMA_SUFFIX_MACHI) 
            || value.endsWith(CITY_ROMA_SUFFIX_CHO)
            || value.endsWith(CITY_ROMA_SUFFIX_MURA)
            || value.endsWith(CITY_ROMA_SUFFIX_SON);
    }
    
    // 入力された政令都市区名を検証します。
    public static void validateWard(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateWardInternal(value, key);
    }
    private static void validateWardInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_WARD;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 24, field, key);
    }
    
    // 入力された政令市区名カナを検証します。
    public static void validateWardKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateWardKanaInternal(value, key);
    }
    private static void validateWardKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_WARD_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    private static final String WARD_ROMA_SUFFIX_KU = "-ku";
    
    // 入力された政令市区名英字を検証します。
    public static void validateWardRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateWardRomaInternal(value, key);
    }
    private static void validateWardRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_WARD_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 100, field, key);

        if (ValidationHelper.isValidWardRomaInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 政令市区（英字） は固有名称部分の後に '%s' が必要です。", key, field, WARD_ROMA_SUFFIX_KU);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidWardRomaInternal(String value) {
        return value == null || value.endsWith(WARD_ROMA_SUFFIX_KU);
    }
    
    // 入力された大字・町名を検証します。
    public static void validateOazaCho(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateOazaChoInternal(value, key);
    }
    private static void validateOazaChoInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_OAZA_CHO;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 120, field, key);
    }
    
    // 入力された大字・町名カナを検証します。
    public static void validateOazaChoKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateOazaChoKanaInternal(value, key);
    }
    private static void validateOazaChoKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_OAZA_CHO_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 240, field, key);
    }
    
    // 入力された大字・町名英字を検証します。
    public static void validateOazaChoRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateOazaChoRomaInternal(value, key);
    }
    private static void validateOazaChoRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_OAZA_CHO_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 180, field, key);
    }
    
    private static final String CHOME_WORD1 = "丁目";
    private static final String CHOME_WORD2 = "丁";
    
    // 入力された丁目名を検証します。
    public static void validateChome(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateChomeInternal(value, key);
    }
    private static void validateChomeInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CHOME;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 32, field, key);

        if (ValidationHelper.isValidChomeInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 丁目名 は '%s（%s）' が必要です。", key, field, CHOME_WORD1, CHOME_WORD2);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidChomeInternal(String value) {
        return value == null 
            || value.contains(CHOME_WORD1) 
            || value.contains(CHOME_WORD2);
    }
    
    private static final String CHOME_KANA_WORD1 = "チョウメ";
    private static final String CHOME_KANA_WORD2 = "チョウ";
    
    // 入力された丁目名カナを検証します。
    public static void validateChomeKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateChomeKanaInternal(value, key);
    }
    private static void validateChomeKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CHOME_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);

        if (ValidationHelper.isValidChomeKanaInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 丁目名（カナ） は '%s（%s）' が必要です。", key, field,
            CHOME_KANA_WORD1, CHOME_KANA_WORD2);
        throw new InvalidMasterException(message);
    }
    private static boolean isValidChomeKanaInternal(String value) {
        return value == null || value.contains(CHOME_KANA_WORD1) || value.contains(CHOME_KANA_WORD2);
    }
    
    // 入力された丁目名数字を検証します。
    public static void validateChomeNumber(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateChomeNumberInternal(value, key);
    }
    private static void validateChomeNumberInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_CHOME_NUMBER;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 2, field, key);
    }
    
    // 入力された小字名を検証します。
    public static void validateKoaza(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateKoazaInternal(value, key);
    }
    private static void validateKoazaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_KOAZA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 120, field, key);
    }
    
    // 入力された小字名カナを検証します。
    public static void validateKoazaKana(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateKoazaKanaInternal(value, key);
    }
    private static void validateKoazaKanaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_KOAZA_KANA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.FullKatakana);
        ValidationHelper.validateLengthInternal(value, 0, 240, field, key);
    }
    
    // 入力された小字名英字を検証します。
    public static void validateKoazaRoma(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateKoazaRomaInternal(value, key);
    }
    private static void validateKoazaRomaInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_KOAZA_ROMA;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number,
            ValidationType.Alphabet,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 180, field, key);
    }
    
    // 入力された同一町字識別情報を検証します。
    public static void validateMachiazaDist(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateMachiazaDistInternal(value, key);
    }
    private static void validateMachiazaDistInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_MACHIAZA_DIST;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 120, field, key);
    }
    
    // 入力された住居表示フラグを検証します。
    public static void validateRsdtAddrFlg(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateRsdtAddrFlgInternal(value, key);
    }
    private static void validateRsdtAddrFlgInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_RSDT_ADD_FLG;
        ValidationHelper.validateValueInternal(value, field, key, ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 1, field, key);

        if (ValidationHelper.isValidRsdtAddrFlgInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 住居表示フラグ は '%s', '%s' のいずれかです。", key, field,
            RsdtAddrFlg.NotImplemented.getId(),
            RsdtAddrFlg.Implemented.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRsdtAddrFlgInternal(String value) {
        if (value == null) {
            return false;
        }
        var flag = RsdtAddrFlg.valueOfId(value);
        return flag == RsdtAddrFlg.NotImplemented
            || flag == RsdtAddrFlg.Implemented;
    }
    
    // 入力された住居表示方式コードを検証します。
    public static void validateRsdtAddrMtdCode(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateRsdtAddrMtdCodeInternal(value, key);
    }
    private static void validateRsdtAddrMtdCodeInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_RSDT_ADDR_MTD_CODE;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 1, field, key);
        
        if (ValidationHelper.isValidRsdtAddrMtdCodeInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 住居表示方式コード は '%s', '%s', '%s' のいずれかです。", key, field,
            RsdtAddrMtdCode.NotImplemented.getId(),
            RsdtAddrMtdCode.Streat.getId(),
            RsdtAddrMtdCode.Block.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRsdtAddrMtdCodeInternal(String value) {
        if (value == null) {
            return true;
        }

        var code = RsdtAddrMtdCode.valueOfId(value);
        return code == RsdtAddrMtdCode.NotImplemented 
            || code == RsdtAddrMtdCode.Streat 
            || code == RsdtAddrMtdCode.Block;
    }
    
    // 入力された大字・町_通称フラグを検証します。
    public static void validateOazaChoAkaFlg(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateOazaChoAkaFlgInternal(value, key);
    }
    private static void validateOazaChoAkaFlgInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_OAZA_CHO_AKA_FLG;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 1, field, key);

        if (ValidationHelper.isValidOazaChoAkaFlgInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 大字・町_通称フラグ は '%s', '%s' のいずれかです。", key, field,
            OazaChoAkaFlg.NoAka.getId(),
            OazaChoAkaFlg.Aka.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidOazaChoAkaFlgInternal(String value) {
        if (value == null) {
            return true;
        }
        var flag = OazaChoAkaFlg.valueOfId(value);
        return flag == OazaChoAkaFlg.NoAka
            || flag == OazaChoAkaFlg.Aka;
    }
    
    // 入力された小字_通称フラグを検証します。
    public static void validateKoazaAkaCode(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateKoazaAkaCodeInternal(value, key);
    }
    private static void validateKoazaAkaCodeInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_KOAZA_AKA_CODE;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 1, field, key);

        if (ValidationHelper.isValidKoazaAkaCodeInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 小字_通称フラグ は '%s', '%s', '%s', '%s' のいずれかです。", key, field,
            KoazaAkaCode.NoAka.getId(),
            KoazaAkaCode.Aka.getId(),
            KoazaAkaCode.AkaKyoto.getId(),
            KoazaAkaCode.AkaGeneral.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidKoazaAkaCodeInternal(String value) {
        if (value == null) {
            return true;
        }
        var code = KoazaAkaCode.valueOfId(value);
        return code == KoazaAkaCode.NoAka
            || code == KoazaAkaCode.Aka
            || code == KoazaAkaCode.AkaKyoto
            || code == KoazaAkaCode.AkaGeneral;
    }
    
    // 入力された大字・町_外字フラグを検証します。
    public static void validateOazaChoGsiUncmn(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateOazaChoGsiUncmnInternal(value, key);
    }
    private static void validateOazaChoGsiUncmnInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_OAZA_CHO_GSI_UNCMN;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    // 入力された小字_外字フラグを検証します。
    public static void validateKoazaGsiUncmn(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateKoazaGsiUncmnInternal(value, key);
    }
    private static void validateKoazaGsiUncmnInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_KOAZA_GSI_UNCMN;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 50, field, key);
    }
    
    // 入力された状態フラグを検証します。
    public static void validateStatusFlg(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateStatusFlgInternal(value, key);
    }
    private static void validateStatusFlgInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_STATUS_FLG;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 1, field, key);

        if (ValidationHelper.isValidStatusFlgInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 状態フラグ は '%s', '%s', '%s', '%s' のいずれかです。", key, field,
            StatusFlg.Waiting.getId(),
            StatusFlg.Approved.getId(),
            StatusFlg.Rejected.getId(),
            StatusFlg.Unknown.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidStatusFlgInternal(String value) {
        if (value == null) {
            return true;
        }
        var flag = StatusFlg.valueOfId(value);
        return flag == StatusFlg.Waiting
            || flag == StatusFlg.Approved
            || flag == StatusFlg.Rejected
            || flag == StatusFlg.Unknown;
    }
    
    // 入力された起番フラグを検証します。
    public static void validateWakeNumFlg(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateWakeNumFlgInternal(value, key);
    }
    private static void validateWakeNumFlgInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_WAKE_NUM_FLG;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 1, field, key);

        if (ValidationHelper.isValidWakeNumFlgInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 起番フラグ は '%s', '%s', '%s' のいずれかです。", key, field,
            WakeNumFlg.NotRegistered.getId(),
            WakeNumFlg.Applied.getId(),
            WakeNumFlg.NotApplied.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidWakeNumFlgInternal(String value) {
        if (value == null) {
            return true;
        }
        var flag = WakeNumFlg.valueOfId(value);
        return flag == WakeNumFlg.NotRegistered
            || flag == WakeNumFlg.Applied
            || flag == WakeNumFlg.NotApplied;
    }
    
    // 入力された効力発生日を検証します。
    // 効力発生日は特殊に扱われており、都道府県マスター・市区町村マスターでは省略可能ですが、
    // 町域マスターでは必須かつキー項目となっています。
    // その為、町域マスターでの検証にのみ、このメソッドを使用してください。
    public static void validateEfctDate(LocalDate value, String key) throws InvalidMasterException {
        ValidationHelper.validateEfctDateInternal(value, key);
    }
    private static void validateEfctDateInternal(LocalDate value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_EFCT_DATE;
        if (value != null) {
            return;
        }

        var message = String.format("[%s:%s] このデータ項目は Not Null です。", key, field);
        throw new InvalidMasterException(message);
    }
    
    // 入力された原典資料コードを検証します。
    public static void validateSrcCode(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateSrcCodeInternal(value, key);
    }
    private static void validateSrcCodeInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_SRC_CODE;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number);
        ValidationHelper.validateLengthInternal(value, 0, 2, field, key);

        if (ValidationHelper.isValidSrcCodeInternal(value)) {
            return;
        }
        var message = String.format("[%s:%s] 原典資料コード は '%s', '%s', '%s', '%s', '%s', '%s' のいずれかです。", key, field,
            SrcCode.Other.getId(),
            SrcCode.LocalGov.getId(),
            SrcCode.NLID_Town.getId(),
            SrcCode.NLID_Block.getId(),
            SrcCode.NLID_Residence.getId(),
            SrcCode.NLID_Other.getId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidSrcCodeInternal(String value) {
        if (value == null) {
            return true;
        }
        var code = SrcCode.valueOfId(value);
        return code == SrcCode.Other
            || code == SrcCode.LocalGov
            || code == SrcCode.NLID_Town
            || code == SrcCode.NLID_Block
            || code == SrcCode.NLID_Residence
            || code == SrcCode.NLID_Other;
    }
    
    // 入力された郵便番号を検証します。
    public static void validatePostCode(String value, String key) throws InvalidMasterException {
        ValidationHelper.validatePostCodeInternal(value, key);
    }
    private static void validatePostCodeInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_POST_CODE;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.Number,
            ValidationType.Sign);
        ValidationHelper.validateLengthInternal(value, 0, 80, field, key);
    }
    
    // 入力された備考を検証します。
    public static void validateRemarks(String value, String key) throws InvalidMasterException {
        ValidationHelper.validateRemarksInternal(value, key);
    }
    private static void validateRemarksInternal(String value, String key) throws InvalidMasterException {
        var field = ValidationHelper.FIELD_REMARKS;
        ValidationHelper.validateValueInternal(value, field, key, 
            ValidationType.Nullable,
            ValidationType.AllCharcters);
        ValidationHelper.validateLengthInternal(value, 0, 256, field, key);
    }

    // 町域マスターの各データ間の相互関係を検証します。
    public static void validateMaster(TownMaster master, String key) throws InvalidMasterException {
        ValidationHelper.validateMasterInternal(master, key);
    }
    private static void validateMasterInternal(TownMaster master, String key) throws InvalidMasterException {
        ValidationHelper.validateRelationOfMachiazaIdAndOazaChoInternal(master);
        ValidationHelper.validateRelationOfMachiazaIdAndOazaChoAndChomeAndKoazasInternal(master);
        ValidationHelper.validateRealtionOfMachiazaTypeAndChomeAndKoazasInternal(master);
        ValidationHelper.validateRelationOfKoazaAkaCodeAndKyotoInternal(master);
        ValidationHelper.validateRelationOfOazaChoGsiUncmnAndOazaChoInternal(master);
        ValidationHelper.validateRelationOfKoazaGsiUncmnAndKoazaInternal(master);
    }

    // 町字IDと大字・町の関連性を検証します。
    // 町字IDが大字・町を示す場合、大字・町の各項目が必須です。
    private static void validateRelationOfMachiazaIdAndOazaChoInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRelationOfMachiazaIdAndOazaCho(master)) {
            return;
        }
        var key   = master.getKey();
        var field = ValidationHelper.FIELD_MACHIAZA_ID;
        var message = String.format("[%s:%s] 町字ID='%s' と 大字・町名（カナ・英字）の関連性が不正です。", key, field, master.getMachiazaId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRelationOfMachiazaIdAndOazaCho(TownMaster master) {
        var id = new MachiazaId(master.getMachiazaId());
        if (id.isOazaCho()) {
            // 大字・町（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
            //return master.getOazaCho() != null && master.getOazaChoKana() != null && master.getOazaChoRoma() != null;
            return master.getOazaCho() != null;
        } else {
            return master.getOazaCho() == null && master.getOazaChoKana() == null && master.getOazaChoRoma() == null;
        }
    }

    // 町字IDと丁目・小字の関連性を検証します。
    // 1. 町字IDが丁目を示す場合、丁目の各項目が必須、小字の各項目が不要です。
    // 2. 町字IDが小字を示す場合、小字の各項目が必須、丁目の各項目が不要です。
    // 3. 町字IDが丁目・小字を示さない場合、丁目・小字の各項目が不要です。
    private static void validateRelationOfMachiazaIdAndOazaChoAndChomeAndKoazasInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRelationOfMachiazaIdAndOazaChoAndChomeAndKoazas(master)) {
            return;
        }
        var key     = master.getKey();
        var field   = ValidationHelper.FIELD_MACHIAZA_ID;
        var message = String.format("[%s:%s] 町字ID='%s' と 大字・町（カナ・数字）、丁目（カナ・数字）・小字（カナ・英字）の関連性が不正です。", 
            key, field, master.getMachiazaId());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRelationOfMachiazaIdAndOazaChoAndChomeAndKoazas(TownMaster master) {
        var id = new MachiazaId(master.getMachiazaId());

        if (id.isOazaCho()) {
            // 大字・町あり(0001-9999)、丁目あり・小字なし(001-100)
            if (id.isChome()) {
                // 大字・町（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
                //return master.getOazaCho() != null && master.getOazaChoKana() != null && master.getOazaChoRoma() != null
                //    && master.getChome() != null && master.getChomeKana() != null && master.getChomeNumber() != null
                //    && master.getKoaza() == null && master.getKoazaKana() == null && master.getKoazaRoma() == null;
                return master.getOazaCho() != null
                    && master.getChome()   != null && master.getChomeKana() != null && master.getChomeNumber() != null
                    && master.getKoaza()   == null && master.getKoazaKana() == null && master.getKoazaRoma()   == null;
                //return master.getOazaCho() == null && master.getOazaChoKana() == null && master.getOazaChoRoma() == null;

            // 大字・町あり(0001-9999)、丁目なし・小字あり(101-999)
            } else if (id.isKoaza()) {
                //return master.getOazaCho() != null && master.getOazaChoKana() != null && master.getOazaChoRoma() != null
                //    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                //    && master.getKoaza()   != null && master.getKoazaKana()   != null && master.getKoazaRoma() != null;
                return master.getOazaCho() != null
                    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                    && master.getKoaza()   != null;

            // 大字・町あり(0001-9999)、丁目なし・小字なし(000)
            } else {
                // 大字・町（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
                // 小字（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
                //return master.getOazaCho() != null && master.getOazaChoKana() != null && master.getOazaChoRoma() != null
                //    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                //    && master.getKoaza()   == null && master.getKoazaKana()   == null && master.getKoazaRoma()   == null;
                return master.getOazaCho() != null
                    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                    && master.getKoaza()   == null && master.getKoazaKana()   == null && master.getKoazaRoma()   == null;
            }
        } else {
            // 大字・町なし(0000)、丁目あり・小字なし(001-100)
            if (id.isChome()) {
                return master.getOazaCho() == null && master.getOazaChoKana() == null && master.getOazaChoRoma() == null
                    && master.getChome()   != null && master.getChomeKana()   != null && master.getChomeNumber() != null
                    && master.getKoaza()   == null && master.getKoazaKana()   == null && master.getKoazaRoma()   == null;

            // 大字・町なし(0000)、丁目なし・小字あり(101-999)
            } else if (id.isKoaza()) {
                // 小字（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
                //return master.getOazaCho() == null && master.getOazaChoKana() == null && master.getOazaChoRoma() == null
                //    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                //    && master.getKoaza()   != null && master.getKoazaKana()   != null && master.getKoazaRoma()   != null;
                return master.getOazaCho() == null
                    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                    && master.getKoaza()   != null;

            // 大字・町なし(0000)、丁目なし・小字なし(0000)
            } else {
                return master.getOazaCho() == null && master.getOazaChoKana() == null && master.getOazaChoRoma() == null
                    && master.getChome()   == null && master.getChomeKana()   == null && master.getChomeNumber() == null
                    && master.getKoaza()   == null && master.getKoazaKana()   == null && master.getKoazaRoma()   == null;
            }
        }
    }

    // 町字区分コードと丁目・小字の関連性を検証します。
    // 町字区分コードが丁目・小字を示す場合、丁目・小字の各項目が必須です。
    private static void validateRealtionOfMachiazaTypeAndChomeAndKoazasInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRealtionOfMachiazaTypeAndChomeAndKoazas(master)) {
            return;
        }
        var key   = master.getKey();
        var field = ValidationHelper.FIELD_MACHIAZA_TYPE;
        var message = String.format("[%s:%s] 町字区分コード='%s' と 丁目（カナ・数字）、小字（カナ・ローマ字）の関連性が不正です。", 
            key, field, master.getMachiazaType());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRealtionOfMachiazaTypeAndChomeAndKoazas(TownMaster master) {
        var actualId = MachiazaType.valueOfId(master.getMachiazaType());
        if (actualId == MachiazaType.Chome) {
            return master.getChome() != null && master.getChomeKana() != null && master.getChomeNumber() != null
                && master.getKoaza() == null && master.getKoazaKana() == null && master.getKoazaRoma() == null;
        } else if (actualId == MachiazaType.Koaza) {
            // 小字（カナ、英字）は未収録の場合が多いため、一時的にコメントアウト
            //return master.getChome() == null && master.getChomeKana() == null && master.getChomeNumber() == null
            //    && master.getKoaza() != null && master.getKoazaKana() != null && master.getKoazaRoma() != null;
            return master.getChome() == null && master.getChomeKana() == null && master.getChomeNumber() == null
                && master.getKoaza() != null;
        } else {
            return master.getChome() == null && master.getChomeKana() == null && master.getChomeNumber() == null
                && master.getKoaza() == null && master.getKoazaKana() == null && master.getKoazaRoma() == null;
        }
    }

    // 小字_通称フラグが、２：京都の通り名を収録、の場合、京都府の小字のみに適用できます。
    private static void validateRelationOfKoazaAkaCodeAndKyotoInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRelationOfKoazaAkaCodeAndKyotoInternal(master)) {
            return;
        }
        var key     = master.getKey();
        var field   = ValidationHelper.FIELD_KOAZA_AKA_CODE;
        var message = String.format("[%s:%s] 小字_通称フラグ='%s' は京都府の小字のみに適用できます。", key, field, master.getKoazaAkaCode());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRelationOfKoazaAkaCodeAndKyotoInternal(TownMaster master) {
        var koazaAkaCode = KoazaAkaCode.valueOfId(master.getKoazaAkaCode());
        if (koazaAkaCode != KoazaAkaCode.AkaKyoto) {
            return true;
        }

        var machiazaId   = new MachiazaId(master.getMachiazaId());
        var machiazaType = MachiazaType.valueOfId(master.getMachiazaType());
        return master.getLgCode().startsWith("26")
            && machiazaId.isKoaza()
            && machiazaType == MachiazaType.Koaza
            //&& master.getPref().equals("京都府")
            //&& master.getPrefKana().equals("キョウトフ")
            //&& master.getPrefRoma().equals("Kyoto")
            && koazaAkaCode == KoazaAkaCode.AkaKyoto;
    }

    // 大字・町_外字フラグと大字・町の関連性を検証します。
    // 1. 大字・町_外字フラグが未入力の場合、大字・町も未入力である必要があります。
    // 2. 大字・町_外字フラグが '0' の場合に限り、大字・町が未入力であることが許容されます。
    // 3. それ以外の場合、大字・町も入力されている必要があります。
    private static void validateRelationOfOazaChoGsiUncmnAndOazaChoInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRelationOfOazaChoGsiUncmnAndOazaCho(master)) {
            return;
        }

        var key     = master.getKey();
        var field   = ValidationHelper.FIELD_OAZA_CHO_GSI_UNCMN;
        var message = String.format("[%s:%s] 大字・町_外字フラグ='%s' と大字・町の関連性が不正です。", key, field, master.getOazaChoGsiUncmn());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRelationOfOazaChoGsiUncmnAndOazaCho(TownMaster master) {
        var oazaChoGsiUncmn = master.getOazaChoGsiUncmn();
        var oazaCho         = master.getOazaCho();

        // 本来、大字・町に入力がある場合、大字・町_外字フラグも入力しなければならないはずだが、（外字がない場合、'0' を入力するため）
        // つまり、大字・町_外字フラグが未入力の場合は、大字・町も未入力の場合のみであるはずだが、
        // 大字・町に入力があり、大字・町_外字フラグが未入力の場合が多数存在するため、一時的にコメントアウト
        // 小字_外字フラグの方は、未入力の場合が少ない（何故・・・）
        if (oazaChoGsiUncmn == null) {
            //return oazaCho == null;
            return true;
        } else if (oazaChoGsiUncmn.equals("0")) {
            return true;
        } else {
            return oazaCho != null;
        }
    }

    // 小字_外字フラグと小字の関連性を検証します。
    // 1. 小字_外字フラグが未入力の場合、小字も未入力である必要があります。
    // 2. 小字_外字フラグが '0' の場合に限り、小字が未入力であることが許容されます。
    // 3. それ以外の場合、小字も入力されている必要があります。
    private static void validateRelationOfKoazaGsiUncmnAndKoazaInternal(TownMaster master) throws InvalidMasterException {
        if (isValidRelationOfKoazaGsiUncmnAndKoaza(master)) {
            return;
        }
        var key     = master.getKey();
        var field   = ValidationHelper.FIELD_KOAZA_GSI_UNCMN;
        var message = String.format("[%s:%s] 小字_外字フラグ='%s' と小字の関連性が不正です。", key, field, master.getKoazaGsiUncmn());
        throw new InvalidMasterException(message);
    }
    private static boolean isValidRelationOfKoazaGsiUncmnAndKoaza(TownMaster master) {
        var koazaGsiUncmn = master.getKoazaGsiUncmn();
        var koaza         = master.getKoaza();
        if (koazaGsiUncmn == null) {
            return koaza == null;
        } else if (koazaGsiUncmn.equals("0")) {
            return true;
        } else {
            return koaza != null;
        }
    }

    // 都道府県マスター間の相互関係を検証します。
    public static void validatePrefMasters(Collection<PrefMaster> prefMasters) throws InvalidMasterException {
        ValidationHelper.validatePrefMastersInternal(prefMasters);
    }
    private static void validatePrefMastersInternal(Collection<PrefMaster> prefMasters) throws InvalidMasterException {
        ValidationHelper.validateDistinctionOfPrefMasters(prefMasters);
    }

    // 都道府県マスター間に重複がないか検証します。
    private static void validateDistinctionOfPrefMasters(Collection<PrefMaster> prefMasters) throws InvalidMasterException {
        var key = ValidationHelper.getFirstDistinctionOfPrefMasters(prefMasters);
        if (key == null) {
            return;
        }
        var message = String.format("[%s] このキーは重複しています。", key);
        throw new InvalidMasterException(message);
    }
    private static String getFirstDistinctionOfPrefMasters(Collection<PrefMaster> prefMasters) {
        var groups = prefMasters.stream()
            .collect(Collectors.groupingBy(PrefMaster::getKey, Collectors.counting()));

        return groups.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    // 市区町村マスター間の相互関係を検証します。
    public static void validateCityMasters(Collection<CityMaster> cityMasters) throws InvalidMasterException {
        ValidationHelper.validateCityMastersInternal(cityMasters);
    }
    private static void validateCityMastersInternal(Collection<CityMaster> cityMasters) throws InvalidMasterException {
        ValidationHelper.validateDistinctionOfCityMasters(cityMasters);
    }

    // 市区町村マスター間に重複がないか検証します。
    private static void validateDistinctionOfCityMasters(Collection<CityMaster> cityMasters) throws InvalidMasterException {
        var key = ValidationHelper.getFirstDistinctionOfCityMasters(cityMasters);
        if (key == null) {
            return;
        }
        var message = String.format("[%s] このキーは重複しています。", key);
        throw new InvalidMasterException(message);
    }
    private static String getFirstDistinctionOfCityMasters(Collection<CityMaster> cityMasters) {
        var groups = cityMasters.stream()
            .collect(Collectors.groupingBy(CityMaster::getKey, Collectors.counting()));

        return groups.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    // 町域マスター間の相互関係を検証します。
    public static void validateTownMasters(Collection<TownMaster> townMasters) throws InvalidMasterException {
        ValidationHelper.validateTownMastersInternal(townMasters);
    }
    private static void validateTownMastersInternal(Collection<TownMaster> townMasters) throws InvalidMasterException {
        ValidationHelper.validateDistinctionOfTownMasters(townMasters);
    }

    // 町域マスター間に重複がないか検証します。
    private static void validateDistinctionOfTownMasters(Collection<TownMaster> townMasters) throws InvalidMasterException {
        var key = ValidationHelper.getFirstDistinctionOfTownMasters(townMasters);
        if (key == null) {
            return;
        }
        var message = String.format("[%s] このキーは重複しています。", key);
        throw new InvalidMasterException(message);
    }
    private static String getFirstDistinctionOfTownMasters(Collection<TownMaster> townMasters) {
        var groups = townMasters.stream()
            .collect(Collectors.groupingBy(TownMaster::getKey, Collectors.counting()));
        
        return groups.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    private ValidationHelper() {}
}
