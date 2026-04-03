package github.ebrauta.controller;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.mapper.ProductMapper;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.json.JsonMapper;

import java.util.List;

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public Response getAll(Request ignoredRequest) {
        List<Product> products = service.getAllProducts();
        List<ProductResponseDTO> responseList = products.stream().map(ProductMapper::toResponse).toList();
        String response = JsonMapper.toJsonList(responseList, JsonMapper::toJson);
        return Response.ok(response);
    }

    public Response create(Request request) {
        ProductRequestDTO dto = JsonMapper.toRequestDTO(request.getBody());
        Product product = ProductMapper.toEntity(dto);
        Product created = service.createProduct(product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(created);
        String response = JsonMapper.toJson(responseDTO);
        return Response.created(response);
    }


    public Response getById(Request request){
        Long id = Long.parseLong(request.getParam("id"));
        Product product = service.getProductById(id);
        if (product == null) {
            return Response.productNotFound();
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(product);
        String response = JsonMapper.toJson(responseDTO);
        return Response.ok(response);
    }

    public Response delete(Request request){
        Long id = Long.parseLong(request.getParam("id"));
        Product deleted = service.deleteProduct(id);
        if (deleted == null) {
            return Response.productNotFound();
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(deleted);
        String response = JsonMapper.toJson(responseDTO);
        return Response.ok(response);
    }

    public Response update(Request request) {
        Long id = Long.parseLong(request.getParam("id"));
        ProductRequestDTO dto = JsonMapper.toRequestDTO(request.getBody());
        Product product = ProductMapper.toEntity(dto);
        Product updated = service.updateProduct(id, product);
        if(updated == null){
            return Response.productNotFound();
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        String response = JsonMapper.toJson(responseDTO);
        return Response.ok(response);
    }

    public Response patch(Request request) {
        Long id = Long.parseLong(request.getParam("id"));
        ProductPatch patch = JsonMapper.toPatch(request.getBody());
        Product patched = service.patchProduct(id, patch);
        if(patched == null){
            return Response.productNotFound();
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(patched);
        String response = JsonMapper.toJson(responseDTO);
        return Response.ok(response);
    }
}
