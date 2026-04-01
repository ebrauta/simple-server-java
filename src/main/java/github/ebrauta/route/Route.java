package github.ebrauta.route;

import com.sun.net.httpserver.HttpHandler;

public class Route {
    final String method;
    final String path;
    final boolean hasParam;
    final HttpHandler handler;

    public Route(String method, String path, HttpHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
    }
}
