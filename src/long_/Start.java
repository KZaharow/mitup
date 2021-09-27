package long_;

import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Start {

    private static final List<String> log = new ArrayList<>();
    private static final long[] INIT = {100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L};

    private static List<List<LongStream>> getTask(long[] arr) {

        List<List<LongStream>> tasks = new ArrayList<>();
        for (long l : arr) {
            ArrayList<LongStream> LongStreams = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                LongStreams.add(LongStream.rangeClosed(1, l));
            }
            tasks.add(LongStreams);
        }
        return tasks;
    }

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        log.add("\ntestMaxLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testMaxLongStream(t);
        }

        log.add("\ntestMaxParallelLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testMaxParallelLongStream(t);
        }

        log.add("\ntestFilterLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testFilterLongStream(t);
        }

        log.add("\ntestFilterParallelLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testFilterParallelLongStream(t);
        }

        log.add("\ntestMapLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testMapLongStream(t);
        }

        log.add("\ntestMapParallelLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testMapParallelLongStream(t);
        }

        log.add("\ntestComplexLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testComplexLongStream(t);
        }

        log.add("\ntestComplexParallelLongStream");
        for (List<LongStream> t : getTask(INIT)) {
            testComplexParallelLongStream(t);
        }

        String res = "";
        for (String s : log) {
            res += s + "\n";
        }
        Files.write(Paths.get("result_Long.txt"), res.getBytes());
    }

    private static void testMaxLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream.max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testMaxParallelLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testFilterLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .filter(x -> x % 2 == 0)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testFilterParallelLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .filter(x -> x % 2 == 0)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testMapLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testMapParallelLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }

    private static void testComplexLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void testComplexParallelLongStream(List<LongStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (LongStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .max();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            //log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(Long::intValue));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }
}
