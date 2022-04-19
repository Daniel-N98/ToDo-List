package util;

import exceptions.InvalidDateTimeFormatException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateParserTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseDateTimeTest() throws InvalidDateTimeFormatException {
        LocalDateTime localDateTime = DateParser.parseStringToLocalDateTime("2025-05-05 15:45", "yyyy-MM-dd HH:mm");
        assertNotNull(localDateTime);
        assertEquals("2025-05-05T15:45", localDateTime.toString());
    }

    @Test
    void parseIncorrectFormatDateTimeTest(){
        Throwable exception = assertThrows(InvalidDateTimeFormatException.class, () -> DateParser.parseStringToLocalDateTime("01-05-2021 01:25", "yyyy-MM-DD HH:mm"));
        assertEquals("'01-05-2021 01:25' is an invalid format. yyyy-MM-DD HH:mm", exception.getMessage());
    }
}