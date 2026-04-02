package github.ebrauta.controller;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.mapper.ProductMapper;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.JsonUtil;
import github.ebrauta.util.ResponseUtil;

import java.util.List;

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public Response getAll(Request request) {
        List<Product> products = service.getAllProducts();
        List<ProductResponseDTO> responseList = products.stream().map(ProductMapper::toResponse).toList();
        String response = JsonUtil.toJsonList(responseList, JsonUtil::toJson);
        return Response.ok(ResponseUtil.success(response));
    }

    public Response create(Request request) {
        ProductRequestDTO dto = JsonUtil.toRequestDTO(request.getBody());
        Product product = ProductMapper.toEntity(dto);
        Product created = service.createProduct(product);
        ProductResponseDTO responseDTO = ProductMapper.toResponse(created);
        String response = JsonUtil.toJson(responseDTO);
        return Response.created(ResponseUtil.success(response));
    }


    public Response getById(Request request){
        Long id = Long.parseLong(request.getParams("id"));
        Product product = service.getProductById(id);
        if (product == null) {
            return Response.notFound(ResponseUtil.error("Produto não Encontrado"));
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(product);
        String response = JsonUtil.toJson(responseDTO);
        return Response.ok(ResponseUtil.success(response));
    }

    public Response delete(Request request){
        Long id = Long.parseLong(request.getParams("id"));
        Product deleted = service.deleteProduct(id);
        if (deleted == null) {
            return Response.notFound(ResponseUtil.error("Produto não Encontrado"));
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(deleted);
        String response = JsonUtil.toJson(responseDTO);
        return Response.ok(ResponseUtil.success(response));
    }

    public Response update(Request request) {
        Long id = Long.parseLong(request.getParams("id"));
        ProductRequestDTO dto = JsonUtil.toRequestDTO(request.getBody());
        Product product = ProductMapper.toEntity(dto);
        Product updated = service.updateProduct(id, product);
        if(updated == null){
            return Response.notFound(ResponseUtil.error("Produto não Encontrado"));
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(updated);
        String response = JsonUtil.toJson(responseDTO);
        return Response.ok(ResponseUtil.success(response));
    }

    public Response patch(Request request) {
        Long id = Long.parseLong(request.getParams("id"));
        ProductPatch patch = JsonUtil.toPatch(request.getBody());
        Product patched = service.patchProduct(id, patch);
        if(patched == null){
            return Response.notFound(ResponseUtil.error("Produto não Encontrado"));
        }
        ProductResponseDTO responseDTO = ProductMapper.toResponse(patched);
        String response = JsonUtil.toJson(responseDTO);
        return Response.ok(ResponseUtil.success(response));
    }
}
