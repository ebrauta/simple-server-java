package github.ebrauta.core.http;

import github.ebrauta.app.util.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final HttpStatus status;
    private final Object data;
    private final String errorMessage;
    private final Map<String, String> headers;

    public Response(HttpStatus status, Object data, String error) {
        this.status = status;
        this.data = data;
        this.errorMessage = error;
        this.headers = new HashMap<>();
    }
    public int getStatus() {
        return status.getCode();
    }
    public Response header(String key, String value) {
        headers.put(key, value);
        return this;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Response ok(Object data){ return new Response(HttpStatus.OK, data, null); }
    public static Response created(Object data){ return new Response(HttpStatus.CREATED, data, null); }
    public static Response noContent(){ return new Response(HttpStatus.NO_CONTENT, null, null); }
    public static Response endpointNotFound(){ return new Response(HttpStatus.NOT_FOUND, null, "EndPoint não encontrado" ); }
    public static Response badRequest(String error){ return new Response(HttpStatus.BAD_REQUEST, null, error); }
    public static Response serverError(String error){ return new Response(HttpStatus.INTERNAL_SERVER_ERROR, null, "Erro de Servidor: " + error); }

    public String toJson(){
        headers.put("Content-Type", "application/json");
        boolean isSuccess = errorMessage == null;
        String dataStr = data != null ? JsonParser.toJson(data) : "null";
        String hasError = errorMessage != null ? JsonParser.toJson(errorMessage) : "null";
        return """
                {
                    "success": %b,
                    "data": %s,
                    "error": %s
                }
                """.formatted(isSuccess, dataStr, hasError);
    }
}
