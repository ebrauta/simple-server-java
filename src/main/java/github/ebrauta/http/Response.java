package github.ebrauta.http;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final int status;
    private final String body;
    private final Map<String, String> headers;

    public Response(int status, String body) {
        this.status = status;
        this.body = body;
        this.headers = new HashMap<>();
    }
    public int getStatus() {
        return status;
    }
    public String getBody() {
        return body;
    }
    public Response header(String key, String value) {
        headers.put(key, value);
        return this;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Response ok(String body) {
        return new Response(200, body);
    }
    public static Response created(String body){ return new Response(201, body); }
    public static Response noContent(){return new Response(204, ""); }
    public static Response badRequest(String body){ return new Response(400, body); }
    public static Response notFound(String body){ return new Response(404, body); }
    public static Response serverError(String body){ return new Response(500, body); }
}
