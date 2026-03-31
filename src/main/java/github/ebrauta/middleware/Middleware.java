package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Middleware {
    void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException;
}
