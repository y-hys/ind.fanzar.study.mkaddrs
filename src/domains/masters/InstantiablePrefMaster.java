package domains.masters;

import java.time.LocalDate;

// このクラスは、生成可能な都道府県マスターを表します。
// コンストラクタの代わりに、ビルダーを使用してインスタンスを生成可能です。
// 各フィールドに対するセッターメソッドは提供されておらず、生成後は基本的に変更できません。
public final class InstantiablePrefMaster implements PrefMaster {
    private final String key;
    public String getKey() { return this.key; }

    private final String lgCode;
    public String getLgCode() { return this.lgCode; }

    private String pref;
    public String getPref() { return this.pref; }

    private String prefKana;
    public String getPrefKana() { return this.prefKana; }

    private String prefRoma;
    public String getPrefRoma() { return this.prefRoma; }

    private LocalDate efctDate;
    public LocalDate getEfctDate() { return this.efctDate; }

    private LocalDate abltDate;
    public LocalDate getAbltDate() { return this.abltDate; }

    private String remarks;
    public String getRemarks() { return this.remarks; }

    public final static class Builder {
        private final String key;

        private final String lgCode;

        private final String pref;

        private String prefKana;
        public Builder prefKana(String prefKana) throws InvalidMasterException {
            ValidationHelper.validatePrefKana(prefKana, this.key);
            this.prefKana = prefKana;
            return this;
        }

        private String prefRoma;
        public Builder prefRoma(String prefRoma) throws InvalidMasterException {
            ValidationHelper.validatePrefRoma(prefRoma, this.key);
            this.prefRoma = prefRoma;
            return this;
        }

        private LocalDate efctDate;
        public Builder efctDate(LocalDate efctDate) {
            this.efctDate = efctDate;
            return this;
        }

        private LocalDate abltDate;
        public Builder abltDate(LocalDate abltDate) {
            this.abltDate = abltDate;
            return this;
        }

        private String remarks;
        public Builder remarks(String remarks) throws InvalidMasterException {
            ValidationHelper.validateRemarks(remarks, this.key);
            this.remarks = remarks;
            return this;
        }

        public InstantiablePrefMaster build() {
            var master      = new InstantiablePrefMaster(this.key, this.lgCode, this.pref);
            master.prefKana = this.prefKana;
            master.prefRoma = this.prefRoma;
            master.efctDate = this.efctDate;
            master.abltDate = this.abltDate;
            master.remarks  = this.remarks;
            return master;
        }

        public Builder(String lgCode, String pref) throws InvalidMasterException {
            var key = lgCode;

            ValidationHelper.validateLgCode(lgCode, key);
            this.lgCode = lgCode;

            ValidationHelper.validatePref(lgCode, key);
            this.pref = pref;
            
            this.key = key;
        }
    }

    private InstantiablePrefMaster(String key, String lgCode, String pref) {
        this.key    = key;
        this.lgCode = lgCode;
        this.pref   = pref;
    }

    public static InstantiablePrefMaster of(PrefMaster source) throws InvalidMasterException {
        return new Builder(source.getLgCode(), source.getPref())
            .prefKana(source.getPrefKana())
            .prefRoma(source.getPrefRoma())
            .efctDate(source.getEfctDate())
            .abltDate(source.getAbltDate())
            .remarks(source.getRemarks())
            .build();
    }
}
