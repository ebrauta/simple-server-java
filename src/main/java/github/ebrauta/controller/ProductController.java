package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.model.Product;
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
    private final String METHOD_NOT_ALLOWED = "{\"error\":\"Endpoint Não Permitido\"}";
    private final String METHOD_NOT_FOUND = "{\"error\":\"Endpoint Não Encontrado\"}";
    private final String PRODUCT_NOT_FOUND = "{\"error\":\"Produto Não Encontrado\"}";

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
        boolean endPointGeneral = pathParts.length == 2;
        boolean endPointWithID = pathParts.length == 3;
        if(endPointGeneral){
            handleCollection(exchange, method);
        } else if(endPointWithID){
            Long id = Long.parseLong(pathParts[2]);
            handleItem(exchange, method, id);
        } else {
            sendResponse(exchange, 404, METHOD_NOT_FOUND);
        }

    }

    private void writeResponse(HttpExchange exchange, String response) throws IOException {
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleCollection(HttpExchange exchange, String method) throws IOException{
        if ("GET".equals(method)) {
            handleGetGeneral(exchange);
        } else if("POST".equals(method)) {
            handlePostGeneral(exchange);
        } else {
            sendResponse(exchange, 405, METHOD_NOT_ALLOWED);
        }
    }

    private void handleGetGeneral(HttpExchange exchange) throws IOException {
        List<Product> products = service.getAllProducts();
        sendResponse(exchange, 200, JsonUtil.toJson(products));
    }
    private void handlePostGeneral(HttpExchange exchange) throws IOException {
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
        Product product = JsonUtil.fromJson(body);
        Product created = service.createProduct(product);
        sendResponse(exchange, 201, JsonUtil.toJson(created));
    }
    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        writeResponse(exchange, response);
    }
    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException{
        if("GET".equals(method)) {
            handleGetId(exchange, id);
        } else {
            sendResponse(exchange, 405, METHOD_NOT_ALLOWED);
        }
    }
    private void handleGetId(HttpExchange exchange, Long id) throws IOException {
        Product product = service.getProductById(id);
        if(product == null){
            sendResponse(exchange, 404, PRODUCT_NOT_FOUND );
            return;
        }
        sendResponse(exchange, 200, JsonUtil.toJson(product));
    }
}
