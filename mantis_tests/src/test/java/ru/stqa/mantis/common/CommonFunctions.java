package ru.stqa.mantis.common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
    public static String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumber = () -> rnd.nextInt(26);
        var result = Stream.generate(randomNumber)
                .limit(n)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
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
