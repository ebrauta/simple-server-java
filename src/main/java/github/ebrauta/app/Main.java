package github.ebrauta.app;

import github.ebrauta.app.util.JsonParser;
import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Response;

public class Main {
    public static void main(String[] args) {
        Application app = Application.create();
        app.router().register(HttpMethod.GET,"/test", createTestRoute());
        app.listen();
    }

    private static IHandler createTestRoute(){
        String json = "{\"msg\":\"Api funcionando\",\"version\": 1.0,\"active\": true}";
        String responseJson = JsonParser.toJson(JsonParser.parse(json));
        return (req) -> Response.ok(responseJson);
    }
}
