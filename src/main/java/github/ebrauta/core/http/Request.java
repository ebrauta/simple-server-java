package github.ebrauta.core.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final HttpMethod method;
    private final String path;
    private final String body;
    private final Map<String, String> pathParams;
    private final Map<String, String> queryParams;
    private final Map<String, Object> attributes;

    public Request(HttpMethod method, String path, String body, Map<String, String> queryParams) {
        this.method = method;
        this.path = path;
        this.body = body;
        this.queryParams = queryParams;
        this.pathParams = new HashMap<>();
        this.attributes = new HashMap<>();
    }
    public HttpMethod getMethod() { return method; }
    public String getPath() { return path; }
    public String getBody(){
        return body;
    }
    public String getPathParam(String key){
        return pathParams.get(key);
    }
    public String getQueryParam(String key){ return queryParams.get(key); }
    public void setPathParam(String key, String value){
        pathParams.put(key, value);
    }
    public void setAttribute(String key, Object value){ attributes.put(key, value); }
    public <T> T getAttribute(String key){
        return (T) attributes.get(key);
    }
    public static Request from(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();
        return new Request(
                HttpMethod.valueOf(exchange.getRequestMethod().toUpperCase()),
                path,
                readBody(exchange),
                parseQuery(query));
    }
    private static Map<String, String> parseQuery(String query){
        Map<String, String> result = new HashMap<>();
        if(query == null || query.isEmpty()) return result;
        for (String pair : query.split("&")) {
            String[] keyValue = pair.split("=", 2);
            String key = URLDecoder.decode(keyValue[0].trim(), StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1].trim(), StandardCharsets.UTF_8) : null;
            result.put(key, value);
        }
        return result;
    }
    private static String readBody(HttpExchange exchange) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            throw new ServerException("Não foi possível ler o corpo da requisição: ", e);
        }
    }
}
