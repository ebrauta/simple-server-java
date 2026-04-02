package github.ebrauta.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public  static String read(String path){
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return "[]";
        }
    }
    public static void write(String path, String content){
        try {
            Files.writeString(Paths.get(path), content);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo");
        }
    }
}
