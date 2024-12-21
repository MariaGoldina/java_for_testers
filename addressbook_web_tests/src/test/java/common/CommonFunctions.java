package common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class CommonFunctions {
    public static String randomString(int n) {
        var rnd = new Random();
        var result = "";
        for (int i = 0; i < n; i++) {
            result += (char) ('a' + rnd.nextInt(26));
        }
        return result;
    }

    public static String randomStringWithNumbers(int n) {
        var rnd = new Random();
        var result = "" + rnd.nextInt(n);
        return result;
    }

    public static String randomFile(String dir) {
        var fileNames = new File(dir).list();
        var index = new Random().nextInt(fileNames.length);
        return Paths.get(dir, fileNames[index]).toString();

    }
}
