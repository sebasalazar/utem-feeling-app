package cl.utem.feeling.exception;

public class UtemException extends RuntimeException {

    private final int code;

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

    public int getCode() {
        return code;
    }
}
