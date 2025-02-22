package domains.masters;

// このクラスは、町字IDを表します。
public final class MachiazaId {
    // 町字IDの上位4桁(大字・町コード)
    private final String oazaChoId;
    public String getOazaChoId() { return oazaChoId; }

    // 町字IDの上位4桁(大字・町コード)の数値化
    private final int oazaChoValue;
    public int getOazaChoValue() { return oazaChoValue; }

    // 町字IDの下位3桁(町字コード)
    private final String chomeKoazaId;
    public String getKoazaChomeId() { return chomeKoazaId; }

    // 町字IDの下位3桁(町字コード)の数値化
    private final int chomeKoazaValue;
    public int getKoazaChomeValue() { return chomeKoazaValue; }

    // このメソッドは、この町字IDが大字・町を意味しているかどうかを判定します。
    public boolean isOazaCho() {
        return this.oazaChoValue >= 1;
    }

    // このメソッドは、この町字IDが丁目を意味しているかどうかを判定します。
    // 町字IDが丁目を意味している（TRUE）の場合、この町字IDは小字を意味しません（FALSE）。
    // 町字IDが丁目を意味していない（FALSE）の場合、この町字IDは小字を意味します（TRUE）。
    public boolean isChome() {
        return this.chomeKoazaValue >= 1 && this.chomeKoazaValue <= 100;
    }

    // このメソッドは、この町字IDが小字を意味しているかどうかを判定します。
    // 町字IDが小字を意味している（TRUE）の場合、この町字IDは丁目を意味しません（FALSE）。
    // 町字IDが小字を意味していない（FALSE）の場合、この町字IDは丁目を意味します（TRUE）。
    public boolean isKoaza() {
        return this.chomeKoazaValue >= 101;
    }

    public MachiazaId(String id) {
        this.oazaChoId       = id.substring(0, 4);
        this.oazaChoValue    = Integer.parseInt(this.oazaChoId);
        this.chomeKoazaId    = id.substring(4, 7);
        this.chomeKoazaValue = Integer.parseInt(this.chomeKoazaId);

        // ケースは存在しない
        if (this.isChome() && this.isKoaza()) {
            throw new UnsupportedOperationException();
        }
        //this.parentKey = parentKey;
    }
}
