package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController implements HttpHandler {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            CorsUtil.addCorsHeaders(exchange);
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        boolean isCollection = pathParts.length == 2;
        boolean isItem = pathParts.length == 3;

        if(isCollection){
            handleCollection(exchange, method);
            return;
        } else if(isItem){
            long id;
            try {
                id = Long.parseLong(pathParts[2]);
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, errorJson("ID Inválido"));
                return;
            }
            handleItem(exchange, method, id);
            return;
        } else {
            sendResponse(exchange, 404, errorJson("Endpoint Não Encontrado"));
        }

    }

    private void handleCollection(HttpExchange exchange, String method) throws IOException{
        if ("GET".equals(method)) {
            handleGetAll(exchange);
        } else if("POST".equals(method)) {
            handleCreate(exchange);
        } else {
            sendResponse(exchange, 405, errorJson("Método Não Permitido"));
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Product> products = service.getAllProducts();
        sendResponse(exchange, 200, JsonUtil.toJson(products));
    }
    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        Product product = JsonUtil.fromJson(body);
        Product created = service.createProduct(product);
        sendResponse(exchange, 201, JsonUtil.toJson(created));
    }
    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException{
        if("GET".equals(method)) {
            handleGetItem(exchange, id);
        } else if("DELETE".equals(method)) {
            handleDeleteItem(exchange, id);
        } else if("PUT".equals(method)){
            handleUpdateItem(exchange, id);
        } else if("PATCH".equals(method)) {
            handlePatchItem(exchange, id);
        } else {
            sendResponse(exchange, 405, errorJson("Método Não Permitido"));
        }
    }
    private void handleGetItem(HttpExchange exchange, Long id) throws IOException {
        Product product = service.getProductById(id);
        if(product == null){
            sendResponse(exchange, 404, errorJson("Produto Não Encontrado") );
            return;
        }
        sendResponse(exchange, 200, JsonUtil.toJson(product));
    }
    private void handleDeleteItem(HttpExchange exchange, Long id) throws IOException {
        Product deleted = service.deleteProduct(id);
        if(deleted == null){
            sendResponse(exchange, 404, errorJson("Produto Não Encontrado"));
            return;
        }
        sendResponse(exchange, 200, JsonUtil.toJson(deleted));
    }
    private void handleUpdateItem(HttpExchange exchange, Long id) throws IOException {
        String body = readBody(exchange);
        Product product = JsonUtil.fromJson(body);
        Product updated = service.updateProduct(id, product);
        if(updated == null){
            sendResponse(exchange, 404, errorJson("Produto Não Encontrado"));
            return;
        }
        sendResponse(exchange, 200, JsonUtil.toJson(updated));
    }
    private void handlePatchItem(HttpExchange exchange, Long id) throws IOException {
        String body = readBody(exchange);
        ProductPatch patch = JsonUtil.fromJsonPatch(body);
        Product updated = service.patchProduct(id, patch);
        if(updated == null){
            sendResponse(exchange, 404, errorJson("Produto Não Encontrado"));
            return;
        }
        sendResponse(exchange, 200, JsonUtil.toJson(updated));
    }
    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
    private String errorJson(String message) {
        return "{\"error\":\"" + message + "\"}";
    }
    private String readBody(HttpExchange exchange) throws IOException{
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }
}
