package org.example.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {


    public static String convertDateToSpanishDate(String americanDate){


        DateTimeFormatter americanFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(americanDate, americanFormatter);

        DateTimeFormatter spanishFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return date.format(spanishFormatter);
    }
}
