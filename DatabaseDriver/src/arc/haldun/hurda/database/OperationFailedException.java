package arc.haldun.hurda.database;

public class OperationFailedException extends Exception {
    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(Throwable cause) {
        super(cause);
    }
}
