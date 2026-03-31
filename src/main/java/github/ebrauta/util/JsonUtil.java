package github.ebrauta.util;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.ProductPatch;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String toJsonFromDTO(ProductResponseDTO dto) {
        return "{"
                + "\"id\":" + dto.getId() + ","
                + "\"name\":\"" + dto.getName() + "\","
                + "\"price\":" + dto.getPrice()
                + "}";
    }

    public static String toJsonFromDTO(List<ProductResponseDTO> products) {
        return products
                .stream()
                .map(JsonUtil::toJsonFromDTO)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public static ProductRequestDTO fromJsonToDTO(String json) {
        ProductRequestDTO dto = new ProductRequestDTO();
        String cleaned = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] fields = cleaned.split(",");
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key){
                case "name" -> dto.setName(value);
                case "price" -> dto.setPrice(Double.parseDouble(value));
            }
        }
        return dto;
    }

    public static ProductPatch fromJsonPatch(String json) {
        ProductPatch patch = new ProductPatch();
        String cleaned = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] fields = cleaned.split(",");
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "name" -> patch.setName(value);
                case "price" -> patch.setPrice(Double.parseDouble(value));
                case "active" -> patch.setActive(Boolean.parseBoolean(value));
            }
        }
        return patch;
    }
}
