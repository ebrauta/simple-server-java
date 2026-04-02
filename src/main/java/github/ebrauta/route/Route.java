package github.ebrauta.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.HashMap;
import java.util.Map;

public class Route {
    final String method;
    final String path;
    final HttpHandler handler;
    final boolean hasParam;
    String basePath;

    public Route(String method, String path, HttpHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
        if(hasParam) {
            this.basePath = path.substring(0, path.indexOf("/{"));
        }
    }
    public boolean matchesPath(String requestPath){
        if(!hasParam){
            return path.equals(requestPath);
        }
        if(!requestPath.startsWith(this.basePath + "/")){
            return false;
        }
        String idPart = requestPath.substring(basePath.length() + 1);
        try {
            Long.parseLong(idPart);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    public void extractParams(HttpExchange exchange, String requestPath) {
        if(!hasParam) return;
        String idPart = requestPath.substring(basePath.length() + 1);
        Map<String,String> params = new HashMap<>();
        params.put("id", idPart);
        exchange.setAttribute("params", params);
    }
}
