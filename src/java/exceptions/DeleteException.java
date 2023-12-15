package exceptions;

public class DeleteException extends Exception {
    private String message;

    public DeleteException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
