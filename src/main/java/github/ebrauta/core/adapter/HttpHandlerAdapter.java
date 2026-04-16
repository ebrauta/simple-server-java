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
        Response response = chain.apply(request);
        for(var entry: response.getHeaders().entrySet()) {
            exchange.getResponseHeaders().set(entry.getKey(), entry.getValue());
        }
        byte[] bytes = response.onJsonFormat().getBytes(StandardCharsets.UTF_8);
        int exchangeSize = response.getStatus() != HttpStatus.NO_CONTENT.getCode() ? bytes.length : -1;
        exchange.sendResponseHeaders(response.getStatus(), exchangeSize);
        try (OutputStream os = exchange.getResponseBody()){
            os.write(bytes);
        }
    }
}
