package date.time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;

import exceptions.InvalidDateException;
import exceptions.InvalidInputException;
import exceptions.InvalidTimeException;
import exceptions.NotLeapYearException;

/**
 * This class encapsulates a Date Time Parser. It contains methods to parse inputs into a
 * LocalDateTime object, as well as a checker for the format.
 *
 * @author Ong Han Yang
 */
public final class DateTimeParser {
    /** Reusable Invalid Input Exception for when the input command has an invalid date format */
    public static final InvalidInputException INVALID_DATE_TIME_FORMAT =
            new InvalidInputException("The date/time format is wrong! Please enter in this format: yyyy-mm-dd hh:mm");

    /**
     * Parses a string input in the form yyyy-mm-dd hh:mm, where dd is the day, mm is the month,
     * yyyy is the year and hh:mm is the 24 hour time, to produce a LocalDateTime object
     * with the provided date and time.
     *
     * Does not check for invalid date/time.
     *
     * @param input the string input in the form yyyy-mm-dd xx:xx .
     * @return the LocalDateTime corresponding to the input.
     */
    public static LocalDateTime parse(String input) {
        String[] split = input.split(" ", 2);
        int[] date = Arrays.stream(split[0]
                .split("-", 3))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] time = Arrays.stream(split[1]
                .split(":", 2))
                .mapToInt(Integer::parseInt)
                .toArray();
        return LocalDateTime.of(
                date[0],
                date[1],
                date[2],
                time[0],
                time[1]);
    }

    /**
     * Parses a string in the format of {@code hh:mm} to produce a LocalTime object with the specified
     * hour and minute.
     *
     * Does not check for invalid time.
     *
     * @param input a String in the form of {@code hh:mm}.
     * @return a LocalTime object with the specified time.
     */
    public static LocalTime parseTime(String input) {
        String[] split = input.split(":", 2);
        return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    /**
     * Checks if a given string input has a valid format to be converted into
     * a LocalDateTime using the DateTimeParser::parse method.
     *
     * @param input The string to be checked if it follows yyyy-mm-dd hh:mm .
     * @return true if there are no exceptions.
     * @throws InvalidInputException when a given format is wrong.
     */
    public static boolean checkValidFormat(String input) throws InvalidInputException {
        if (input.length() != 16) {
            throw INVALID_DATE_TIME_FORMAT;
        }
        // check for separators and numbers
        // 0123456789012345
        // yyyy-mm-dd xx:xx
        // if wrong format, return false
        for (int i = 0; i < input.length(); i++) {
            if (i == 4 || i == 7) {
                if (input.charAt(i) != '-') {
                    throw INVALID_DATE_TIME_FORMAT;
                }
            } else if (i == 10) {
                if (input.charAt(10) != ' ') {
                    throw INVALID_DATE_TIME_FORMAT;
                }
            } else if (i == 13) {
                if (input.charAt(13) != ':') {
                    throw INVALID_DATE_TIME_FORMAT;
                }
            } else { // for non divider indexes
                if (!Character.isDigit(input.charAt(i))) {
                    throw INVALID_DATE_TIME_FORMAT;
                }
            }
        }

        // check valid numbers for months and days
        // if invalid, throw Invalid Date Exception.
        int monthNum = Integer.parseInt(input.substring(5, 7));
        if (monthNum < 1 || monthNum > 12) {
            throw new InvalidDateException(String.format("There is no %d-th month!", monthNum));
        }
        Month month = Month.of(monthNum);
        Year year = Year.of(Integer.parseInt(input.substring(0, 4)));
        int dayNum = Integer.parseInt(input.substring(8, 10));
        if (dayNum < 1) {
            throw new InvalidDateException(String.format("There is no %d-th day!", dayNum));
        }
        if (!year.isLeap() && monthNum == 2) { // leap year
            if (dayNum > month.minLength()) {
                // throws not leap year exception
                throw NotLeapYearException.get(year.toString());
            }
        } else {
            if (dayNum > month.maxLength()) {
                // throws invalid date exception
                throw new InvalidDateException(
                        String.format("%s does not have a %d-th day!", month, dayNum));
            }
        }

        // check valid numbers for hours and min
        int hours = Integer.parseInt(input.substring(11, 13));
        int mins = Integer.parseInt((input.substring(14, 16)));
        boolean isValidHour = hours >= 0 && hours < 24;
        boolean isValidMin = mins >= 0 && mins < 60;
        if (isValidHour || isValidMin) {
            throw new InvalidTimeException(
                    String.format("There is no %d-th hour, %d-th minute!", hours, mins));
        }
        return true;
    }
}
