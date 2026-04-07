package github.ebrauta;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.http.HttpHandlerAdapter;
import github.ebrauta.http.HttpMethod;
import github.ebrauta.http.Request;
import github.ebrauta.http.Response;
import github.ebrauta.middleware.*;
import github.ebrauta.route.Router;
import github.ebrauta.util.Banner;
import github.ebrauta.util.JsonParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Router router = new Router();
        int port = getPort();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        testContext(server, router);
        server.start();
        Banner.print(port, "DEV");
    }

    private static void testContext(HttpServer server, Router router){
        String json = "{\"msg\":\"Api funcionando\",\"version\": 1.0,\"active\": true}";
        Map<String, Object> map = (Map<String, Object>) JsonParser.parse(json);
        String responseJson = JsonParser.toJson(map);
        List<Middleware> middlewares = List.of(
                new CorsMiddleware(),
                new ExceptionMiddleware(),
                new LoggingMiddleware()
        );
        MiddlewareChain chain = new MiddlewareChain(middlewares, router::handle);
        Function<Request, Response> testFunction = (req) -> Response.ok(responseJson);;
        router.register(HttpMethod.GET,"/test", testFunction);
        server.createContext("/", exchange -> {
            HttpHandlerAdapter adapter = new HttpHandlerAdapter(chain);
            adapter.handle(exchange);
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
