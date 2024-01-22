package exceptions;

public class UpdateException extends Exception {

    private String message;

    public UpdateException(String message) {
        this.message = message;
    }

    public UpdateException(String message, Exception e) {
        super(e);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
