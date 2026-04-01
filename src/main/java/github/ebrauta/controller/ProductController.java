package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.mapper.ProductMapper;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.JsonUtil;
import github.ebrauta.util.ResponseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController implements HttpHandler {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ResponseUtil.send(exchange, 404, ResponseUtil.error("Endpoint Não Encontrado"));
    }

    public void getAll(HttpExchange exchange) throws IOException {
        List<Product> products = service.getAllProducts();
        List<ProductResponseDTO> responseList = products.stream().map(ProductMapper::toResponse).toList();
        ResponseUtil.send(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseList)));
    }

    public void create(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(body);
        Product product = ProductMapper.toEntity(dto);
        Product created = service.createProduct(product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(created);
        ResponseUtil.send(exchange, 201, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }


    public void getById(HttpExchange exchange) throws IOException {
        Long id = (Long) exchange.getAttribute("id");
        Product product = service.getProductById(id);
        if (product == null) {
            ResponseUtil.send(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(product);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    public void delete(HttpExchange exchange) throws IOException {
        Long id = (Long) exchange.getAttribute("id");
        Product deleted = service.deleteProduct(id);
        if (deleted == null) {
            ResponseUtil.send(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(deleted);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    public void update(HttpExchange exchange) throws IOException {
        Long id = (Long) exchange.getAttribute("id");
        String body = readBody(exchange);
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(body);
        Product product = ProductMapper.toEntity(dto);
        Product updated = service.updateProduct(id, product);
        if (updated == null) {
            ResponseUtil.send(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    public void patch(HttpExchange exchange) throws IOException {
        Long id = (Long) exchange.getAttribute("id");
        String body = readBody(exchange);
        ProductPatch patch = JsonUtil.fromJsonPatch(body);
        Product updated = service.patchProduct(id, patch);
        if (updated == null) {
            ResponseUtil.send(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
            return;
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(JsonUtil.toJsonFromDTO(responseDTO)));
    }

    private String readBody(HttpExchange exchange) {
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }
}
