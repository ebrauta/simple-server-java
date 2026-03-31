package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.controller.ProductController;
import github.ebrauta.repository.ProductRepository;
import github.ebrauta.service.ProductService;
import github.ebrauta.util.Banner;

import java.io.IOException;
import java.net.InetSocketAddress;

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
        server.createContext("/products", controller);
    }

    private static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
