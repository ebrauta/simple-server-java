package github.ebrauta.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.util.CorsUtil;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Route> routes = new ArrayList<>();

    public void register(String method, String path, HttpHandler handler){
        routes.add(new Route(method, path, handler));
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        for (Route route : routes) {
            if(!route.method.equals(method)) continue;
            if(!route.hasParam && route.path.equals(path)){
                route.handler.handle(exchange);
                return;
            }
            if(route.hasParam){
                String basePath = route.path.substring(0, route.path.indexOf("/{"));
                if(path.startsWith(basePath + "/")){
                    String idPart = path.substring(basePath.length() + 1);
                    try{
                        Long id = Long.parseLong(idPart);
                        exchange.setAttribute("id", id);
                        route.handler.handle(exchange);
                        return;
                    } catch (NumberFormatException e){
                        continue;
                    }
                }
            }
        }
        sendNotFound(exchange);
    }

    private void sendNotFound(HttpExchange exchange) throws IOException{
        String response = ResponseUtil.error("Endpoint Não Encontrado");
        CorsUtil.addCorsHeaders(exchange);
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(404, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
