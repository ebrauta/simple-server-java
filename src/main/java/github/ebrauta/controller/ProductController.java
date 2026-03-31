package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.exception.ValidationException;
import github.ebrauta.mapper.ProductMapper;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.JsonUtil;
import github.ebrauta.util.ResponseUtil;

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
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if ("OPTIONS".equals(method)) {
                CorsUtil.addCorsHeaders(exchange);
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            boolean isCollection = pathParts.length == 2;
            boolean isItem = pathParts.length == 3;

            if (isCollection) {
                handleCollection(exchange, method);
            } else if (isItem) {
                long id;
                try {
                    id = Long.parseLong(pathParts[2]);
                } catch (NumberFormatException e) {
                    sendResponse(exchange, 400, ResponseUtil.error("ID Inválido"));
                    return;
                }
                handleItem(exchange, method, id);
            } else {
                sendResponse(exchange, 404, ResponseUtil.error("Endpoint Não Encontrado"));
            }
        } catch (ValidationException e) {
            sendResponse(exchange, 400, ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            sendResponse(exchange, 500, ResponseUtil.error(e.getMessage()));
        }
    }

    private void handleCollection(HttpExchange exchange, String method) throws IOException {
        if ("GET".equals(method)) {
            handleGetAll(exchange);
        } else if ("POST".equals(method)) {
            handleCreate(exchange);
        } else {
            sendResponse(exchange, 405, ResponseUtil.error("Método Não Permitido"));
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Product> products = service.getAllProducts();
        List<ProductResponseDTO> responseList = products.stream().map(ProductMapper::toResponse).toList();
        sendResponse(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseList)));
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(body);
        Product product = ProductMapper.toEntity(dto);
        Product created = service.createProduct(product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(created);
        sendResponse(exchange, 201, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException {
        if (method == null) {
            sendResponse(exchange, 400, ResponseUtil.error("Método Inválido"));
            return;
        }
        switch (method) {
            case "GET" -> handleGetItem(exchange, id);
            case "DELETE" -> handleDeleteItem(exchange, id);
            case "PUT" -> handleUpdateItem(exchange, id);
            case "PATCH" -> handlePatchItem(exchange, id);
            default -> sendResponse(exchange, 405, ResponseUtil.error("Método Não Permitido"));
        }
    }

    private void handleGetItem(HttpExchange exchange, Long id) throws IOException {
        Product product = service.getProductById(id);
        if (product == null) {
            sendResponse(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(product);
        sendResponse(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private void handleDeleteItem(HttpExchange exchange, Long id) throws IOException {
        Product deleted = service.deleteProduct(id);
        if (deleted == null) {
            sendResponse(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(deleted);
        sendResponse(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private void handleUpdateItem(HttpExchange exchange, Long id) throws IOException {
        String body = readBody(exchange);
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(body);
        Product product = ProductMapper.toEntity(dto);
        Product updated = service.updateProduct(id, product);
        if (updated == null) {
            sendResponse(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        sendResponse(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private void handlePatchItem(HttpExchange exchange, Long id) throws IOException {
        String body = readBody(exchange);
        ProductPatch patch = JsonUtil.fromJsonPatch(body);
        Product updated = service.patchProduct(id, patch);
        if (updated == null) {
            sendResponse(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        sendResponse(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private String readBody(HttpExchange exchange) {
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }
}
