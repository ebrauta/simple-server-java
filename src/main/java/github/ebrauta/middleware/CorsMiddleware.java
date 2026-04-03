package github.ebrauta.middleware;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

import java.util.function.Function;

public class CorsMiddleware implements Middleware {
    @Override
    public Response apply(Request request, Function<Request, Response> next) {
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
