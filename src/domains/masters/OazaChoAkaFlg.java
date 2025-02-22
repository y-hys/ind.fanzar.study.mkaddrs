package domains.masters;

// この列挙体は、大字・町_通称フラグの列挙値を定義します。
public enum OazaChoAkaFlg {
    NoAka(false, "0"),  // 通称ではない
    Aka(true, "1");     // 大字・町名に通称名を収録

    private final boolean flag;
    private final String id;

    public boolean getFlag() {
        return this.flag;
    }

    public String getId() {
        return this.id;
    }

    public static OazaChoAkaFlg valueOfId(String id) {
        for (var e : OazaChoAkaFlg.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private OazaChoAkaFlg(boolean flg, String id) {
        this.flag = flg;
        this.id   = id;
    }
}
