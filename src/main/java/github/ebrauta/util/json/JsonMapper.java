package github.ebrauta.util.json;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonMapper {
    public static String toJson(Product product) {
        return JsonWriter.createJson(product.id(), product.name(), product.price(), product.active());
    }

    public static String toJson(ProductResponseDTO dto) {
        return JsonWriter.createJson(dto.id(), dto.name(), dto.price(), true);
    }

    public static <T> String toJsonList(List<T> list, Function<T, String> mapper) {
        return list.stream().map(mapper).collect(Collectors.joining(",", "[", "]"));
    }

    public static List<Product> toProductList(String json) {
        List<Product> list = new ArrayList<>();
        if (json == null || json.isBlank() || json.equals("[]")) {
            return list;
        }
        String cleaned = json.trim().substring(1, json.length() - 1);
        String[] items = cleaned.split("},\\{");
        for (String item : items) {
            if (!item.startsWith("{")) item = "{" + item;
            if (!item.endsWith("}")) item = item + "}";
            Product product = toProduct(item);
            list.add(product);
        }
        return list;
    }

    public static Product toProduct(String json) {
        Map<String, String> map = JsonParser.parse(json);
        return new Product(JsonParser.parseLong(map.get("id")), JsonParser.parseString(map.get("name")), JsonParser.parseDouble(map.get("price")), JsonParser.parseBoolean(map.get("active")));
    }

    public static ProductRequestDTO toRequestDTO(String json) {
        Map<String, String> map = JsonParser.parse(json);
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(JsonParser.parseString(map.get("name")));
        dto.setPrice(JsonParser.parseDouble(map.get("price")));
        return dto;
    }

    public static ProductPatch toPatch(String json) {
        Map<String, String> map = JsonParser.parse(json);
        ProductPatch patch = new ProductPatch();
        patch.setName(JsonParser.parseString(map.get("name")));
        patch.setPrice(JsonParser.parseDouble(map.get("price")));
        patch.setActive(JsonParser.parseBoolean(map.get("active")));
        return patch;
    }

}
