package github.ebrauta.app.middleware;


import github.ebrauta.app.util.Logger;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

import java.util.function.Function;

public class LoggingMiddleware implements Middleware {
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        long start = System.currentTimeMillis();
        Response response = next.apply(request);
        long duration = System.currentTimeMillis() - start;
        Logger.log(request.getMethod(), request.getPath(), response.getStatus(), duration);
        return response;
    }
}
