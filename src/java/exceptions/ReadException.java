package exceptions;

public class ReadException extends Exception {
    private String message;

    public ReadException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
