package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface ControllerHandler {
    void handle(HttpExchange exchange) throws IOException;
}
