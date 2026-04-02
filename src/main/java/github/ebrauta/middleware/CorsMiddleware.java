package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.util.function.Function;

public class CorsMiddleware implements Middleware{
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        return null;
    }

    /*
    @Override
    public void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            ResponseUtil.sendNoContent(exchange);
            return;
        }
        chain.next(exchange);
    }

     */
}
