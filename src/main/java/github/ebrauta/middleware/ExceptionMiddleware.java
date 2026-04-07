package github.ebrauta.middleware;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

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
