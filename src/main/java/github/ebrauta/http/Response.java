package github.ebrauta.http;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final int status;
    private final Object data;
    private final String errorMessage;
    private final Map<String, String> headers;

    public Response(int status, Object data, String error) {
        this.status = status;
        this.data = data;
        this.errorMessage = error;
        this.headers = new HashMap<>();
    }
    public int getStatus() {
        return status;
    }
    public Response header(String key, String value) {
        headers.put(key, value);
        return this;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Response ok(Object data){ return new Response(200, data, null); }
    public static Response created(Object data){ return new Response(201, data, null); }
    public static Response noContent(){ return new Response(204, "", null); }
    public static Response productNotFound(){ return new Response(404, null, "Produto não encontrado" ); }
    public static Response endpointNotFound(){ return new Response(404, null, "EndPoint não encontrado" ); }
    public static Response badRequest(String error){ return new Response(400, null, error); }
    public static Response serverError(String error){ return new Response(500, null, "Erro de Servidor: " + error); }

    public String onJsonFormat(){
        boolean isSuccess = errorMessage == null;
        String dataStr = data != null ? data.toString() : "null";
        String hasError = errorMessage != null ? errorMessage : "null";
        return """
                {
                    "success": %b,
                    "data": %s,
                    "error": %s
                }
                """.formatted(isSuccess, dataStr, hasError);
    }
}
