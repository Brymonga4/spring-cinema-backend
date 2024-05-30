package org.example.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateComparison {


    public static boolean compareZonedDateTime(ZonedDateTime madridTime, LocalDateTime dbTime){

       // ZonedDateTime timeInUTCConvertedToMadrid = dbTime.withZoneSameInstant(ZoneId.of("Europe/Madrid"));
       // timeInUTCConvertedToMadrid = timeInUTCConvertedToMadrid.minusHours(2);

      //  return timeInUTCConvertedToMadrid.isAfter(madridTime);

        return true;
    }
}
