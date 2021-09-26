import org.apache.commons.lang.time.StopWatch;
import person.Person;
import person.PersonGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    private static List<Person> p1000 = new PersonGenerator().generate(new ArrayList<>(), 1000L);
    private static List<Person> p10000 = new PersonGenerator().generate(new ArrayList<>(), 10000L);
    private static List<Person> p25000 = new PersonGenerator().generate(new ArrayList<>(), 25000L);
    private static List<Person> p50000 = new PersonGenerator().generate(new ArrayList<>(), 50000L);
    private static List<Person> p100000 = new PersonGenerator().generate(new ArrayList<>(), 100000L);
    private static List<Person> p250000 = new PersonGenerator().generate(new ArrayList<>(), 250000L);
    private static List<Person> p500000 = new PersonGenerator().generate(new ArrayList<>(), 500000L);
    private static List<Person> p1000000 = new PersonGenerator().generate(new ArrayList<>(), 1000000L);
    private static List<List<Person>> tasks = Arrays.asList(p1000, p10000, p250000, p50000, p100000, p250000, p500000, p1000000);

    public static void main(String[] args) {

        test();
    }

    private static void test() {

        for (List<Person> t : tasks) {
            testPersonArrayList(t);
            parallelTestPersonArrayList(t);
        }
    }

    private static void testPersonArrayList(List<Person> t, List) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sw.reset();
            sw.start();
            t.stream()
                    .map(person -> person.getName().toUpperCase())
                    .sorted()
                    .collect(Collectors.toList());
            sw.stop();
            long_ time = sw.getTime();
            timings.add(time);
            System.out.println("Single-stream time [size=" + t.size() + "], " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        System.out.println("Single stream avg test time (size=" + t.size() + "), " + collect + " ms");
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
            long_ time = sw.getTime();
            timings.add(time);
            System.out.println("Parallel-stream time [size=" + t.size() + "], " + time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        System.out.println("Parallel stream avg test time (size=" + t.size() + "), " + collect + " ms");
    }
}
