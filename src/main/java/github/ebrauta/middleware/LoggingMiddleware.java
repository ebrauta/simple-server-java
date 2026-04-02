package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.LoggerUtil;

import java.io.IOException;
import java.util.function.Function;

public class LoggingMiddleware implements Middleware{
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        long start = System.currentTimeMillis();
        Response response = next.apply(request);
        long duration = System.currentTimeMillis() - start;
        LoggerUtil.log(request.getMethod(), request.getPath(), response.getStatus(), duration);
        return response;
    }
/*
    @Override
    public void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException {
        long start = System.currentTimeMillis();
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        try {
            chain.next(exchange);
        } finally {
            long duration = System.currentTimeMillis() - start;
            int status = exchange.getResponseCode();
            LoggerUtil.log(method, path, status, duration);
        }
    }
 */
}
