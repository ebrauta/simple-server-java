package github.ebrauta.middleware;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

import java.util.function.Function;

public interface Middleware {
    //void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException;
    Response apply(Request request, Function<Request, Response> next);
}
