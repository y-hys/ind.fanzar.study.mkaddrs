package domains.masters;

// この列挙体は、起番フラグの列挙値を定義します。
public enum WakeNumFlg {
    NotRegistered("0"), // 登記されていない
    Applied("1"),       // 起番
    NotApplied("2");    // 非起番

    private final String id;

    public String getId() {
        return this.id;
    }

    public static WakeNumFlg valueOfId(String id) {
        for (var e : WakeNumFlg.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private WakeNumFlg(String id) {
        this.id = id;
    }
}
