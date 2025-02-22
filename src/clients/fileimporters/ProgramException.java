package clients.fileimporters;

// このクラスは、Programクラスで発生した例外のラッパー、または独自例外を表します。
final class ProgramException extends Exception {
    ProgramException(String message) {
        super(message);
    }
}
