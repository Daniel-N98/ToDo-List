package util;


import exceptions.InvalidDateTimeFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {

    /**
     * Parses the String parameter into a LocalDateTime variable using the format parameter
     *
     * @param date   to be parsed
     * @param format date format
     * @return LocalDateTime parsed LocalDateTime object
     * @throws InvalidDateTimeFormatException if the String date parameter cannot be parsed
     */
    public static LocalDateTime parseStringToLocalDateTime(String date, String format) throws InvalidDateTimeFormatException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(date.replace("T", " "), formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException("'" + date + "' is an invalid format. " + format);
        }
    }
}
