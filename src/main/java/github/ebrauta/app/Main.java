package github.ebrauta.app;

import github.ebrauta.app.util.JsonParser;
import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        Application app = Application.create();
        app.router().register(HttpMethod.GET,"/test", createTestRoute());
        app.listen();
    }

    private static Function<Request, Response> createTestRoute(){
        String json = "{\"msg\":\"Api funcionando\",\"version\": 1.0,\"active\": true}";
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) JsonParser.parse(json);
        String responseJson = JsonParser.toJson(map);
        return (req) -> Response.ok(responseJson);
    }
}
