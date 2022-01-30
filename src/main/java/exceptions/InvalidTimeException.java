package exceptions;

/**
 * This class represents an invalid time exception, a subclass of the invalid input exception.
 * This is thrown when a given time is not possible, ie: "25:00" or "12:-1"
 *
 * @author Ong Han Yang
 */
public class InvalidTimeException extends InvalidInputException {
    /**
     * Constructs an Invalid Time Exception with a message.
     *
     * @param message the message to be carried by the exception.
     */
    public InvalidTimeException(String message) {
        super(message);
    }
}
