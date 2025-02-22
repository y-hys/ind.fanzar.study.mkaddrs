package domains.masters;

// この列挙体は、小字_通称フラグの列挙値を定義します。
public enum KoazaAkaCode {
    NoAka("0"),         // 通称ではない
    Aka("1"),           // 小字名に通称名を収録
    AkaKyoto("2"),      // 小字名に京都通り名を収録
    AkaGeneral("3");    // 通称名が電子国土基本図(地名情報)の字または通称)

    private final String id;

    public String getId() {
        return this.id;
    }

    public static KoazaAkaCode valueOfId(String id) {
        for (var e : KoazaAkaCode.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private KoazaAkaCode(String id) {
        this.id = id;
    }
}
