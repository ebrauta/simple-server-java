package github.ebrauta.middleware;

import com.sun.net.httpserver.HttpExchange;
import github.ebrauta.exception.ValidationException;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ExceptionMiddleware implements Middleware {
    @Override
    public void handle(HttpExchange exchange, MiddlewareChain chain) throws IOException {
        try {
            chain.next(exchange);
        } catch (ValidationException e) {
            sendError(exchange, 400, e.getMessage());
        } catch (Exception e) {
            sendError(exchange, 500, "Erro interno do Servidor");
        }
    }
     private void sendError(HttpExchange exchange, int status, String message) throws IOException {
         CorsUtil.addCorsHeaders(exchange);
         String response = ResponseUtil.error(message);
         byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
         exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
         exchange.sendResponseHeaders(status, bytes.length);
         OutputStream os = exchange.getResponseBody();
         os.write(bytes);
         os.close();
     }
}
