package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.util.stream.Collectors.*;

public class Java8ParallelAggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.parallelStream()
                .mapToInt(x -> x)
                .reduce(0, Integer::sum);
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        return words.parallelStream()
                .collect(groupingByConcurrent(e -> e, counting()))
                .entrySet()
                .parallelStream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .sorted((e1, e2) -> Math.toIntExact(e2.getValue()-e1.getValue()))
                .limit(limit)
                .collect(toList());
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        Comparator<String> myComparator = (s1, s2) -> {
            if (s1.length() > s2.length()) {
                return 1;
            } else if (s1.length() < s2.length()) {
                return -1;
            }
            return s1.compareTo(s2);
        };

        return words.parallelStream()
                .collect(groupingByConcurrent(String::toUpperCase, counting()))
                .entrySet()
                .parallelStream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted(myComparator)
                .limit(limit)
                .collect(toList());
    }
}
