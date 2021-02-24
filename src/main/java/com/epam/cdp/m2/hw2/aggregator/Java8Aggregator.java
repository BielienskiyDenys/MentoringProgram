package com.epam.cdp.m2.hw2.aggregator;

import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;

import static java.util.stream.Collectors.*;

public class Java8Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::sum);
    }


    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        Comparator<Pair<String, Long>> myComparator = (p1, p2) -> {
            if (p1.getValue() < p2.getValue()) {
                return 1;
            } else if (p1.getValue() > p2.getValue()) {
                return -1;
            }
            return p1.getKey().compareTo(p2.getKey());
        };

        return words.stream()
                .collect(Collectors.groupingBy(e -> e, counting()))
                .entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .sorted(myComparator)
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

        Set<String> uniques = new HashSet<>();
        return words.stream()
                .map(String::toUpperCase)
                .filter(e -> !uniques.add(e))
                .sorted(myComparator)
                .limit(limit)
                .collect(toList());

// Solution without outer Set
//        return words.stream()
//                .collect(groupingBy(String::toUpperCase, counting()))
//                .entrySet()
//                .stream()
//                .filter(e -> e.getValue()>1)
//                .map(Map.Entry::getKey)
//                .sorted(myComparator)
//                .limit(limit)
//                .collect(toList());
    }
}