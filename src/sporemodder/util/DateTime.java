package sporemodder.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTime {
    private DateTime() {}

    private static final String BASE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String BASE_ZONEID = "UTC";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(BASE_PATTERN).withZone(ZoneId.of(BASE_ZONEID));
    
    public enum DateTimeType {
            YEAR1,
            INSTANCEID
    }

    // public static void setZoneID(String zoneID) {
    //     DateTimeFormatter.ofPattern(BASE_PATTERN).withZone(ZoneId.of(zoneID));
    // }

    public static LocalDateTime parseDateTime(String input) {
        return LocalDateTime.parse(input, dateTimeFormatter);
    }

    public static String toDateTime(long value) {
        return dateTimeFormatter.format(Instant.ofEpochSecond(value));
    }

    private static long getSeconds(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static long toLong(String input, DateTimeType type) {
		LocalDateTime dateTime = DateTime.parseDateTime(input);
        long seconds = getSeconds(dateTime);
        switch (type) {
            case YEAR1:         return seconds + 62135683200L;
            case INSTANCEID:    return seconds - 1104534000L;
            default: return seconds;
        }
    }

    public static String toDateTime(long value, DateTimeType type) {
        switch (type) {
            case YEAR1:         return toDateTime(value - 62135683200L);
            case INSTANCEID:    return toDateTime(value + 1104534000L);
            default: return toDateTime(value);
        }
    }
}
