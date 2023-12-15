package exceptions;

public class CreateException extends Exception {
    private String message;

    public CreateException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
