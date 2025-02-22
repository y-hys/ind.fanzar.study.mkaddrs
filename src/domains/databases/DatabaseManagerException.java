package domains.databases;

// このクラスは、データベース管理サービスで発生した例外のラッパー、または独自例外を表します。
public final class DatabaseManagerException extends Exception {
    DatabaseManagerException(String message) {
        super(message);
    }
}
