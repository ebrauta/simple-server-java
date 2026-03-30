package github.ebrauta.util;

import github.ebrauta.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String toJson(Product product){
        return "{"
                + "\"id\":" + product.id() + ","
                + "\"name\":\"" + product.name() + "\","
                + "\"price\":" + product.price() + ","
                + "\"active\":" + product.active() +
                "}";
    }
    public static String toJson(List<Product> products){
        return products
                .stream()
                .map(JsonUtil::toJson)
                .collect(Collectors.joining(",","[","]"));
    }

    public static Product fromJson(String json){
        String cleaned = json.replace("{","").replace("}","").replace("\"","");
        String[] fields = cleaned.split(",");
        String name = null;
        double price = 0;
        for(String field: fields){
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            if("name".equals(key)){
                name = value;
            } else if("price".equals(key)){
                price = Double.parseDouble(value);
            }
        }
        return new Product(null, name, price, true);
    }
}
