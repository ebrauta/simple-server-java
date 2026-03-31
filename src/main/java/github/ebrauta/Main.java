package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.controller.ProductController;
import github.ebrauta.middleware.LoggingMiddleware;
import github.ebrauta.middleware.Middleware;
import github.ebrauta.middleware.MiddlewareChain;
import github.ebrauta.repository.ProductRepository;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.Banner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = getPort();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", exchange -> {
            String response = "API is running";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        createProductContext(server);
        server.start();
        Banner.print(port);
    }

    private static void createProductContext(HttpServer server) {
        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);
        ProductController controller = new ProductController(service);
        List<Middleware> middlewares = List.of(new LoggingMiddleware());
        MiddlewareChain chain = new MiddlewareChain(middlewares, controller::handle);
        server.createContext("/products", chain::next);
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
