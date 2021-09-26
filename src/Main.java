import org.apache.commons.lang.time.StopWatch;
import person.Person;
import person.PersonGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    private static final List<Person> p1000 = new PersonGenerator().generate(new ArrayList<>(), 1000L);
    private static final List<Person> p10000 = new PersonGenerator().generate(new ArrayList<>(), 10000L);
    private static final List<Person> p25000 = new PersonGenerator().generate(new ArrayList<>(), 25000L);
    private static final List<Person> p50000 = new PersonGenerator().generate(new ArrayList<>(), 50000L);
    private static final List<Person> p100000 = new PersonGenerator().generate(new ArrayList<>(), 100000L);
    private static final List<Person> p250000 = new PersonGenerator().generate(new ArrayList<>(), 250000L);
    private static final List<Person> p500000 = new PersonGenerator().generate(new ArrayList<>(), 500000L);
    private static final List<Person> p1000000 = new PersonGenerator().generate(new ArrayList<>(), 1000000L);
    private static final List<List<Person>> tasks = Arrays.asList(p1000, p10000, p25000, p50000, p100000, p250000, p500000, p1000000);
    private static final List<String> log = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        for (List<Person> t : tasks) {
            testPersonArrayList(t, new ArrayList<>());
            parallelTestPersonArrayList(t, new ArrayList<>());
            testPersonArrayList(t, new LinkedList<>());
            parallelTestPersonArrayList(t, new LinkedList<>());
        }

        Files.write(Paths.get("result.txt"), log.toString().getBytes());
    }

    private static void testPersonArrayList(List<Person> t, List<Person> list) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sw.reset();
            sw.start();
            list.stream()
                    .map(person -> person.getName().toUpperCase())
                    .sorted()
                    .collect(Collectors.toList());
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            System.out.println(time);
            log.add("Single-stream time [size=" + t.size() + "], " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Single stream avg test time (size=" + t.size() + "), " + collect + " ms");
    }

    private static void parallelTestPersonArrayList(List<Person> t, List<Person> list) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sw.reset();
            sw.start();
            list.stream()
                    .map(person -> person.getName().toUpperCase())
                    .parallel()
                    .sorted()
                    .collect(Collectors.toList());
            sw.stop();
            long time = sw.getTime();
            timings.add(time);
            System.out.println(time);
            log.add("Parallel-stream time [size=" + t.size() + "], " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        log.add("Parallel stream avg test time (size=" + t.size() + "), " + collect + " ms");
    }
}
