package github.ebrauta.app.config;

import java.util.LinkedList;
import java.util.List;

public class ApplicationConfig {
    private ApplicationConfig() {}
    private static final List<String> allowedMethods = new LinkedList<>();
    private static final List<String> allowedOrigins = new LinkedList<>();

    public static int getPort() {
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            return Integer.parseInt(envPort);
        }
        return 8080;
    }

    public static void addAllowedMethods(String allowedMethod) {
        allowedMethods.add(allowedMethod);
    }

    public static void addAllowedOrigins(String allowedOrigin) {
        allowedOrigins.add(allowedOrigin);
    }

    public static String getCorsMethods() {
        return String.join(", ", allowedMethods);
    }

    public static String getCorsOrigins() {
        return String.join(", ", allowedOrigins);
    }
}
