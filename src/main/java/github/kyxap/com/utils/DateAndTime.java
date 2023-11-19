package github.kyxap.com.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
    public static String getCurDateTime() {
        long currentTimeMillis = System.currentTimeMillis();

        // Convert to Instant
        Instant instant = Instant.ofEpochMilli(currentTimeMillis);

        // Convert to LocalDateTime using a specific time zone (adjust as needed)
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        // Format the LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }
}
