package misc.exceptions;

public class FormatException extends RuntimeException {

    public FormatException(String message, int lineNumber) {
        super(message);
    }
}


