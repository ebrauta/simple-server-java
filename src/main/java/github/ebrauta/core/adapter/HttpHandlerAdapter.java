package github.ebrauta.core.adapter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.core.http.HttpStatus;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.MiddlewareChain;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpHandlerAdapter implements HttpHandler {
    private final MiddlewareChain chain;

    public HttpHandlerAdapter(MiddlewareChain chain) {
        this.chain = chain;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Request request = Request.from(exchange);
        Response response;
        try {
            response = chain.apply(request);
        } catch (Exception e) {
            response = Response.serverError(e.getMessage());
        }
        if (response == null) {
            response = Response.serverError("Resposta nula");
        }
        for(var entry: response.getHeaders().entrySet()) {
            exchange.getResponseHeaders().add(entry.getKey(), entry.getValue());
        }
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        byte[] bytes = new byte[0];
        int statusCode = response.getStatus();
        if (statusCode != HttpStatus.NO_CONTENT.getCode()){
            bytes = response.toJson().getBytes(StandardCharsets.UTF_8);
        }
        int length = (statusCode != HttpStatus.NO_CONTENT.getCode() ? bytes.length : -1) ;
        exchange.sendResponseHeaders(statusCode, length);
        if(length != -1){
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}
