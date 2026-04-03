package github.ebrauta.util;

public class ResponseUtil {

    public static String success(String json) {
        return """
                 {
                     "success": true,
                     "data": %s,
                     "error": null
                 }
                """.formatted(json);
    }

    public static String error(String message) {
        return """
                {
                    "success": false,
                    "data": null,
                    "error": %s
                }
                """.formatted(message);
    }
}
