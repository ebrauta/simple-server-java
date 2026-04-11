package github.ebrauta.app.middleware;

import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;
import github.ebrauta.core.middleware.Middleware;

public class CorsMiddleware implements Middleware {
    @Override
    public Response apply(Request request, IHandler next) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return addDefaultHeaders(Response.noContent());

        }
        Response response = next.apply(request);
        return addDefaultHeaders(response);
    }

    private Response addDefaultHeaders(Response entry) {
        return entry.header("Access-Control-Allow-Origin", "http://127.0.0.1:5500")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
