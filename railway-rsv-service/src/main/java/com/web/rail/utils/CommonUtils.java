package com.web.rail.utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public interface CommonUtils {

    static String decryptString(String input) {
        // Decode the Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        // Convert bytes to original string
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
    static LocalDateTime getLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime);
    }
    static String convertDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
