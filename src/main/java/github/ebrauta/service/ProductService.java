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
        return repository.findAllActive();
    }
    public Product createProduct(Product product) {
        return repository.save(product);
    }
    public Product getProductById(Long id){return repository.findById(id);}
    public Product deleteProduct(Long id){return repository.remove(id);}
    public Product updateProduct(Long id, Product product){return repository.update(id, product);}
}
