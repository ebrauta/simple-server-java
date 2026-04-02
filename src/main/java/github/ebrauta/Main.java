package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.controller.ProductController;
import github.ebrauta.middleware.*;
import github.ebrauta.repository.ProductRepository;
import github.ebrauta.route.Router;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.Banner;
import github.ebrauta.util.FileUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Router router  = new Router();
        int port = getPort();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", exchange -> {
            String response = "API is running";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        createProductContext(server, router);
        server.start();
        Banner.print(port);
    }

    private static void createProductContext(HttpServer server, Router router) {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);
        router.register("GET", "/products", productController::getAll);
        router.register("POST", "/products", productController::create);
        router.register("GET", "/products/{id}", productController::getById);
        router.register("DELETE", "/products/{id}", productController::delete);
        router.register("PUT", "/products/{id}", productController::update);
        router.register("PATCH", "/products/{id}", productController::patch);
        List<Middleware> middlewares = List.of(
                new CorsMiddleware(),
                new ExceptionMiddleware(),
                new LoggingMiddleware()
        );
        server.createContext("/products", exchange -> {
            MiddlewareChain chain = new MiddlewareChain(middlewares, router::handle);
            chain.next(exchange);
        });
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
