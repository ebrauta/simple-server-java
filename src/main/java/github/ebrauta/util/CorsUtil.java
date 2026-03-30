package github.ebrauta.util;

import com.sun.net.httpserver.HttpExchange;

public class CorsUtil {
    public static void addCorsHeaders(HttpExchange exchange){
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }
}
