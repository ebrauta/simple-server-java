package github.ebrauta.middleware;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.util.LoggerUtil;

import java.util.function.Function;

public class LoggingMiddleware implements Middleware{
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
        long start = System.currentTimeMillis();
        Response response = next.apply(request);
        long duration = System.currentTimeMillis() - start;
        LoggerUtil.log(request.getMethod(), request.getPath(), response.getStatus(), duration);
        return response;
    }
}
