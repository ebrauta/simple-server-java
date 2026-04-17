package github.ebrauta.app;

import com.sun.net.httpserver.HttpServer;
import github.ebrauta.app.config.ApplicationConfig;
import github.ebrauta.app.controller.TestController;
import github.ebrauta.app.middleware.CorsMiddleware;
import github.ebrauta.app.middleware.ExceptionMiddleware;
import github.ebrauta.app.middleware.LoggingMiddleware;
import github.ebrauta.app.util.Banner;
import github.ebrauta.core.adapter.HttpHandlerAdapter;
import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.middleware.Middleware;
import github.ebrauta.core.middleware.MiddlewareChain;
import github.ebrauta.core.router.Router;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private final Router router = new Router();
    private final List<Middleware> middlewares = new ArrayList<>();
    private MiddlewareChain chain;

    public static void run(String[] args) {
        Application app = new Application();
        app.configure();
        app.listen(ApplicationConfig.getPort());
    }

    private void configure(){
        TestController controller = new TestController();
        router().register(HttpMethod.GET,"/test", controller.get());
        router().register(HttpMethod.GET,"/test/{id}", controller.getById());
        router().register(HttpMethod.GET,"/query", controller.getWithQuery());
        use(new CorsMiddleware());
        use(new ExceptionMiddleware());
        use(new LoggingMiddleware());
        configureCors();
    }

    private Application use(Middleware middleware){
        middlewares.add(middleware);
        return this;
    }

    private void configureCors(){
        ApplicationConfig.addAllowedOrigins("*");
        ApplicationConfig.addAllowedMethods("GET");
        ApplicationConfig.addAllowedMethods("OPTIONS");
    }

    public Router router() {
        return router;
    }

    public void listen(int port){
        try {
            this.chain = new MiddlewareChain(middlewares, router::handle);
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            HttpHandlerAdapter adapter = new HttpHandlerAdapter(chain);
            server.createContext("/", adapter::handle);
            Banner.print(port, "DEV");
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao iniciar o Servidor", e);
        }
    }
}
