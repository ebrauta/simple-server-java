package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.util.LoggerUtil;

import java.io.IOException;

public class LoggingMiddleware implements Middleware{

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
}
