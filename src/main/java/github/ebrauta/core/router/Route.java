package github.ebrauta.core.router;

import github.ebrauta.core.http.HttpMethod;
import github.ebrauta.core.http.IHandler;
import github.ebrauta.core.http.Request;
import github.ebrauta.core.http.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private final HttpMethod method;
    private final Pattern pattern;
    private final List<String> paramNames = new ArrayList<>();
    private final IHandler handler;

    public Route(HttpMethod method, String path, IHandler handler) {
        this.method = method;
        this.pattern = Pattern.compile(buildRegex(path));
        this.handler = handler;
    }

    private String buildRegex(String path) {
        StringBuilder regex = new StringBuilder("^");
        String[] parts = path.split("/");
        for (String part : parts) {
            if(part.isEmpty()) continue;
            regex.append("/");
            if(part.startsWith("{") && part.endsWith("}")){
                String paramName = part.substring(1, part.length() - 1);
                paramNames.add(paramName);
                regex.append("(?<").append(paramName).append(">[^/]+)");
            } else {
                regex.append(part);
            }
        }
        regex.append("$");
        return regex.toString();
    }

    public boolean matches(Request request) {
        if(method != request.getMethod()) return false;
        Matcher matcher = pattern.matcher(request.getPath());
        if(!matcher.matches()) return false;
        for(String name : paramNames){
            request.setPathParam(name, matcher.group(name));
        }
        return true;
    }

    public Response handle(Request request) {
        return handler.apply(request);
    }

    //final String path;
    //final boolean hasParam;

    /*public Route(HttpMethod method, String path, IHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.hasParam = path.contains("{");
    }*/
}
