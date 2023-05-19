package cl.utem.vote.feeling.exception;

public class UtemException extends RuntimeException {

    private int code = 412;

    public UtemException() {
        super("Error al procesar");
        this.code = 412;
    }

    public UtemException(String message) {
        super(message);
        this.code = 412;
    }

    public UtemException(int code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
