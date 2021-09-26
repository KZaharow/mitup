package person;

import org.apache.commons.lang.math.RandomUtils;

import java.time.LocalDateTime;
import java.util.List;

public class PersonGenerator implements Generator<Person>{

    @Override
    public List<Person> generate(List<Person> list, long times) {

        for (long i = 0; i < times; i++) {
            long l = RandomUtils.nextLong();
            list.add(new Person(l, "Person_" + l, LocalDateTime.now()));
        }
        return list;
    }
}
