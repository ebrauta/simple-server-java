package github.ebrauta.app.util;

import java.util.*;

public class JsonParser {
    private int index;
    private final String json;

    public JsonParser(String json) {
        this.json = json.trim();
        this.index = 0;
    }

    public static Object parse(String json) {
        return new JsonParser(json).parseValue();
    }

    private Object parseValue() {
        skipWhiteSpace();
        char c = peek();
        if (c == '{') return parseObject();
        if (c == '"') return parseString();
        if (c == 't' || c == 'f') return parseBoolean();
        if (c == 'n') return parseNull();
        if (Character.isDigit(c) || c == '-') return parseNumber();
        throw new RuntimeException("Json inválido na posição " + index);
    }
    private Map<String,Object> parseObject() {
        Map<String,Object> map = new LinkedHashMap<>();
        consume('{');
        skipWhiteSpace();
        if(peek() == '}') {
            consume('}');
            return map;
        }
        while(true) {
            String key = parseString();
            skipWhiteSpace();
            consume(':');
            Object value = parseValue();
            map.put(key, value);
            skipWhiteSpace();
            if(peek() == ',') {
                consume(',');
                continue;
            }
            if(peek() == '}') {
                consume('}');
                break;
            }
        }
        return map;
    }
    private String parseString() {
        consume('"');
        StringBuilder sb = new StringBuilder();
        while (peek() != '"') {
            sb.append(next());
        }
        consume('"');
        return sb.toString();
    }
    private Object parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (index < json.length()) {
            char c = peek();
            if(!Character.isDigit(c) && c != '.' && c != '-') break;
            sb.append(c);
            index++;
        }
        String num = sb.toString();
        if(num.contains(".")) {
            return Double.parseDouble(num);
        }
        return Integer.parseInt(num);
    }
    private Boolean parseBoolean() {
        if(json.startsWith("true", index)){
            index += 4;
            return true;
        } else {
            index += 5;
            return false;
        }
    }
    private Object parseNull() {
        index += 4;
        return null;
    }

    private char peek() {
        return json.charAt(index);
    }
    private char next(){
        return json.charAt(index++);
    }
    private void consume(char expected) {
        if(json.charAt(index) != expected) {
            throw new RuntimeException("Esperado '" + expected + "' na posição " + index);
        }
        index++;
    }
    private void skipWhiteSpace() {
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
    }

    public static String toJson(Object value) {
        return writeValue(value);
    }

    private static String writeValue(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return "\"" + escape((String) value) + "\"";
        }

        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }

        if (value instanceof Map) {
            return writeObject((Map<String, Object>) value);
        }

        throw new RuntimeException("Tipo não suportado: " + value.getClass());
    }

    private static String writeObject(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();

            sb.append("\"")
                    .append(escape(entry.getKey()))
                    .append("\":");

            sb.append(writeValue(entry.getValue()));

            if (it.hasNext()) {
                sb.append(",");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    private static String escape(String str) {
        return str
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
