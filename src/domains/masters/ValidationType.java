package domains.masters;

// ValidationHelper.validate() メソッドで文字列が指定された条件を満たしているかを検証するための列挙値を定義します。
//
// Nullable:     バリデーション対象の文字列が null です。指定しない場合は、null 以外です。
// Number:       バリデーション対象の文字列が数値で構成されています。
//               0-9 の数字が対象で、０-９の全角数字は対象外です。
// Alphabet:     バリデーション対象の文字列がアルファベットで構成されています。
//               a-z, A-Z のアルファベットが対象です。
//               ａ-ｚ, Ａ-Ｚ の全角アルファベットは対象外です。
// Sign:         バリデーション対象の文字列が記号で構成されています。全角記号は対象外です。
// FullKatakana: バリデーション対象の文字列が全角カタカナ、全角記号、全角数字、全角アルファベットで構成されています。
// AllCharcters: バリデーション対象の文字列に制限はありません。
//
// ValidationHelper.validate() メソッドに複数のバリデーション条件を指定した場合は、各条件を OR で結合します。
// 例) ValidationHelper.validate(text, ValidationType.Number, ValidationType.Alphabet) 
//     text は 数値またはアルファベットで構成されています。
enum ValidationType {
    Nullable,
    Number,
    Alphabet,
    Sign,
    FullKatakana,
    AllCharcters,
}
