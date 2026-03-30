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
    public List<Product> findAllActive(){
        return products.stream().filter(Product::active).toList();
    }
    public Product save(Product product) {
        Long id = idGenerator.getAndIncrement();
        Product newProduct = new Product(id, product.name(), product.price(), product.active());
        products.add(newProduct);
        return newProduct;
    }
    public Product findById(Long id){
        return products
                .stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElse(null);
    }
    public Product remove(Long id){
        for(int i = 0; i < products.size(); i++){
            Product p = products.get(i);
            if(p.id().equals(id)){
                Product updated = new Product(
                        p.id(),
                        p.name(),
                        p.price(),
                        false
                );
                products.set(i, updated);
                return updated;
            }
        }
        return null;
    }
    public Product update(Long id, Product product){
        for(int i = 0; i < products.size(); i++){
            Product existing = products.get(i);
            if(existing.id().equals(id)){
                Product updated = new Product(id, product.name(), product.price(), product.active());
                products.set(i, updated);
                return updated;
            }
        }
        return null;
    }
}
