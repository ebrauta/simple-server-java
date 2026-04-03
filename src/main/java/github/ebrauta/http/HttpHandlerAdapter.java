package github.ebrauta.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.middleware.MiddlewareChain;

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
        byte[] bytes = response.getBody().getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(response.getStatus(), bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
