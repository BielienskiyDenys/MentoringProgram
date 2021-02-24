package com.epam.cdp.m2.hw2.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PerformanceTesting {
    public static void start() {
        List<Aggregator> aggregatorList = new ArrayList<>();

        aggregatorList.add(new Java7Aggregator());
        aggregatorList.add(new Java8Aggregator());
        aggregatorList.add(new Java7ParallelAggregator());
        aggregatorList.add(new Java8ParallelAggregator());

        List<List<Integer>> listOfIntegerLists = new ArrayList<>();
        listOfIntegerLists.add(generateListOfIntegers(100_000));
        listOfIntegerLists.add(generateListOfIntegers(1_000_000));
        listOfIntegerLists.add(generateListOfIntegers(10_000_000));

        List<List<String>> listOfStringListsForFrequency = new ArrayList<>();
        listOfStringListsForFrequency.add(generateListOfStrings(2_000));
        listOfStringListsForFrequency.add(generateListOfStrings(5_000));
        listOfStringListsForFrequency.add(generateListOfStrings(10_000));

        List<List<String>> listOfStringListsForDuplicates = new ArrayList<>();
        listOfStringListsForDuplicates.add(generateListOfStrings(10_000));
        listOfStringListsForDuplicates.add(generateListOfStrings(100_000));
        listOfStringListsForDuplicates.add(generateListOfStrings(300_000));

        runSummationTaskOnListsForAllAggregatorsAndPrintResult(aggregatorList, listOfIntegerLists, 20);
        runFrequentStringSearchTaskOnListsForAllAggregatorsAndPrintResult(aggregatorList, listOfStringListsForFrequency, 20);
        runDuplicatesTaskOnListsForAllAggregatorsAndPrintResult(aggregatorList, listOfStringListsForDuplicates, 20);

    }

    private static void runSummationTaskOnListsForAllAggregatorsAndPrintResult(List<Aggregator> aggregatorList, List<List<Integer>> listOfTaskLists, int runs) {
        System.out.println("*** SUMMATION ***");
        for(List<Integer> currentList: listOfTaskLists) {
            for(Aggregator currentAggregator: aggregatorList) {
                double executionTime = 0;
                for(int i = 0; i<runs; i++){
                long testStart = System.currentTimeMillis();
                currentAggregator.sum(currentList);
                long testEnd = System.currentTimeMillis();
                executionTime += testEnd - testStart;}
                executionTime = executionTime/runs;
                System.out.println(executionTime + " ms -- Processing list with " + currentList.size() + " elements with " + currentAggregator.getClass().getSimpleName());
            }
            System.out.println();
        }
    }

    private static void runFrequentStringSearchTaskOnListsForAllAggregatorsAndPrintResult(List<Aggregator> aggregatorList, List<List<String>> listOfTaskLists, int runs) {
        System.out.println("*** FREQUENT STRINGS SEARCH ***");
        for(List<String> currentList: listOfTaskLists) {
            for(Aggregator currentAggregator: aggregatorList) {
                double executionTime = 0;
                for(int i = 0; i<runs; i++) {
                    long testStart = System.currentTimeMillis();
                    currentAggregator.getMostFrequentWords(currentList, 50L);
                    long testEnd = System.currentTimeMillis();
                    executionTime += testEnd - testStart;
                }
                executionTime = executionTime/runs;
                System.out.println(executionTime + " ms -- Processing list with " + currentList.size() + " elements with " + currentAggregator.getClass().getSimpleName());
            }
            System.out.println();
        }
    }

    private static void runDuplicatesTaskOnListsForAllAggregatorsAndPrintResult(List<Aggregator> aggregatorList, List<List<String>> listOfTaskLists, int runs) {
        System.out.println("*** DUPLICATES SEARCH ***");
        for(List<String> currentList: listOfTaskLists) {
            for(Aggregator currentAggregator: aggregatorList) {
                double executionTime = 0;
                for(int i = 0; i<runs; i++) {
                long testStart = System.currentTimeMillis();
                currentAggregator.getDuplicates(currentList, 50L);
                long testEnd = System.currentTimeMillis();
                    executionTime += testEnd - testStart;
                }
                executionTime = executionTime/runs;
                System.out.println(executionTime + " ms -- Processing list with " + currentList.size() + " elements with " + currentAggregator.getClass().getSimpleName());
            }
            System.out.println();
        }
    }

    private static List<Integer> generateListOfIntegers(int size) {
        return IntStream.generate(() -> getRandomNumberInRange(-500, 500))
                .boxed()
                .limit(size)
                .collect(Collectors.toList());
    }

    private static List<String> generateListOfStrings(int size) {
        return Stream.generate(() -> generateString(5))
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
        return  alphabet.charAt(r.nextInt(alphabet.length()));
    }

}
