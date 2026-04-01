package github.ebrauta.util;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.ProductPatch;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String toJsonFromDTO(ProductResponseDTO dto) {
        return "{"
                + "\"id\":" + dto.id() + ","
                + "\"name\":\"" + dto.name() + "\","
                + "\"price\":" + dto.price()
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
            switch (key) {
                case "name" -> {
                    if ("null".equals(value)) {
                        dto.setName(null);
                    } else {
                        dto.setName(value);
                    }
                }
                case "price" -> {
                    if (!"null".equals(value)) {
                        dto.setPrice(Double.parseDouble(value));
                    }
                }
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
                case "name" -> {
                    if ("null".equals(value)) {
                        patch.setName(null);
                    } else {
                        patch.setName(value);
                    }
                }
                case "price" -> {
                    if (!"null".equals(value)) {
                        patch.setPrice(Double.parseDouble(value));
                    }
                }
                case "active" -> {
                    if (!"null".equals(value)) {
                        patch.setActive(Boolean.parseBoolean(value));
                    }
                }
            }
        }
        return patch;
    }
}
