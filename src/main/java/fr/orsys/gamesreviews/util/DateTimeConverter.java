package fr.orsys.gamesreviews.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {

    private DateTimeConverter() {}

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
