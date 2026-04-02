package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public interface ControllerHandler extends HttpHandler {
    void handle(HttpExchange exchange) throws IOException;
}
