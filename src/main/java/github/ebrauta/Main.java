package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.controller.ProductController;

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
        server.createContext("/products", new ProductController());
        server.start();
        System.out.println("Servidor rodando na porta " + port);
    }

    private static int getPort(){
        String envPort = System.getenv("PORT");
        if(envPort != null){
            return Integer.parseInt(envPort);
        }
        return 8080;
    }
}
