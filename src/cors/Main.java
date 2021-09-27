package cors;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        getInfo();
    }

    private static void getInfo() {

        System.out.println("CPU: " + Runtime.getRuntime().availableProcessors());
        System.out.println("freeMemory: " + Runtime.getRuntime().freeMemory() / 1048576);
        System.out.println("maxMemory: " + Runtime.getRuntime().maxMemory() / 1048576);
        System.out.println("totalMemory: " + Runtime.getRuntime().totalMemory() / 1048576);
        Set<String> collect = IntStream.range(0, Integer.MAX_VALUE / 20)
                .parallel()
                .mapToObj(i -> Thread.currentThread().getName() + "\n")
                .collect(Collectors.toSet());
        System.out.println(collect);
    }
}
