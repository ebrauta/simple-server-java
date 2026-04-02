package github.ebrauta.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.util.CorsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HttpHandlerAdapter implements HttpHandler {
    private final Function<Request, Response> handler;

    public HttpHandlerAdapter(Function<Request, Response> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.preFlight(exchange);
        CorsUtil.addCorsHeaders(exchange);
        Map<String, String> params = extractParams(exchange.getAttribute("params"));
        Request request = new Request(exchange.getRequestMethod(), exchange.getRequestURI().getPath(), readBody(exchange), params);
        Response response = handler.apply(request);
        byte[] bytes = response.getBody().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(response.getStatus(), bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private String readBody(HttpExchange exchange) {
        return new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining());
    }

    private Map<String, String> extractParams(Object attr){
        Map<String, String> params = new HashMap<>();
        if(attr instanceof Map<?,?> rawMap){
            for(Map.Entry<?,?> entry: rawMap.entrySet()){
                params.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        } else {
            return Map.of();
        }
        return params;
    }
}
