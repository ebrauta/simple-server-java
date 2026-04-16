package github.ebrauta.core.middleware;

import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

@FunctionalInterface
public interface Middleware {
    Response apply(Request request, IHandler next);
}
