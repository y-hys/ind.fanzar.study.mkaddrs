package domains.masters;

// この列挙体は、住居表示方式コードの列挙値を定義します。
public enum RsdtAddrFlg {
    NotImplemented(false, "0"), // 住居表示実施
    Implemented(true, "1");     // 住居表示未実施

    private final boolean flag;
    private final String id;

    public boolean getFlag() {
        return this.flag;
    }
    public String getId() {
        return this.id;
    }

    public static RsdtAddrFlg valueOfId(String id) {
        for (var e : RsdtAddrFlg.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private RsdtAddrFlg(boolean flg, String id) {
        this.flag = flg;
        this.id   = id;
    }
}
