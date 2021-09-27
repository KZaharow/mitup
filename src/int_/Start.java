package int_;

import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Start {

    private static final List<String> log = new ArrayList<>();
    private static final int[] INIT = {1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, Integer.MAX_VALUE / 100};

    private static List<List<IntStream>> getTask(int[] arr) {

        List<List<IntStream>> tasks = new ArrayList<>();
        for (int l : arr) {
            ArrayList<IntStream> IntStreams = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                IntStreams.add(IntStream.rangeClosed(1, l));
            }
            tasks.add(IntStreams);
        }
        return tasks;
    }

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        log.add("\ntestMaxIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testMaxIntStream(t);
        }

        log.add("\ntestMaxParallelIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testMaxParallelIntStream(t);
        }

        log.add("\ntestFilterIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testFilterIntStream(t);
        }

        log.add("\ntestFilterParallelIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testFilterParallelIntStream(t);
        }

        log.add("\ntestMapIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testMapIntStream(t);
        }

        log.add("\ntestMapParallelIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testMapParallelIntStream(t);
        }

        log.add("\ntestComplexIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testComplexIntStream(t);
        }

        log.add("\ntestComplexParallelIntStream");
        for (List<IntStream> t : getTask(INIT)) {
            testComplexParallelIntStream(t);
        }

        String res = "";
        for (String s : log) {
            res += s + "\n";
        }
        Files.write(Paths.get("result_int.txt"), res.getBytes());
    }

    private static void testMaxIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testMaxParallelIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testFilterIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .filter(x -> x % 2 == 0)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testFilterParallelIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .filter(x -> x % 2 == 0)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testMapIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testMapParallelIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testComplexIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testComplexParallelIntStream(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }
}
