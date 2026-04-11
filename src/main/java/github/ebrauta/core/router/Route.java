package github.ebrauta.core.router;

import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.IHandler;

public class Route {
    final HttpMethod method;
    final String path;
    final IHandler handler;
    final boolean hasParam;

    public Route(HttpMethod method, String path, IHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
    }
}
