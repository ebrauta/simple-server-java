package github.ebrauta.service;

import github.ebrauta.model.Product;
import github.ebrauta.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    public Product createProduct(Product product) {
        return repository.save(product);
    }
}
