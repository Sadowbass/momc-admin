package com.momc.admin.learningtest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class StreamPerformanceTest {

    @Test
    void testStreamPerformance() {
        List<TestObject> list = new ArrayList<>();
        EasyRandom easyRandom = new EasyRandom();

        int loopCount = 10000000;
        for (int i = 0; i < loopCount; i++) {
            TestObject to = new TestObject(i, easyRandom.nextObject(String.class));
            list.add(to);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("===============");
            sequenceStream(list);
            parallelStream(list);
            System.out.println("===============");
            System.out.println();
        }
    }

    private void sequenceStream(List<TestObject> list) {
        System.out.println("StreamPerformanceTest.sequenceStream");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        list.stream()
                .map(this::mapSomeThing)
                .collect(
                        Collectors.toList());
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
    }

    private void parallelStream(List<TestObject> list) {
        System.out.println("StreamPerformanceTest.parallelStream");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        list.stream()
                .parallel()
                .map(this::mapSomeThing)
                .collect(
                        Collectors.toList());
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
    }

    private TestObject mapSomeThing(TestObject testObject) {
        testObject.setIndex(testObject.getIndex() + 1);
        testObject.setIndex(testObject.getIndex() + 1);
        testObject.setIndex(testObject.getIndex() + 1);
        testObject.setIndex(testObject.getIndex() - 1);
        testObject.setIndex(testObject.getIndex() - 1);
        testObject.setIndex(testObject.getIndex() - 1);

        return testObject;
    }

    static class TestObject {

        private int index;
        private String someThing;

        public TestObject(int index, String someThing) {
            this.index = index;
            this.someThing = someThing;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getSomeThing() {
            return someThing;
        }

        public void setSomeThing(String someThing) {
            this.someThing = someThing;
        }
    }
}
