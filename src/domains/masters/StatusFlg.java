package domains.masters;

// この列挙体は、状態フラグの列挙値を定義します。
public enum StatusFlg {
    Waiting("0"),   // 自治体確認待ち
    Approved("1"),  // 地方自治法の町若しくは字に該当
    Rejected("2"),  // 地方自治法の町若しくは字に該当せず
    Unknown("3");   // 不明

    private final String id;

    public String getId() {
        return this.id;
    }

    public static StatusFlg valueOfId(String id) {
        for (var e : StatusFlg.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private StatusFlg(String id) {
        this.id = id;
    }
}
