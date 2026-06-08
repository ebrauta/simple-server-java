package github.ebrauta.app.controller;

import github.ebrauta.core.ioc.IHandler;
import github.ebrauta.core.http.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestController {
    public IHandler get() {
        return req -> {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("msg", "Api funcionando");
            body.put("version", 1.0);
            body.put("active", true);
            return Response.ok(body);
        };
    }

    public IHandler getById() {
        return req -> {
            String id = req.getPathParam("id");
            int idValue = id != null ? Integer.parseInt(id) : -1;
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("id", idValue);
            body.put("msg", "Recurso param funcionando");
            return Response.ok(body);
        };
    }

    public IHandler getWithQuery() {
        return req -> {
            String page = req.getQueryParam("page");
            String size = req.getQueryParam("size");

            int pageValue = page != null ? Integer.parseInt(page) : 1;
            int sizeValue = size != null ? Integer.parseInt(size) : 10;
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("page", pageValue);
            body.put("size", sizeValue);
            body.put("msg", "Recurso query funcionando");
            return Response.ok(body);
        };
    }
}
