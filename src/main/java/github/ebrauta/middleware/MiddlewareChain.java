package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.controller.ControllerHandler;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class MiddlewareChain {
    private final List<Middleware> middlewares;

    public MiddlewareChain(List<Middleware> middlewares) {
        this.middlewares = middlewares;
    }
    public Function<Request, Response> build(Function<Request, Response> handler) {
        Function<Request, Response> next = handler;
        for(int i = middlewares.size() - 1; i >= 0; i--){
            Middleware middleware = middlewares.get(i);
            Function<Request,Response> currentNext = next;
            next = request -> middleware.apply(request, currentNext);
        }
        return next;
    }

    //private int index = 0;
    //private final ControllerHandler controller;
    /*public MiddlewareChain(List<Middleware> middlewares, ControllerHandler controller) {
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
    }*/
}
