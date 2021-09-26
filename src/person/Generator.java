package person;

import java.util.List;

public interface Generator<T> {

    List<T> generate(List<T> list, long times);
}
