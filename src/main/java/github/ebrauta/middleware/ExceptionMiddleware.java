package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.exception.ValidationException;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.util.function.Function;

public class ExceptionMiddleware implements Middleware {
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        try {
            return next.apply(request);
        } catch (ValidationException e) {
            return Response.badRequest(ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            return Response.serverError(ResponseUtil.error(e.getMessage()));
        }
    }
    /*
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
     */
}
