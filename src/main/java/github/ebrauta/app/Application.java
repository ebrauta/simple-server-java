package github.ebrauta.app;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.app.middleware.CorsMiddleware;
import github.ebrauta.app.middleware.ExceptionMiddleware;
import github.ebrauta.app.middleware.LoggingMiddleware;
import github.ebrauta.app.util.Banner;
import github.ebrauta.core.adapter.HttpHandlerAdapter;
import github.ebrauta.core.middleware.Middleware;
import github.ebrauta.core.middleware.MiddlewareChain;
import github.ebrauta.core.router.Router;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Application {
    private final Router router = new Router();

    public static Application create(){
        return new Application();
    }

    public Router router() {
        return router;
    }

    public void listen(){
        try {
            int port = getPort();
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            List<Middleware> middlewares = List.of(
                    new CorsMiddleware(),
                    new ExceptionMiddleware(),
                    new LoggingMiddleware()
            );
            MiddlewareChain chain = new MiddlewareChain(middlewares, router::handle);
            server.createContext("/", exchange -> {
                HttpHandlerAdapter adapter = new HttpHandlerAdapter(chain);
                adapter.handle(exchange);
            });
            Banner.print(port, "DEV");
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao iniciar o Servidor", e);
        }
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
