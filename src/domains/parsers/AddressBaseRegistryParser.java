package domains.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domains.masters.PrefMaster;
import domains.masters.CityMaster;
import domains.masters.TownMaster;
import domains.masters.InstantiablePrefMaster;
import domains.masters.InstantiableCityMaster;
import domains.masters.InstantiableTownMaster;
import domains.masters.InvalidMasterException;
import domains.masters.ValidationHelper;

// このクラスは、アドレスベースレジストリのファイルパースを行うクラスです。
public final class AddressBaseRegistryParser implements FileParsable {

    // 文字列の正規化形式
    // 都道府県名英字、地方公共団体コードなどの、文字列（英字）項目や、文字列（数値）項目に対して正規化を行う形式。
    // 都道府県名、都道府県名カナなどの、文字列、文字列（全角カナ）項目に対しては使用しない。
    private static final Normalizer.Form NORMALIZATION_FORM = Normalizer.Form.NFKC;

    // 効力発生日、廃止日の日付フォーマット文字列
    //private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE;

    // このメソッドは、入力されたファイルパスのファイルを読み込み、都道府県マスターデータを生成します。
    @Override
    public Collection<PrefMaster> parsePrefFile(Path path) throws FileParserException {
        try {
            return parsePrefFileInternal(path);
        } catch (IOException | InvalidMasterException e) {
            throw new FileParserException(e.getMessage());
        }
    }
    private Collection<PrefMaster> parsePrefFileInternal(Path path) throws IOException, InvalidMasterException {
        List<PrefMaster> masters = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path)) {
            // ヘッダーを読み飛ばす
            var line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                var items = line.split(",", -1);
                
                var lgCode   = Normalizer.normalize(items[0], NORMALIZATION_FORM);
                var pref     = items[1];
                var prefKana = !items[2].isBlank() ? items[2] : null;
                var prefRoma = !items[3].isBlank() ? Normalizer.normalize(items[3], NORMALIZATION_FORM) : null;
                var efctDate = !items[4].isBlank() ? LocalDate.parse(Normalizer.normalize(items[4], NORMALIZATION_FORM), DATE_TIME_FORMATTER) : null;
                var abltDate = !items[5].isBlank() ? LocalDate.parse(Normalizer.normalize(items[5], NORMALIZATION_FORM), DATE_TIME_FORMATTER) : null;
                var remarks  = !items[6].isBlank() ? items[6] : null;

                var master = new InstantiablePrefMaster.Builder(lgCode, pref)
                    .prefKana(prefKana)
                    .prefRoma(prefRoma)
                    .efctDate(efctDate)
                    .abltDate(abltDate)
                    .remarks(remarks)
                    .build();

                masters.add(master);
            }
        }
        ValidationHelper.validatePrefMasters(masters);
        return masters;
    }

    // このメソッドは、入力されたファイルパスのファイルを読み込み、市区町村マスターデータを生成します。
    @Override
    public Collection<CityMaster> parseCityFile(Path path) throws FileParserException {
        try {
            return parseCityFileInternal(path);
        } catch (IOException | InvalidMasterException e) {
            throw new FileParserException(e.getMessage());
        } 
    }
    private Collection<CityMaster> parseCityFileInternal(Path path) throws IOException, InvalidMasterException {
        List<CityMaster> masters = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path)) {
            // ヘッダーを読み飛ばす
            var line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                var items = line.split(",", -1);

                var lgCode     = Normalizer.normalize(items[0], NORMALIZATION_FORM);
                var pref       = items[1];
                var prefKana   = !items[2].isBlank()  ? items[2] : null;
                var prefRoma   = !items[3].isBlank()  ? Normalizer.normalize(items[3], NORMALIZATION_FORM)  : null;
                var county     = !items[4].isBlank()  ? items[4] : null;
                var countyKana = !items[5].isBlank()  ? items[5] : null;
                var countyRoma = !items[6].isBlank()  ? Normalizer.normalize(items[6], NORMALIZATION_FORM)  : null;
                var city       = items[7];
                var cityKana   = !items[8].isBlank()  ? items[8] : null;
                var cityRoma   = !items[9].isBlank()  ? Normalizer.normalize(items[9], NORMALIZATION_FORM) : null;
                var ward       = !items[10].isBlank() ? items[10] : null;
                var wardKana   = !items[11].isBlank() ? items[11] : null;
                var wardRoma   = !items[12].isBlank() ? Normalizer.normalize(items[12], NORMALIZATION_FORM) : null;
                var efctDate   = !items[13].isBlank() ? LocalDate.parse(Normalizer.normalize(items[13], NORMALIZATION_FORM), DATE_TIME_FORMATTER) : null;
                var abltDate   = !items[14].isBlank() ? LocalDate.parse(Normalizer.normalize(items[14], NORMALIZATION_FORM), DATE_TIME_FORMATTER) : null;
                var remarks    = !items[15].isBlank() ? items[15] : null;
                
                var master = new InstantiableCityMaster.Builder(lgCode, pref, city)
                    .prefKana(prefKana)
                    .prefRoma(prefRoma)
                    .county(county)
                    .countyKana(countyKana)
                    .countyRoma(countyRoma)
                    .cityKana(cityKana)
                    .cityRoma(cityRoma)
                    .ward(ward)
                    .wardKana(wardKana)
                    .wardRoma(wardRoma)
                    .efctDate(efctDate)
                    .abltDate(abltDate)
                    .remarks(remarks)
                    .build();
                
                masters.add(master);
            }
        }
        ValidationHelper.validateCityMasters(masters);
        return masters;
    }

    // このメソッドは、入力されたファイルパスのファイルを読み込み、町丁目マスターデータを生成します。
    @Override
    public Collection<TownMaster> parseTownFile(Path path) throws FileParserException {
        try {
            var masters = parseTownFileInternal(path);
            return masters;
        } catch (IOException | InvalidMasterException e) {
            throw new FileParserException(e.getMessage());
        }
    }
    private Collection<TownMaster> parseTownFileInternal(Path path) throws IOException, InvalidMasterException {
        List<TownMaster> masters = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path)) {
            // ヘッダーを読み飛ばす
            var line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                var items = line.split(",", -1);

                var lgCode          = Normalizer.normalize(items[0], NORMALIZATION_FORM);
                var machiazaId      = Normalizer.normalize(items[1], NORMALIZATION_FORM);
                var machiazaType    = Normalizer.normalize(items[2], NORMALIZATION_FORM);
                var pref            = items[3];
                var prefKana        = !items[4].isBlank()  ? items[4] : null;
                var prefRoma        = !items[5].isBlank()  ? Normalizer.normalize(items[5], NORMALIZATION_FORM) : null;
                var county          = !items[6].isBlank()  ? items[6] : null;
                var countyKana      = !items[7].isBlank()  ? items[7] : null;
                var countyRoma      = !items[8].isBlank()  ? Normalizer.normalize(items[8], NORMALIZATION_FORM) : null;
                var city            = items[9];
                var cityKana        = !items[10].isBlank() ? items[10] : null;
                var cityRoma        = !items[11].isBlank() ? Normalizer.normalize(items[11], NORMALIZATION_FORM) : null;
                var ward            = !items[12].isBlank() ? items[12] : null;
                var wardKana        = !items[13].isBlank() ? items[13] : null;
                var wardRoma        = !items[14].isBlank() ? Normalizer.normalize(items[14], NORMALIZATION_FORM) : null;
                var oazaCho         = !items[15].isBlank() ? items[15] : null;
                var oazaChoKana     = !items[16].isBlank() ? items[16] : null;
                var oazaChoRoma     = !items[17].isBlank() ? Normalizer.normalize(items[17], NORMALIZATION_FORM) : null;
                var chome           = !items[18].isBlank() ? items[18] : null;
                var chomeKana       = !items[19].isBlank() ? items[19] : null;
                var chomeNumber     = !items[20].isBlank() ? Normalizer.normalize(items[20], NORMALIZATION_FORM) : null;
                var koaza           = !items[21].isBlank() ? items[21] : null;
                var koazaKana       = !items[22].isBlank() ? items[22] : null;
                var koazaRoma       = !items[23].isBlank() ? Normalizer.normalize(items[23], NORMALIZATION_FORM) : null;
                var machiazaDist    = !items[24].isBlank() ? items[24] : null;
                var rsdtAddrFlg     = Normalizer.normalize(items[25], NORMALIZATION_FORM);
                var rsdtAddrMtdCode = !items[26].isBlank() ? Normalizer.normalize(items[26], NORMALIZATION_FORM) : null;
                var oazaChoAkaFlg   = !items[27].isBlank() ? Normalizer.normalize(items[27], NORMALIZATION_FORM) : null;
                var koazaAkaCode    = !items[28].isBlank() ? Normalizer.normalize(items[28], NORMALIZATION_FORM) : null;
                var oazaChoGsiUncmn = !items[29].isBlank() ? Normalizer.normalize(items[29], NORMALIZATION_FORM) : null;
                var koazaGsiUncmn   = !items[30].isBlank() ? Normalizer.normalize(items[30], NORMALIZATION_FORM) : null;
                var statusFlg       = !items[31].isBlank() ? Normalizer.normalize(items[31], NORMALIZATION_FORM) : null;
                var wakeNumFlag     = !items[32].isBlank() ? Normalizer.normalize(items[32], NORMALIZATION_FORM) : null;
                var efctDate        = LocalDate.parse(Normalizer.normalize(items[33], NORMALIZATION_FORM), DATE_TIME_FORMATTER);
                var abltDate        = !items[34].isBlank() ? LocalDate.parse(items[34], DATE_TIME_FORMATTER) : null;
                var srcCode         = !items[35].isBlank() ? Normalizer.normalize(items[35], NORMALIZATION_FORM) : null;
                var postCode        = !items[36].isBlank() ? Normalizer.normalize(items[36], NORMALIZATION_FORM) : null;
                var remarks         = !items[37].isBlank() ? items[37] : null;
                
                var master = new InstantiableTownMaster.Builder(lgCode, 
                    machiazaId, 
                    machiazaType, 
                    pref, 
                    city, 
                    rsdtAddrFlg, 
                    efctDate)
                    .prefKana(prefKana)
                    .prefRoma(prefRoma)
                    .county(county)
                    .countyKana(countyKana)
                    .countyRoma(countyRoma)
                    .cityKana(cityKana)
                    .cityRoma(cityRoma)
                    .ward(ward)
                    .wardKana(wardKana)
                    .wardRoma(wardRoma)
                    .oazaCho(oazaCho)
                    .oazaChoKana(oazaChoKana)
                    .oazaChoRoma(oazaChoRoma)
                    .chome(chome)
                    .chomeKana(chomeKana)
                    .chomeNumber(chomeNumber)
                    .koaza(koaza)
                    .koazaKana(koazaKana)
                    .koazaRoma(koazaRoma)
                    .machiazaDist(machiazaDist)
                    .rsdtAddrMtdCode(rsdtAddrMtdCode)
                    .oazaChoAkaFlg(oazaChoAkaFlg)
                    .koazaAkaCode(koazaAkaCode)
                    .oazaChoGsiUncmn(oazaChoGsiUncmn)
                    .koazaGsiUncmn(koazaGsiUncmn)
                    .statusFlg(statusFlg)
                    .wakeNumFlg(wakeNumFlag)
                    .abltDate(abltDate)
                    .srcCode(srcCode)
                    .postCode(postCode)
                    .remarks(remarks)
                    .build();
                
                masters.add(master);
            }
        }
        ValidationHelper.validateTownMasters(masters);
        return masters;
    }
}
