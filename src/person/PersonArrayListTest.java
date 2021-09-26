package person;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PersonArrayListTest {

    @Test
    private void runTest(){

        List<Person> list = new PersonGenerator().generate(new ArrayList<Person>(), 10000000L);
        System.out.println(list);
    }
}