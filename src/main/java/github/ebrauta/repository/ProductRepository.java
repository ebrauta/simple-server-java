package github.ebrauta.repository;

import github.ebrauta.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll() {
        return products;
    }
    public Product save(Product product) {
        Long id = idGenerator.getAndIncrement();
        Product newProduct = new Product(id, product.name(), product.price(), product.active());
        products.add(newProduct);
        return newProduct;
    }
}
