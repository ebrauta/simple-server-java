package github.ebrauta.app.middleware;


import github.ebrauta.app.util.Logger;
import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

public class LoggingMiddleware implements Middleware {
    @Override
    public Response apply(Request request, IHandler next) {
        long start = System.currentTimeMillis();
        Response response = next.apply(request);
        long duration = System.currentTimeMillis() - start;
        Logger.log(request.getMethod().name(), request.getPath(), response.getStatus(), duration);
        return response;
    }
}
