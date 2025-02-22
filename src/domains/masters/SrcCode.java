package domains.masters;

// この列挙体は、原典資料コードの列挙値を定義します。
public enum SrcCode {
    Other("0"),             // その他資料
    LocalGov("1"),          // 自治体資料
    NLID_Other("10"),       // 位置参照情報・その他
    NLID_Town("11"),        // 位置参照情報・自治体資料
    NLID_Block("12"),       // 位置参照情報・街区レベル
    NLID_Residence("13");   // 位置参照情報・1/2500地形図

    private final String id;

    public String getId() {
        return this.id;
    }

    public static SrcCode valueOfId(String id) {
        for (var e : SrcCode.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private SrcCode(String id) {
        this.id = id;
    }
}
