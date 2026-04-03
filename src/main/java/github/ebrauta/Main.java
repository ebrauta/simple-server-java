package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.controller.ProductController;
import github.ebrauta.http.HttpHandlerAdapter;
import github.ebrauta.http.Response;
import github.ebrauta.middleware.*;
import github.ebrauta.repository.ProductRepository;
import github.ebrauta.route.Router;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.Banner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Router router = new Router();
        int port = getPort();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        testContext(server);
        createProductContext(server, router);
        server.start();
        Banner.print(port);
    }

    private static void testContext(HttpServer server){
        String message = Response.ok("Api funcionando. Versão 1.0").onJsonFormat();
        server.createContext("/test", exchange -> {
            exchange.sendResponseHeaders(200, message.getBytes(StandardCharsets.UTF_8).length);
            exchange.getResponseBody().write(message.getBytes(StandardCharsets.UTF_8));
            exchange.close();
        });
    }

    private static void createProductContext(HttpServer server, Router router) {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);
        List<Middleware> middlewares = List.of(
                new CorsMiddleware(),
                new ExceptionMiddleware(),
                new LoggingMiddleware()
        );
        MiddlewareChain chain = new MiddlewareChain(middlewares, router::handle);

        router.register("GET", "/products", productController::getAll);
        router.register("POST", "/products", productController::create);
        router.register("GET", "/products/{id}", productController::getById);
        router.register("DELETE", "/products/{id}", productController::delete);
        router.register("PUT", "/products/{id}", productController::update);
        router.register("PATCH", "/products/{id}", productController::patch);

        server.createContext("/", exchange -> {
            HttpHandlerAdapter adapter = new HttpHandlerAdapter(chain);
            adapter.handle(exchange);
        } );
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
