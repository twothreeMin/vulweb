package smvulweb.vulweb.global.config.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CheckManager {
    public static List<String> getManagerList() throws IOException {
        return Files.readAllLines(Path.of("src/main/resources/managers.txt"));
    }
    public static boolean checkIfManager(String email) {
        System.out.println("checkIFManager!@#@!#@!#@!#!#!@# " + email);
        try {
            List<String> managers = getManagerList();
            return managers.contains(email);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}