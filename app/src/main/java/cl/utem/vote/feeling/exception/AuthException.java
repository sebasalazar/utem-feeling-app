package cl.utem.vote.feeling.exception;

public class AuthException extends RuntimeException {

    public AuthException() {
        super("Autenticación fallida");
    }

    public AuthException(String message) {
        super(message);
    }
}
