package github.ebrauta.util.json;

import java.util.HashMap;
import java.util.Map;

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
        if (value == null || "null".equals(value)) return 0L;
        return Long.parseLong(value);
    }

    public static String parseString(String value) {
        if (value == null || "null".equals(value)) return null;
        return value.replace("\"", "");
    }

    public static Double parseDouble(String value) {
        if (value == null || "null".equals(value)) return null;
        return Double.parseDouble(value);
    }

    public static Boolean parseBoolean(String value) {
        if (value == null || "null".equals(value)) return true;
        return Boolean.parseBoolean(value);
    }
}
