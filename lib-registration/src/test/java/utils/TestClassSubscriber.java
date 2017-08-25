package utils;

import java.util.Arrays;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

public class TestClassSubscriber<T> extends TestSubscriber<T> {

    public void assertTypeSequence(Class... classes) {
        assertTypeSequence(Arrays.asList(classes));
    }

    public void assertTypeSequence(List<Class> expectedSequence) {
        if (expectedSequence.size() != values.size())
            throw fail("unequal sequence size. expected:" + expectedSequence.size() + " actual:" + values.size());

        for (int i = 0; i < values.size(); i++) {
            Object actual = values.get(i);
            Class expected = expectedSequence.get(i);
            if (!actual.getClass().equals(expected)) {
                throw fail("invalid class. expected: " + expected + " actual:" + actual.getClass());
            }
        }
    }
}
