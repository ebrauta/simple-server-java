package github.ebrauta.mapper;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequestDTO dto){
        return new Product(null, dto.getName(), dto.getPrice(), true);
    }

    public static ProductResponseDTO toResponse(Product product){
        return new ProductResponseDTO(product.id(), product.name(), product.price());
    }
}
