package github.ebrauta.app.util;

import java.util.logging.Logger;

public class Banner {
    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Banner.class.getName());
    private Banner() {}
    public static void print(int port, String environment){
        String bannerMsg = """
                 \s
                 ____  _             _ _   _     _____                   \s
                / ___|| |_ ___  __ _| | |_| |__ |  ___|__  _ __ __ _  ___\s
                \\___ \\| __/ _ \\/ _` | | __| '_ \\| |_ / _ \\| '__/ _` |/ _ \\
                 ___) | ||  __/ (_| | | |_| | | |  _| (_) | | | (_| |  __/
                |____/ \\__\\___|\\__,_|_|\\__|_| |_|_|  \\___/|_|  \\__, |\\___|
                                                               |___/     \s
                
                       🚀 StealthForge API
               
                       ✔ Server running on port: %d
                       ✔ Environment: %s
                       ✔ Status: ONLINE
               
                       %n""".formatted(port, environment);
        LOGGER.info(bannerMsg);
    }
}
