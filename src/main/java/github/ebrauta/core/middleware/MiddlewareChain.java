package github.ebrauta.core.middleware;

import github.ebrauta.core.ioc.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.List;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private final IHandler finalHandler;

    public MiddlewareChain(List<Middleware> middlewares, IHandler finalHandler) {
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
