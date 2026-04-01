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
                    } catch (NumberFormatException ignore){}
                }
            }
        }
        ResponseUtil.send(exchange, 404, ResponseUtil.error("EndPoint Não Encontrado"));
    }
}
