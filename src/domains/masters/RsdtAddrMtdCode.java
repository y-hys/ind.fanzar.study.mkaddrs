package domains.masters;

// この列挙体は、住居表示方式コードの列挙値を定義します。
public enum RsdtAddrMtdCode {
    NotImplemented("0"),    // 住居表示でない
    Block("2"),             // 街区方式
    Streat("1");            // 道路方式

    private final String id;

    public String getId() {
        return this.id;
    }

    public static RsdtAddrMtdCode valueOfId(String id) {
        for (var e : RsdtAddrMtdCode.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private RsdtAddrMtdCode(String id) {
        this.id = id;
    }
}
