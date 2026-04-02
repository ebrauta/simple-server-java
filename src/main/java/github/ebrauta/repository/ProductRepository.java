package github.ebrauta.repository;

import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;
import github.ebrauta.util.FileUtil;
import github.ebrauta.util.JsonUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private final List<Product> products;
    private final String FILE_PATH = "src/main/resources/data/products.json";
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductRepository() {
        String json = FileUtil.read(FILE_PATH);
        this.products = JsonUtil.toProductList(json);
    }

    private void persist(){
        String json = JsonUtil.toJsonList(products, JsonUtil::toJson);
        FileUtil.write(FILE_PATH, json);
    }

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
        persist();
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
            persist();
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
            persist();
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
            persist();
            return patched;
        }
        return null;
    }
}
