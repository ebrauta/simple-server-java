package github.ebrauta.util.json;

public class JsonWriter {
    public static String createJson(long id, String name, double price, boolean active) {
        return """
                {
                    %s,
                    %s,
                    %s,
                    %s
                }
                """.formatted(toJsonFields("id", id), toJsonFields("name", name), toJsonFields("price", price), toJsonFields("active", active));
    }

    public static String toJsonFields(String key, Object value) {
        if (value == null) return String.format("\"%s\": \"null\"", key);
        if (value instanceof String) return String.format("\"%s\": \"%s\"", key, value);
        return "\"" + key + "\": " + value;
    }
}
