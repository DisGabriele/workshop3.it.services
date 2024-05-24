package it.paa.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
classe con metodo statico utilizzato con le date passate in stringa
in modo da poterle passare in 2 formati possibili: yyyy-mm-dd o dd-mm-yyyy
 */
public class DateStringParser {
    public static LocalDate parse(String date) throws Exception {
        LocalDate parsedDate = null;
        /*
        effettua un try catch di LocalDate.parse(date, formatter) per entrambi i formati; se falliscono tutti, ritorna eccezione.
        Se si volessero aggiungere altri tipi di formato, si può effettuare un try catch con quel formato.
         */
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
