package domains.masters;

// この列挙体は、町字区分コードの列挙値を定義します。
public enum MachiazaType {
    OazaCho("1"),   // 大字・町
    Chome("2"),     // 丁目
    Koaza("3"),     // 小字
    None("4"),      // 大字・町/丁目/小字なし
    Street("5");    // 道路方式の住居表示における道路名

    private final String id;

    public String getId() {
        return this.id;
    }

    public static MachiazaType valueOfId(String id) {
        for (var e : MachiazaType.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private MachiazaType(String id) {
        this.id = id;
    }
}
