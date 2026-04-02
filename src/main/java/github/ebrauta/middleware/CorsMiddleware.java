package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;

public class CorsMiddleware implements Middleware{
    @Override
    public void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            ResponseUtil.sendNoContent(exchange);
            return;
        }
        chain.next(exchange);
    }
}
