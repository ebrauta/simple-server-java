package github.ebrauta.app.middleware;

import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

public class ExceptionMiddleware implements Middleware {
    @Override
    public Response apply(Request request, IHandler next) {
        try {
            return next.apply(request);
        } catch (Exception e) {
            return Response.serverError(e.getMessage());
        }
    }
}
