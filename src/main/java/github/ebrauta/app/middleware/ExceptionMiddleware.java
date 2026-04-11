package github.ebrauta.app.middleware;

import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

import java.util.function.Function;

public class ExceptionMiddleware implements Middleware {
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        try {
            return next.apply(request);
        } catch (Exception e) {
            return Response.serverError(e.getMessage());
        }
    }
}
