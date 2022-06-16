package org.dl.lang.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author WuJi
 * @since 2022/6/14
 **/
public class CountLongWords {
    public static void main(String[] args) throws IOException {
        String fileName = Objects.requireNonNull(CountLongWords.class.getClassLoader().getResource("data/test.txt")).getPath();
        String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        List<String> split = Arrays.asList(content.split("\\PL+"));
        long count = 0;
        for (String s : split) {
            if (s.length() > 10) count++;
        }
        System.out.println("classic for count :" + count);

        count = split.stream().filter(x -> x.length() > 10).count();
        System.out.println("java8 stream count:" + count);

        count = split.stream().parallel().filter(x -> x.length() > 10).count();
        System.out.println("java8 parallel stream count:" + count);
    }
}
