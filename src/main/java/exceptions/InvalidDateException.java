package exceptions;

/**
 * This class represents an invalid date exception, thrown when an invalid date is given as an input.
 * Examples of invalid dates: 2022-01-90
 *
 * @author Ong Han Yang
 */
public class InvalidDateException extends InvalidInputException {
    /**
     * Constructs an Invalid Date Exception with a message.
     *
     * @param message the message to be carried by the exception.
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
