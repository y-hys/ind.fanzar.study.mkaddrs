package domains.masters;

// このクラスは、マスター操作時に発生した例外のラッパー、または独自例外を表します。
public final class InvalidMasterException extends Exception {
    InvalidMasterException(String message) {
        super(message);
    }
}
