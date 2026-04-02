package github.ebrauta.util;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonUtil {
    /*public static String toJsonFromDTO(ProductResponseDTO dto) {
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

    public static ProductRequestDTO toRequestDTOFromJson(String json) {
        ProductRequestDTO dto = new ProductRequestDTO();
        String[] fields = cleanAndGetFields(json);
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

    public static ProductPatch toPatchFromJson(String json) {
        ProductPatch patch = new ProductPatch();
        String[] fields = cleanAndGetFields(json);
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

    public static Product toProductFromJson(String json) {
        String[] fields = cleanAndGetFields(json);
        Long id = null;
        String name = null;
        double price = 0;
        boolean active = true;
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "id" -> id = Long.parseLong(value);
                case "name" -> name = value;
                case "price" -> price = Double.parseDouble(value);
                case "active" -> active = Boolean.parseBoolean(value);
            }
        }
        return new Product(id, name, price, active);
    }

    public static String toJsonFromProduct(Product product) {
        return String.format("{\"id\": %d, \"name\":\"%s\", \"price\":%.2f,\"active\": %b}", product.id(), product.name(), product.price(), product.active());
    }

    public static List<Product> toListFromJson(String json) {
        List<Product> list = new ArrayList<>();
        if(json == null || json.isBlank() || json.equals("[]")){
            return list;
        }
        String cleaned = json.trim().substring(1, json.length() - 1);
        String[] items = cleaned.split("},\\{");
        for (String item : items) {
            if(!item.startsWith("{")) item = "{" + item;
            if(!item.endsWith("}")) item = item + "}";
            Product product = toProductFromJson(item);
            list.add(product);
        }
        return list;
    }

    public static String toJsonFromList(List<Product> products) {
        return products
                .stream()
                //.map(p -> "{\"id\":" + p.id() + ",\"name\":\"" + p.name() + "\",\"price\":" + p.price() + ",\"active\":" + p.active() + "}")
                .map(p -> createJson(p.id(),p.name(), p.price(), p.active()))
                .collect(Collectors.joining(",", "[", "]"));
    }

    private static String createJson(long id, String name, double price, boolean active) {
        return String.format("{\"id\": %d, \"name\":\"%s\", \"price\":%.2f,\"active\": %b}", id, name, price, active);
    }

    private static String[] cleanAndGetFields(String json) {
        String cleaned = json.replace("{", "").replace("}", "").replace("\"", "");
        return cleaned.split(",");
    }*/
    public static String toJson(Product product) {
        return createJson(product.id(), product.name(), product.price(), product.active());
    }

    public static String toJson(ProductResponseDTO dto) {
        return createJson(dto.id(), dto.name(), dto.price(), true);
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
        String[] fields = cleanAndGetFields(json);
        Long id = null;
        String name = null;
        double price = 0;
        boolean active = true;
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "id" -> id = Long.parseLong(value);
                case "name" -> name = value;
                case "price" -> price = Double.parseDouble(value);
                case "active" -> active = Boolean.parseBoolean(value);
            }
        }
        return new Product(id, name, price, active);
    }

    public static ProductRequestDTO toRequestDTO(String json) {
        ProductRequestDTO dto = new ProductRequestDTO();
        String[] fields = cleanAndGetFields(json);
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

    public static ProductPatch toPatch(String json) {
        ProductPatch patch = new ProductPatch();
        String[] fields = cleanAndGetFields(json);
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

    private static String createJson(long id, String name, double price, boolean active) {
        return String.format("{\"id\": %d, \"name\":\"%s\", \"price\": %s,\"active\": %b}", id, name, price, active);
    }

    private static String[] cleanAndGetFields(String json) {
        String cleaned = json.replace("{", "").replace("}", "").replace("\"", "");
        return cleaned.split(",");
    }
}
