package github.ebrauta.core.middleware;

import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.List;
import java.util.function.Function;

public class MiddlewareChain {
    private final List<github.ebrauta.core.middleware.Middleware> middlewares;
    private final Function<Request, Response> finalHandler;

    public MiddlewareChain(List<github.ebrauta.core.middleware.Middleware> middlewares, Function<github.ebrauta.core.http.Request, github.ebrauta.core.http.Response> finalHandler) {
        this.middlewares = middlewares;
        this.finalHandler = finalHandler;
    }
    public github.ebrauta.core.http.Response apply(github.ebrauta.core.http.Request request){
        return execute(request, 0);
    }
    public github.ebrauta.core.http.Response execute(github.ebrauta.core.http.Request request, int index) {
        if(index < middlewares.size()){
            github.ebrauta.core.middleware.Middleware current = middlewares.get(index);
            return current.apply(request, req -> execute(req, index+1));
        } else {
            return finalHandler.apply(request);
        }
    }
}
