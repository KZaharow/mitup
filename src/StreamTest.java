import org.apache.commons.lang.time.StopWatch;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class StreamTest {

    private LongStream getStr() {

        return LongStream.rangeClosed(1L, 10000000L);
    }


    public static void main(String[] args) {

        StopWatch sw = new StopWatch();
        ArrayList<Long> timings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LongStream str = new StreamTest().getStr();
            sw.reset();
            sw.start();
            str
                    .filter(x -> x % 2 == 0)
                    .map(x -> x * 2)
                    .count();
            sw.stop();
            long_ time = sw.getTime();
            timings.add(time);
            System.out.println(time + " ms");
        }
        Double collect = timings.stream().collect(Collectors.averagingInt(x -> x.intValue()));
        System.out.println(collect + " ms");
    }
}
