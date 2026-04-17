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
        for (Route route : routes) {
            if(route.matches(request)){
                return route.handle(request);
            }
        }
        return Response.endpointNotFound();
    }
}
