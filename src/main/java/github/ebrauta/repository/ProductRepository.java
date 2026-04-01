package github.ebrauta.repository;

import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    private List<Product> findAll() {
        return products;
    }
    public List<Product> findAllActive(){
        return findAll().stream().filter(Product::active).toList();
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
                .filter(p -> p.id().equals(id) && p.active())
                .findFirst()
                .orElse(null);
    }
    public Product remove(Long id){
        Product found = findById(id);
        if(found != null){
            Product removed = new Product(id, found.name(), found.price(), false);
            products.remove(found);
            products.add(removed);
            return removed;
        }
        return null;
    }
    public Product update(Long id, Product product){
        Product found = findById(id);
        if(found != null){
            Product updated = new Product(id, product.name(), product.price(), product.active());
            products.remove(found);
            products.add(updated);
            return updated;
        }
        return null;
    }
    public Product patch(Long id, ProductPatch patch){
        Product found = findById(id);
        if(found != null){
            Product patched = new Product(
                    id,
                    patch.getName() != null ? patch.getName() : found.name(),
                    patch.getPrice() != null ? patch.getPrice() : found.price(),
                    patch.getActive() != null ? patch.getActive() : found.active()
            );
            products.remove(found);
            products.add(patched);
            return patched;
        }
        return null;
    }
}
