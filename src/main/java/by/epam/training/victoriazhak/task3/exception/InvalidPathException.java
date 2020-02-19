package by.epam.training.victoriazhak.task3.exception;

public class InvalidPathException extends Exception {
    public InvalidPathException() {
        super();
    }

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
