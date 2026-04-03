package github.ebrauta.route;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

import java.util.function.Function;

public class Route {
    final String method;
    final String path;
    final Function<Request, Response> handler;
    final boolean hasParam;

    public Route(String method, String path, Function<Request, Response> handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
    }
}
