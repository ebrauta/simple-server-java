package github.ebrauta.core.http;

@FunctionalInterface
public interface IHandler {
    Response apply(Request request);
}
