package github.ebrauta.middleware;

import github.ebrauta.http.Request;
import github.ebrauta.http.Response;

import java.util.List;
import java.util.function.Function;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private final Function<Request, Response> finalHandler;

    public MiddlewareChain(List<Middleware> middlewares, Function<Request, Response> finalHandler) {
        this.middlewares = middlewares;
        this.finalHandler = finalHandler;
    }
    public Response apply(Request request){
        return execute(request, 0);
    }
    public Response execute(Request request, int index) {
        if(index < middlewares.size()){
            Middleware current = middlewares.get(index);
            return current.apply(request, req -> execute(req, index+1));
        } else {
            return finalHandler.apply(request);
        }
    }
}
