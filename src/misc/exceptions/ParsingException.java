package misc.exceptions;

/**
 * A class representing a parsing error.
 * Exception could be illegal instruction format.
 */
public class ParsingException extends RuntimeException {

    public ParsingException(String message, int lineNumber) {
        super(message);
    }
}