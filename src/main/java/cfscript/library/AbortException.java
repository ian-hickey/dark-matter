package cfscript.library;
public class AbortException extends RuntimeException {
    public AbortException(String message) {
        super(message);
    }
    public AbortException() {
        super(StdLib.now().toString());
    }
}
