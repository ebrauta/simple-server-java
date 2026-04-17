package github.ebrauta.app.middleware;

import github.ebrauta.app.config.ApplicationConfig;
import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.ioc.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

public class CorsMiddleware implements Middleware {
    @Override
    public Response apply(Request request, IHandler next) {
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return addDefaultHeaders(Response.noContent());

        }
        Response response = next.apply(request);
        return addDefaultHeaders(response);
    }

    private Response addDefaultHeaders(Response entry) {
        String allowedOrigin = ApplicationConfig.getCorsOrigins();
        String allowedMethods = ApplicationConfig.getCorsMethods();
        return entry.header("Access-Control-Allow-Origin", allowedOrigin)
                .header("Access-Control-Allow-Methods", allowedMethods)
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
