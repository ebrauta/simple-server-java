package github.ebrauta.util;

public class ResponseUtil {
    public static String success(String dataJson) {
        return "{"
                + "\"success\": true,"
                + "\"data\": " + dataJson + ","
                + "\"error\": null"
                + "}";
    }

    public static String error(String message) {
        return "{"
                + "\"success\": false,"
                + "\"data\": null ,"
                + "\"error\": \"" + message + "\""
                + "}";
    }
}
