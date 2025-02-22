package domains.parsers;

// このクラスは、ファイルパースサービスで発生した例外のラッパー、または独自例外を表します。
public final class FileParserException extends Exception {
    FileParserException(String message) {
        super(message);
    }
}
