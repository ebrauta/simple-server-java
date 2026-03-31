package github.ebrauta.dto;

public class ProductResponseDTO {
    private final Long id;
    private final String name;
    private final Double price;

    public ProductResponseDTO(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
