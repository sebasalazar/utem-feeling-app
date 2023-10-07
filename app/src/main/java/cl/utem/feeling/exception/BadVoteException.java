package cl.utem.feeling.exception;

public class BadVoteException extends RuntimeException {

    public BadVoteException() {
        super("Token inválido");
    }

    public BadVoteException(String message) {
        super(message);
    }
}
