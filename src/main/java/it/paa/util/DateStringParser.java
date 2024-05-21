package it.paa.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateStringParser {
    public static LocalDate parse(String date) throws Exception {
        LocalDate parsedDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            parsedDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                parsedDate = LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e2) {
                throw new Exception("invalid date format");
            }
        }
        return parsedDate;
    }
}
