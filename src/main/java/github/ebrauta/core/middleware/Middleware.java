package github.ebrauta.core.middleware;

import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.function.Function;

public interface Middleware {
    Response apply(Request request, Function<Request, Response> next);
}
