package github.ebrauta.http;

import java.util.Map;

public class Request {
    private final String method;
    private final String path;
    private final String body;
    private final Map<String, String> params;

    public Request(String method, String path, String body, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.body = body;
        this.params = params;
    }
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getBody(){
        return body;
    }
    public String getParams(String key){
        return params.get(key);
    }
}
