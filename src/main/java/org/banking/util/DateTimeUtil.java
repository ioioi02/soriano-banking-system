package org.banking.util;

import org.banking.config.PropertyLoader;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String APP_TIMEZONE = PropertyLoader.getInstance().getProperty("app.timezone");

    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

    private DateTimeUtil() {
    }

    public static String formatToLocal(Instant instant) {
        return instant.atZone(ZoneId.of(APP_TIMEZONE)).format(DATE_TIME_PATTERN);
    }
}