package github.ebrauta.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void log(String method, String path, int status, long duration){
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String message = String.format("[ %s ] %s %s %s (%d ms)", timestamp, method, path, status, duration);
        System.out.println(message);
    }
}
