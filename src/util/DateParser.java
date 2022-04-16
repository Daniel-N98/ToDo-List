package util;

import exceptions.InvalidDateTimeFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {

    public static LocalDateTime parseStringToLocalDateTime(String date, String format) throws InvalidDateTimeFormatException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(date, formatter);
        }catch (DateTimeParseException e){
            throw new InvalidDateTimeFormatException("'" + date + "' is an invalid format. " + format);
        }
    }
}
