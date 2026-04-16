package github.ebrauta.core.router;

import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Route> routes = new ArrayList<>();

    public void register(HttpMethod method, String path, IHandler handler) {
        routes.add(new Route(method, path, handler));
    }

    public Response handle(Request request){
        String method = request.getMethod();
        String path = request.getPath();

        for (Route route : routes) {
            if (!route.method.toString().equals(method)) continue;
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
        return Response.endpointNotFound();
    }
}
