package com.epam.cdp.m2.hw2.aggregator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PerformanceTesting {
    public static void start() {
        List<Aggregator> aggregators = new ArrayList<>();

        aggregators.add(new Java7Aggregator());
        aggregators.add(new Java8Aggregator());
        aggregators.add(new Java7ParallelAggregator());
        aggregators.add(new Java8ParallelAggregator());

        List<List<Integer>> tasksForSum = new ArrayList<>();
        tasksForSum.add(generateListOfIntegers(100_000));
        tasksForSum.add(generateListOfIntegers(1_000_000));
        tasksForSum.add(generateListOfIntegers(10_000_000));

        List<List<String>> tasksForFrequency = new ArrayList<>();
        tasksForFrequency.add(generateListOfStrings(2_000));
        tasksForFrequency.add(generateListOfStrings(5_000));
        tasksForFrequency.add(generateListOfStrings(10_000));

        List<List<String>> tasksForDuplicates = new ArrayList<>();
        tasksForDuplicates.add(generateListOfStrings(10_000));
        tasksForDuplicates.add(generateListOfStrings(100_000));
        tasksForDuplicates.add(generateListOfStrings(300_000));

        runMethodOnListOfTasks(Methods.SUM.name(), tasksForSum, 20, aggregators);
        runMethodOnListOfTasks(Methods.FREQUENCY.name(), tasksForFrequency, 20, aggregators);
        runMethodOnListOfTasks(Methods.DUPLICATES.name(), tasksForDuplicates, 20, aggregators);
    }

    private static <T> void runMethodOnListOfTasks(String method, List<List<T>> tasks, int runs, List<Aggregator> aggregators) {
        System.out.println("*** " + method + " ***");
        for (List<T> task : tasks) {
            for (Aggregator aggregator : aggregators) {
                double executionTime = 0;
                for (int i = 0; i < runs; i++) {
                    long testStart = System.currentTimeMillis();
                    performSuitableMethod(method, task, aggregator);
                    long testEnd = System.currentTimeMillis();
                    executionTime += testEnd - testStart;
                }
                executionTime = executionTime / runs;
                System.out.println(executionTime + " ms -- Processing list with " + task.size() + " elements with " + aggregator.getClass().getSimpleName());
            }
            System.out.println();
        }
    }

    private static <T> void performSuitableMethod(String method, List<T> task, Aggregator aggregator) {
        switch (method) {
            case "SUM":
                aggregator.sum((List<Integer>) task);
                break;
            case "FREQUENCY":
                aggregator.getMostFrequentWords((List<String>) task, 20L);
                break;
            case "DUPLICATES":
                aggregator.getDuplicates((List<String>) task, 20L);
                break;
            default:
                throw new NotImplementedException();
        }
    }

    private static List<Integer> generateListOfIntegers(int size) {
        return IntStream.generate(() -> getRandomNumberInRange(-500, 500))
                .boxed()
                .limit(size)
                .collect(Collectors.toList());
    }

    private static List<String> generateListOfStrings(int size) {
        return Stream.generate(() -> generateString(getRandomNumberInRange(4, 6)))
                .limit(size)
                .collect(Collectors.toList());
    }

    private static String generateString(int size) {
        StringBuilder sb = new StringBuilder();
        IntStream.generate(PerformanceTesting::getRandomChar)
                .mapToObj(e -> (char) e)
                .limit(size)
                .forEach(sb::append);
        return sb.toString();
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static char getRandomChar() {
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return alphabet.charAt(r.nextInt(alphabet.length()));
    }

    enum Methods {
        SUM,
        FREQUENCY,
        DUPLICATES
    }

}
