package int_;

import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.IntStream;

public class Start {

    private static final List<List<IntStream>> tasks1 = new ArrayList<>();
    private static final List<List<IntStream>> tasks2 = new ArrayList<>();
    private static final List<String> log = new ArrayList<>();

    static {

        int[] arr = {1000, 10000, 25000, 50000, 100000, 250000, 500000, 1000000, Integer.MAX_VALUE};
        for (int l : arr) {
            ArrayList<IntStream> IntStreams = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                IntStreams.add(IntStream.rangeClosed(1, l));
            }
            tasks1.add(IntStreams);
        }
        for (int l : arr) {
            ArrayList<IntStream> IntStreams = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                IntStreams.add(IntStream.rangeClosed(1, l));
            }
            tasks2.add(IntStreams);
        }

    }

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        log.add("Int single");
        for (List<IntStream> t : tasks1) {
            testPersonArrayList(t);
        }
        log.add("\nInt parallel");
        for (List<IntStream> t : tasks2) {
            parallelTestPersonArrayList(t);
        }

        String res = "";
        for (String s : log) {
            res += s + "\n";
        }
        Files.write(Paths.get("result_int.txt"), res.getBytes());
    }

    private static void testPersonArrayList(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Single-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Single stream avg test time: " + collect + " ms");
    }

    private static void parallelTestPersonArrayList(List<IntStream> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (IntStream stream : t) {
            sw.reset();
            sw.start();
            stream.parallel()
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            log.add("Parallel-stream time: " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Parallel stream avg test time: " + collect + " ms");
    }
}
