package github.ebrauta.util;

public class Banner {
    public static void print(int port){
        System.out.printf("""
                 ____  _             _ _   _     _____                   \s
                / ___|| |_ ___  __ _| | |_| |__ |  ___|__  _ __ __ _  ___\s
                \\___ \\| __/ _ \\/ _` | | __| '_ \\| |_ / _ \\| '__/ _` |/ _ \\
                 ___) | ||  __/ (_| | | |_| | | |  _| (_) | | | (_| |  __/
                |____/ \\__\\___|\\__,_|_|\\__|_| |_|_|  \\___/|_|  \\__, |\\___|
                                                               |___/     \s
                
                       🚀 StealthForge API
               
                       ✔ Server running on port: %d
                       ✔ Environment: DEV
                       ✔ Status: ONLINE
               
                       %n""", port);
    }
}
