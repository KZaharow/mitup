package long_;

import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Start {

    private static final List<List<LongStream>> tasks1 = new ArrayList<>();
    private static final List<List<LongStream>> tasks2 = new ArrayList<>();
    private static final List<String> log = new ArrayList<>();

    static {

        long[] arr = {1000L, 10000L, 25000, 50000L, 100000L, 250000L, 500000L, 1000000L, Integer.MAX_VALUE};
        for (long l : arr) {
            ArrayList<LongStream> longStreams = new ArrayList<>();
            for (long i = 0; i < 10; i++) {
                longStreams.add(LongStream.rangeClosed(1L, l));
            }
            tasks1.add(longStreams);
        }
        for (long l : arr) {
            ArrayList<LongStream> longStreams = new ArrayList<>();
            for (long i = 0; i < 10; i++) {
                longStreams.add(LongStream.rangeClosed(1L, l));
            }
            tasks2.add(longStreams);
        }

    }

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        log.add("Long single");
        for (List<LongStream> t : tasks1) {
            testPersonArrayList(t);
        }
        log.add("\nLong parallel");
        for (List<LongStream> t : tasks2) {
            parallelTestPersonArrayList(t);
        }

        String res = "";
        for (String s : log) {
            res += s + "\n";
        }
        Files.write(Paths.get("result_Long.txt"), res.getBytes());
    }

    private static void testPersonArrayList(List<LongStream> t) {

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
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void parallelTestPersonArrayList(List<LongStream> t) {

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
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }
}
