package cors;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        parallel();
    }

    private static void parallel() {

        System.out.println("CPU: " + Runtime.getRuntime().availableProcessors());
        System.out.println("freeMemory" + Runtime.getRuntime().freeMemory());
        System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());
        System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
        Set<String> collect = IntStream.range(0, Integer.MAX_VALUE / 2)
                .parallel()
                .mapToObj(i -> Thread.currentThread().getName())
                .collect(Collectors.toSet());
        System.out.println(collect);
    }
}
