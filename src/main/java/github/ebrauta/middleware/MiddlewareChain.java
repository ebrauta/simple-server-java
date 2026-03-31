package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private int index = 0;
    private final ControllerHandler controller;
    public MiddlewareChain(List<Middleware> middlewares, ControllerHandler controller) {
        this.middlewares = middlewares;
        this.controller = controller;
    }
    public void next(HttpExchange exchange) throws IOException {
        if(index < middlewares.size()) {
            Middleware current = middlewares.get(index++);
            current.handle(exchange, this);
        } else {
            controller.handle(exchange);
        }
    }
}
