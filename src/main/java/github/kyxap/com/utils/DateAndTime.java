package github.kyxap.com.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
    public static String getCurDateTime() {
        final long currentTimeMillis = System.currentTimeMillis();

        // Convert to Instant
        final Instant instant = Instant.ofEpochMilli(currentTimeMillis);

        // Convert to LocalDateTime using a specific time zone (adjust as needed)
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        // Format the LocalDateTime
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }
}
