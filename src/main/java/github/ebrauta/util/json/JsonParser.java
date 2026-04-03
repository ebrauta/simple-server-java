package github.ebrauta.util.json;

import github.ebrauta.dto.ProductRequestDTO;
import github.ebrauta.dto.ProductResponseDTO;
import github.ebrauta.model.Product;
import github.ebrauta.model.ProductPatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonParser {

    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().replace("{", "").replace("}", "");
        String[] fields = json.split(",");
        for (String field : fields) {
            String[] keyValue = field.split(":", 2);
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim();
            map.put(key, value);
        }
        return map;
    }

    public static Long parseLong(String value) {
        if (value.equals("null")) return 0L;
        return Long.parseLong(value);
    }

    public static String parseString(String value) {
        if (value.equals("null")) return "";
        return value.replace("\"", "");
    }

    public static Double parseDouble(String value) {
        if (value.equals("null")) return 0.0;
        return Double.parseDouble(value);
    }

    public static Boolean parseBoolean(String value) {
        if (value.equals("null")) return true;
        return Boolean.parseBoolean(value);
    }
}
