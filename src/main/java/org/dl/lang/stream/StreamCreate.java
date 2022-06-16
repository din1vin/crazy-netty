package org.dl.lang.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WuJi
 * @since 2022/6/14
 **/
public class StreamCreate {

    private static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElement = stream.limit(SIZE).collect(Collectors.toList());
        System.out.println("title :" + title);
        StringJoiner sj = new StringJoiner(",", "", "...");
        firstElement.forEach(x -> sj.add(x.toString()));
        System.out.println(sj);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        String fileName = Objects.requireNonNull(CountLongWords.class.getClassLoader().getResource("data/test.txt")).getPath();
        String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        List<String> split = Arrays.asList(content.split("\\PL+"));

        Stream<String> words = split.stream();
        show("words stream", words);

        // 指定数量的流
        Stream<String> ofStream = Stream.of("hello", "stream", "generate", "the");
        show("Stream.of", ofStream);

        // 空的流
        Stream<String> emptyStream = Stream.empty();
        show("empty stream",emptyStream);

        // 无限流
        Stream<String> generateStream = Stream.generate(() -> "echo");
        show("generate",generateStream);

        // 无限增长流
        Stream<BigInteger> iterateStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
        show("iterate",iterateStream);
    }
}
