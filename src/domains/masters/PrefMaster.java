package domains.masters;

import java.time.LocalDate;

// 01 都道府県
//
// [注1]
// 廃止（過去）レコードを保持したい場合等に「効力発生日」「廃止日」の項目を使用する。
public interface PrefMaster {
    // データキー
    String getKey();

    // 全国地方公共団体コード
    // 町字の上位階層の行政区域となる市区町村を一意に識別するためのコード。
    // 総務省「全国地方公共団体コード」に従って6桁のコードを収録。
    //
    // キー（変更不可）、必須項目(Not Null)
    // 6桁の文字列(数値)
    String getLgCode();

    // 都道府県名
    // 都道府県の名称。
    // JIS X 0401及び総務省「全国地方公共団体コード」に従って収録。
    //
    // 必須項目(Not Null)
    // 10桁までの文字列
    String getPref();

    // 都道府県名カナ
    // 都道府県名の読みの全角カタカナ表記。
    // 総務省「全国地方公共団体コード」に従って収録。
    //
    // 50桁までの文字列（全角カナ）
    String getPrefKana();

    // 都道府県名英字
    // 都道府県名の英字表記。
    // 「地名等の英語表記規程」（平成28年国地達第10号）に従って収録
    // （固有名称部分のみ収録、但し北海道は「Hokkaido」とする）。
    //
    // 50桁までの文字列（英字）
    String getPrefRoma();

    // 効力発生日
    // 町字の新設または名称変更の実施日。
    // ※月まではわかるが日がわからない場合は”DD”に”01”を収録する。　（注1）
    LocalDate getEfctDate();

    // 廃止日
    // 町字の廃止の実施日、または名称変更の告示による旧名称の廃止の実施日。
    // ※月まではわかるが日がわからない場合は”DD”に”01”を収録する。　（注1）
    LocalDate getAbltDate();

    // 備考
    // 特記事項があれば記載。
    //
    // 256桁までの文字列
    String getRemarks();

    // 都道府県マスターデータをコピーして新しい都道府県マスターデータを生成します。
    public static PrefMaster of(PrefMaster source) throws InvalidMasterException {
        return ImmutablePrefMaster.of(source);
    }
}
