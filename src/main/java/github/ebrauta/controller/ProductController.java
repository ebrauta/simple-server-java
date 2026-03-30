package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.model.Product;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController implements HttpHandler {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            handleGet(exchange);
        } else if("POST".equals(method)) {
            handlePost(exchange);
        } else {
            sendResponse(exchange, 405, "Método não suportado");
        }
    }

    private void writeResponse(HttpExchange exchange, String response) throws IOException {
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        List<Product> products = service.getAllProducts();
        String response = JsonUtil.toJson(products);
        sendResponse(exchange, 200, response);
    }
    private void handlePost(HttpExchange exchange) throws IOException {
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
        Product product = JsonUtil.fromJson(body);
        Product created = service.createProduct(product);
        String response = JsonUtil.toJson(created);
        sendResponse(exchange, 201, response);
    }

    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.length());
        writeResponse(exchange, response);
    }
}
