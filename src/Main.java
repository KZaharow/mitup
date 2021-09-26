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

    private static final List<Person> p1000l = new PersonGenerator().generate(new LinkedList<>(), 1000L);
    private static final List<Person> p10000l = new PersonGenerator().generate(new LinkedList<>(), 10000L);
    private static final List<Person> p25000l = new PersonGenerator().generate(new LinkedList<>(), 25000L);
    private static final List<Person> p50000l = new PersonGenerator().generate(new LinkedList<>(), 50000L);
    private static final List<Person> p100000l = new PersonGenerator().generate(new LinkedList<>(), 100000L);
    private static final List<Person> p250000l = new PersonGenerator().generate(new LinkedList<>(), 250000L);
    private static final List<Person> p500000l = new PersonGenerator().generate(new LinkedList<>(), 500000L);
    private static final List<Person> p1000000l = new PersonGenerator().generate(new LinkedList<>(), 1000000L);


    private static final List<List<Person>> tasks1 = Arrays.asList(p1000, p10000, p25000, p50000, p100000, p250000, p500000, p1000000);
    private static final List<List<Person>> tasks2 = Arrays.asList(p1000l, p10000l, p25000l, p50000l, p100000l, p250000l, p500000l, p1000000l);
    private static final List<String> log = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        test();
    }

    private static void test() throws IOException {

        log.add("ArrayList");
        for (List<Person> t : tasks1) {
            testPersonArrayList(t);
            parallelTestPersonArrayList(t);
        }

        log.add("LinkedList");
        for (List<Person> t : tasks2) {
            testPersonArrayList(t);
            parallelTestPersonArrayList(t);
        }

        String res = "";
        for (String s : log) {
            res += s + "\n";
        }
        Files.write(Paths.get("result.txt"), res.getBytes());
    }

    private static void testPersonArrayList(List<Person> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sw.reset();
            sw.start();
            List<String> collect = t.stream()
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

    private static void parallelTestPersonArrayList(List<Person> t) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sw.reset();
            sw.start();
            t.stream()
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
