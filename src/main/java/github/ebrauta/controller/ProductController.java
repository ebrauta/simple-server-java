package github.ebrauta.controller;

import com.sun.net.httpserver.HttpExchange;
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

public class ProductController implements ControllerHandler {
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
        String response = JsonUtil.toJsonFromDTO(responseList);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(response));
    }

    public void create(HttpExchange exchange) throws IOException {
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(readBody(exchange));
        Product product = ProductMapper.toEntity(dto);
        Product created = service.createProduct(product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(created);
        String response = JsonUtil.toJsonFromDTO(responseDTO);
        ResponseUtil.send(exchange, 201, ResponseUtil.success(response));
    }


    public void getById(HttpExchange exchange) throws IOException {
        Product product = service.getProductById(getId(exchange));
        checkIfProductIsNull(exchange, product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(product);
        String response = JsonUtil.toJsonFromDTO(responseDTO);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(response));
    }

    public void delete(HttpExchange exchange) throws IOException {
        Product deleted = service.deleteProduct(getId(exchange));
        checkIfProductIsNull(exchange, deleted);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(deleted);
        String response = JsonUtil.toJsonFromDTO(responseDTO);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(response));
    }

    public void update(HttpExchange exchange) throws IOException {
        ProductRequestDTO dto = JsonUtil.fromJsonToDTO(readBody(exchange));
        Product product = ProductMapper.toEntity(dto);
        Product updated = service.updateProduct(getId(exchange), product);
        checkIfProductIsNull(exchange, updated);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        String response = JsonUtil.toJsonFromDTO(responseDTO);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(response));
    }

    public void patch(HttpExchange exchange) throws IOException {
        ProductPatch patch = JsonUtil.fromJsonPatch(readBody(exchange));
        Product updated = service.patchProduct(getId(exchange), patch);
        checkIfProductIsNull(exchange, updated);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        String response = JsonUtil.toJsonFromDTO(responseDTO);
        ResponseUtil.send(exchange, 200, ResponseUtil.success(response));
    }

    private String readBody(HttpExchange exchange) {
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }

    private Long getId(HttpExchange exchange){
        return (Long) exchange.getAttribute("id");
    }

    private void checkIfProductIsNull(HttpExchange exchange, Product product) throws IOException {
        if (product == null) {
            ResponseUtil.send(exchange, 404, ResponseUtil.error("Produto Não Encontrado"));
        }
    }
}
