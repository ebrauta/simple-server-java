package github.ebrauta.core.ioc;

import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

@FunctionalInterface
public interface IHandler {
    Response apply(Request request);
}
