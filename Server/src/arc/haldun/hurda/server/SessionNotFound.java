package arc.haldun.hurda.server;

public class SessionNotFound extends RuntimeException {
    public SessionNotFound(String message) {
        super(message);
    }
}
