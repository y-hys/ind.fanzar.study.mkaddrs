package domains.masters;

import java.time.LocalDate;

// 03 町字
//
// [注1]
// 住居表示実施か住居表示非実施かに関わらず、地方自治法260条に基づく町字の区域の単位で一意のIDを設定する。
// データ更新時に同一の町字に対する町字IDは同じ値が継承される。なお、本フォーマット（町字マスター）では、区域の変更、名称の変更での町字IDの振り直しは想定しない。
// 丁目・小字の3桁については以下の範囲で使用する。
// "000"　：丁目、小字ともなしの場合
// "001"～"100"　：丁目の場合（丁目数字をそのまま使用し、左ゼロ埋めで収録する）
// "101"～"999"　：小字の場合（重複なく付番し、収録する）
// 大字がない（小字のみまたは小字もない）場合は、大字・町の4桁を"0000"とする。
// 大字・町も小字もなく、市区町村に続いて地番の表示となる場合は、地番に対する親レコードとして町字IDの7桁を"0000000"のレコードを収録する。
//
// [注2]
// 小字が地番区域の場合は、小字を収録することが望ましい。
//
// [注3]
// 地方自治法260条に基づく町字とは別に、通称名が住所・所在地の表示のため普及している場合には、大字・町名、小字名に通称名を収録する。
// 大字・町の階層が通称の場合（小字の階層は空欄または小字の階層にも通称を収録）、大字・町の階層が通称でなく小字の階層が通称の場合、を想定している。
// なお、このデータセットでは京都の通り名の収録は想定しない（必要な場合は別データで保有する）。
//
// [注4]
// （例）四文字の地名で三文字目が外字の場合「*_*_E001_*」　（電子国土基本図（地名情報）の仕様を踏襲）
//
// [注5]
// 廃止（過去）レコードを保持したい場合、または実施日が未来だが実施確定しているレコードを保持したい場合（公示済みで未定の換地処分日が実施日ケース等）に、「効力発生日」「廃止日」の項目を使用する。
public interface WritableTownMaster extends TownMaster {
    // 1. 全国地方公共団体コード

    // 2. 町字ＩＤ

    // 3. 町字区分コード
    void setMachiazaType(String machiazaType);

    // 4. 都道府県名
    void setPref(String pref);

    // 5. 都道府県名カナ
    void setPrefKana(String prefKana);

    // 6. 都道府県名英字
    void setPrefRoma(String prefRoma);

    // 7. 郡名
    void setCounty(String county);

    // 8. 郡名カナ
    void setCountyKana(String countyKana);

    // 9. 郡名英字
    void setCountyRoma(String countyRoma);

    // 10. 市区町村名
    void setCity(String city);

    // 11. 市区町村名カナ
    void setCityKana(String cityKana);

    // 12. 市区町村名英字
    void setCityRoma(String cityRoma);

    // 13. 政令市区名
    void setWard(String ward);

    // 14. 政令市区名カナ
    void setWardKana(String wardKana);

    // 15. 政令市区名英字
    void setWardRoma(String wardRoma);

    // 16. 大字・町名
    void setOazaCho(String oazaCho);

    // 17. 大字・町名カナ
    void setOazaChoKana(String oazaChoKana);

    // 18. 大字・町名英字
    void setOazaChoRoma(String oazaChoRoma);

    // 19. 丁目名
    void setChome(String chome);

    // 20. 丁目名カナ
    void setChomeKana(String chomeKana);

    // 21. 丁目名数字
    void setChomeNumber(String chomeNumber);

    // 22. 小字名
    void setKoaza(String koaza);

    // 23. 小字名カナ
    void setKoazaKana(String koazaKana);

    // 24. 小字名英字
    void setKoazaRoma(String koazaRoma);

    // 25. 同一町字識別情報
    void setMachiazaDist(String machiazaDist);

    // 26. 住居表示フラグ
    void setRsdtAddrFlg(String rsdtAddrFlg);

    // 27. 住居表示方式コード
    void setRsdtAddrMtdCode(String rsdtAddrMtdCode);

    // 28. 大字・町_通称フラグ
    void setOazaChoAkaFlg(String oazaChoAkaFlg);

    // 29. 小字_通称フラグ
    void setKoazaAkaCode(String koazaAkaCode);

    // 30. 大字・町_外字フラグ
    void setOazaChoGsiUncmn(String oazaChoGsiUncmn);

    // 31. 小字_外字フラグ
    void setKoazaGsiUncmn(String koazaGsiUncmn);

    // 32. 状態フラグ
    void setStatusFlg(String statusFlg);

    // 33. 起番フラグ
    void setWakeNumFlg(String wakeNumFlag);

    // 34. 効力発生日
    void setEfctDate(LocalDate efctDate);

    // 35. 廃止日
    void setAbltDate(LocalDate abltDate);

    // 36. 原典資料コード
    void setSrcCode(String srcCode);

    // 37. 郵便番号
    void setPostCode(String postCode);

    // 38. 備考
    void setRemarks(String remarks);
}
