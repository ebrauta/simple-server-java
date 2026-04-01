package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.exception.ValidationException;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;

public class ExceptionMiddleware implements Middleware {
    @Override
    public void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException {
        try {
            chain.next(exchange);
        } catch (ValidationException e) {
            ResponseUtil.send(exchange, 400, ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            ResponseUtil.send(exchange, 500, ResponseUtil.error("Erro interno do Servidor"));
        }
    }
}
