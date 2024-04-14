package gg.soares.domain.helpers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DateHelper {

    public static Instant addMonthsToInstant(Instant instant, int months) {
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        YearMonth newYearMonth = YearMonth.of(year, month).plusMonths(months);
        int dayOfMonth = Math.min(localDate.getDayOfMonth(), newYearMonth.lengthOfMonth());
        LocalDate newDate = LocalDate.of(newYearMonth.getYear(), newYearMonth.getMonth(), dayOfMonth);
        return newDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public static Instant subtractMonthsToInstant(Instant instant, int months) {
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        YearMonth newYearMonth = YearMonth.of(year, month).minusMonths(months);
        int dayOfMonth = Math.min(localDate.getDayOfMonth(), newYearMonth.lengthOfMonth());
        LocalDate newDate = LocalDate.of(newYearMonth.getYear(), newYearMonth.getMonth(), dayOfMonth);
        return newDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public static Long getMonthsBetweenInstants(Instant start, Instant end) {
        LocalDate startDate = start.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.atZone(ZoneId.systemDefault()).toLocalDate();
        return YearMonth.from(startDate).until(YearMonth.from(endDate), ChronoUnit.MONTHS);
    }
}
