package github.ebrauta.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    public static void send(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    public static void sendNoContent(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
    }

    public static String success(String json) {
        return "{"
                + "\"success\": true,"
                + "\"data\": " + json + ","
                + "\"error\": null"
                + "}";
    }

    public static String error(String message) {
        return "{"
                + "\"success\": false,"
                + "\"data\": null ,"
                + "\"error\": \"" + message + "\""
                + "}";
    }
}
