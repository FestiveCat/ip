package exceptions;

/**
 * This class represents an exception raised when a Feb-29 date is given on a non-leap year.
 *
 * @author Ong Han Yang
 */
public class NotLeapYearException extends InvalidDateException {
    /**
     * Constructs a Not Leap Year Exception with a message.
     *
     * @param message the message to be carried by the exception.
     */
    private NotLeapYearException(String message) {
        super(message);
    }

    /**
     * Produces a Not Leap Year exception with a given year input.
     *
     * @param nonLeapYear the 4-digit year as an input to make an exception.
     * @return the exception that there is no leap year on the given year.
     */
    public static NotLeapYearException get(String nonLeapYear) {
        return new NotLeapYearException(
                String.format("%s is not a leap year! There is no Feb-29", nonLeapYear));
    }
}
