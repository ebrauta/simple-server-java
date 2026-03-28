package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ProductController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response;
        if ("GET".equals(method)) {
            response = "Lista de produtos";
        } else {
            response = "Método não suportado";
            exchange.sendResponseHeaders(405, response.length());
            writeResponse(exchange, response);
            return;
        }
        exchange.sendResponseHeaders(200, response.length());
        writeResponse(exchange, response);
    }

    private void writeResponse(HttpExchange exchange, String response) throws IOException {
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
