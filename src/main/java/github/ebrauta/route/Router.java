package github.ebrauta.route;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Router {
    private final List<Route> routes = new ArrayList<>();

    public void register(String method, String path, Function<Request, Response> handler) {
        routes.add(new Route(method, path, handler));
    }

    public Response handle(Request request){
        String method = request.getMethod();
        String path = request.getPath();

        for (Route route : routes) {
            if (!route.method.equals(method)) continue;
            if(!route.hasParam && route.path.equals(path)){
                return route.handler.apply(request);
            }
            if(route.hasParam){
                String basePath = route.path.substring(0, route.path.indexOf("/{"));
                if(path.startsWith(basePath + "/")){
                    String idPart = path.substring(basePath.length() + 1);
                    try {
                        Long.parseLong(idPart);
                        request.setAttribute("id", idPart);
                        return route.handler.apply(request);
                    } catch (NumberFormatException ignored) {}

                }
            }
        }
        return Response.notFound(ResponseUtil.error("Endpoint Não Encontrado"));
    }
}
