package github.ebrauta.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Route> routes = new ArrayList<>();

    public void register(String method, String path, HttpHandler handler) {
        routes.add(new Route(method, path, handler));
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        boolean pathMatched = false;

        for (Route route : routes) {
            if (!route.matchesPath(path)) continue;
            pathMatched = true;
            if (!route.method.equals(method)) continue;
            route.extractParams(exchange, path);
            route.handler.handle(exchange);
            return;
        }
        if (pathMatched) {
            send(exchange, 405, ResponseUtil.error("Método não Permitido"));
        } else {
            send(exchange, 404, ResponseUtil.error("Endpoint não Encontrado"));
        }
            /*if(!route.method.equals(method)) {
                send(exchange, 405, ResponseUtil.error("Método não Permitido"));
            };
            if(!route.hasParam && route.path.equals(path)){
                route.handler.handle(exchange);
                return;
            }
            if(route.hasParam){
                String basePath = route.path.substring(0, route.path.indexOf("/{"));
                if(path.startsWith(basePath + "/")){
                    String idPart = path.substring(basePath.length() + 1);
                    try{
                        //Long id = Long.parseLong(idPart);
                        params.put("id", idPart);
                        exchange.setAttribute("params", params);
                        route.handler.handle(exchange);
                        return;
                    } catch (NumberFormatException ignore){}
                }
            }
        }
        send(exchange, 404, ResponseUtil.error("EdnPoint não Encontrado"));
        //ResponseUtil.send(exchange, 404, ResponseUtil.error("EndPoint Não Encontrado"));
        */
    }

    private static void send(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
