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
import github.ebrauta.util.ResponseUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
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
        String message = ResponseUtil.success("Api funcionando. Versão 1.0");
        server.createContext("/test", new HttpHandlerAdapter(req -> Response.ok(message)));
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
        MiddlewareChain chain = new MiddlewareChain(middlewares);

        router.register("GET", "/products", new HttpHandlerAdapter(chain.build(productController::getAll)));
        router.register("POST", "/products", new HttpHandlerAdapter(chain.build(productController::create)));
        router.register("GET", "/products/{id}", new HttpHandlerAdapter(chain.build(productController::getById)));
        router.register("DELETE", "/products/{id}", new HttpHandlerAdapter(chain.build(productController::delete)));
        router.register("PUT", "/products/{id}", new HttpHandlerAdapter(chain.build(productController::update)));
        router.register("PATCH", "/products/{id}", new HttpHandlerAdapter(chain.build(productController::patch)));

        server.createContext("/", router::handle);
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
