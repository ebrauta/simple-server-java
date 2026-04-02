package github.ebrauta.middleware;

import github.ebrauta.exception.ValidationException;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.ResponseUtil;

import java.util.function.Function;

public class ExceptionMiddleware implements Middleware {
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        try {
            return next.apply(request);
        } catch (ValidationException e) {
            return Response.badRequest(ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            return Response.serverError(ResponseUtil.error(e.getMessage()));
        }
    }
}
