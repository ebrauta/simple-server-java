package github.ebrauta.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final String method;
    private final String path;
    private final String body;
    private final Map<String, String> params;

    public Request(String method, String path, String body, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.body = body;
        this.params = params;
    }
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getBody(){
        return body;
    }
    public String getParam(String key){
        return params.get(key);
    }
    public void setAttribute(String key, String value){
        params.put(key, value);
    }
    public static Request from(HttpExchange exchange) {
        return new Request(exchange.getRequestMethod(), exchange.getRequestURI().getPath(), readBody(exchange), new HashMap<>());
    }
    private static String readBody(HttpExchange exchange) {
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }
}
