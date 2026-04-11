package github.ebrauta.core.router;

import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.function.Function;

public class Route {
    final HttpMethod method;
    final String path;
    final Function<Request, Response> handler;
    final boolean hasParam;

    public Route(HttpMethod method, String path, Function<Request, Response> handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
    }
}
