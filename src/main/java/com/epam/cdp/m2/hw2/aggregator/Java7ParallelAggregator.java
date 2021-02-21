package com.epam.cdp.m2.hw2.aggregator;

import java.util.*;

import javafx.util.Pair;

public class Java7ParallelAggregator implements Aggregator {
    private final int THREADS_NUMBER = 3;

    @Override
    public int sum(List<Integer> numbers) {
        int size = getSizeOfSubListForSingleThread(numbers);
        Summation[] threads = new Summation[THREADS_NUMBER];
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads[i] = new Summation(numbers, i * size, (i + 1) * size);
            threads[i].start();
        }
        joinAllThreadsInArray(threads);
        int commonResult = 0;
        for (Summation t : threads) {
            commonResult += t.calculationsResult;
        }
        return commonResult;
    }


    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        int size = getSizeOfSubListForSingleThread(words);
        WordsCounter[] threads = new WordsCounter[THREADS_NUMBER];
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads[i] = new WordsCounter(words, i * size, (i + 1) * size);
            threads[i].start();
        }
        joinAllThreadsInArray(threads);
        Map<String, Long> commonResult = new HashMap<>();
        for (WordsCounter t : threads) {
            for (String s : t.calculationResult.keySet()) {
                if (commonResult.containsKey(s)) {
                    commonResult.put(s, commonResult.get(s) + t.calculationResult.get(s));
                } else {
                    commonResult.put(s, t.calculationResult.get(s));
                }
            }
        }

        LinkedList<Pair<String, Long>> orderedList = new LinkedList<>();
        for (String word : commonResult.keySet()) {
            Pair<String, Long> newPairToInsert = new Pair<>(word, commonResult.get(word));

            if (orderedList.isEmpty()) {
                orderedList.add(newPairToInsert);
                continue;
            }
            if (orderedList.size() == 1) {
                if (newPairToInsert.getValue() > orderedList.getFirst().getValue()) {
                    orderedList.addFirst(newPairToInsert);
                } else {
                    orderedList.add(newPairToInsert);
                }
                continue;
            }
            ListIterator<Pair<String, Long>> listIterator = orderedList.listIterator();
            boolean pairWasInserted = false;
            while (listIterator.hasNext()) {
                if (newPairToInsert.getValue() > listIterator.next().getValue()) {
                    listIterator.previous();
                    listIterator.add(newPairToInsert);
                    pairWasInserted = true;
                    break;
                }
            }
            if (!pairWasInserted) {
                orderedList.addLast(newPairToInsert);
            }
        }
        LinkedList<Pair<String, Long>> listToReturn = new LinkedList<>();
        for (long i = 0; i < limit; i++) {
            if (orderedList.isEmpty()) {
                break;
            }
            Pair<String, Long> firstPair = orderedList.getFirst();
            listToReturn.add(firstPair);
            orderedList.removeFirst();
        }

        return listToReturn;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        int size = getSizeOfSubListForSingleThread(words);
        WordsCounter[] threads = new WordsCounter[THREADS_NUMBER];
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads[i] = new WordsCounter(words, i * size, (i + 1) * size, true);
            threads[i].start();
        }
        joinAllThreadsInArray(threads);
        Map<String, Long> commonResult = new HashMap<>();
        for (WordsCounter t : threads) {
            for (String s : t.calculationResult.keySet()) {
                if (commonResult.containsKey(s)) {
                    commonResult.put(s, commonResult.get(s) + t.calculationResult.get(s));
                } else {
                    commonResult.put(s, t.calculationResult.get(s));
                }
            }
        }

        DuplicateMapper[] threadsForMappingDuplicates = new DuplicateMapper[THREADS_NUMBER];
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threadsForMappingDuplicates[i] = new DuplicateMapper(commonResult, i * size, (i + 1) * size);
            threadsForMappingDuplicates[i].start();
        }
        joinAllThreadsInArray(threadsForMappingDuplicates);
        List<String> listOfDuplicates = new LinkedList<>();
        for (DuplicateMapper t : threadsForMappingDuplicates) {
            listOfDuplicates.addAll(t.calculationResult);
        }

        LinkedList<String> orderedList = new LinkedList<>();
        for (String duplicate : listOfDuplicates) {
            if (orderedList.isEmpty()) {
                orderedList.add(duplicate);
                continue;
            }
            if (orderedList.size() == 1) {
                if (duplicate.length() > orderedList.getFirst().length()) {
                    orderedList.addLast(duplicate);
                } else {
                    orderedList.addFirst(duplicate);
                }
                continue;
            }

            ListIterator<String> listIterator = orderedList.listIterator();
            boolean duplicateWasInserted = false;
            while (listIterator.hasNext()) {
                String nextOrderedListItem = listIterator.next();
                if (duplicate.length() < nextOrderedListItem.length()) {
                    listIterator.previous();
                    listIterator.add(duplicate);
                    duplicateWasInserted = true;
                    break;
                } else if (duplicate.length() == nextOrderedListItem.length()) {
                    if (duplicate.compareTo(nextOrderedListItem) < 1) {
                        listIterator.previous();
                        listIterator.add(duplicate);
                        duplicateWasInserted = true;
                        break;
                    }
                }
            }
            if (!duplicateWasInserted) {
                orderedList.addLast(duplicate);
            }
        }

        LinkedList<String> listToReturn = new LinkedList<>();
        for (long i = 0; i < limit; i++) {
            if (orderedList.isEmpty()) {
                break;
            }
            listToReturn.add(orderedList.getFirst());
            orderedList.removeFirst();
        }
        return listToReturn;
    }

    private int getSizeOfSubListForSingleThread(List list) {
        return (int) Math.ceil(list.size() * 1.0 / THREADS_NUMBER);
    }

    private void joinAllThreadsInArray(Thread[] threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Summation extends Thread {
        private final List<Integer> list;
        private final int low, high;
        private int calculationsResult;

        Summation(List<Integer> list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = Math.min(high, list.size());
        }

        @Override
        public void run() {
            calculationsResult = sumPartOfTheList(list, low, high);
        }

        private int sumPartOfTheList(List<Integer> list, int low, int high) {
            int total = 0;
            for (int i = low; i < high; i++) {
                total += list.get(i);
            }
            return total;
        }
    }

    private class WordsCounter extends Thread {
        private final List<String> list;
        private final int low, high;
        Map<String, Long> calculationResult;
        private final boolean switchResultToUpperCase;

        WordsCounter(List<String> list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = Math.min(high, list.size());
            this.calculationResult = new HashMap<>();
            this.switchResultToUpperCase = false;
        }

        WordsCounter(List<String> list, int low, int high, boolean switchResultToUpperCase) {
            this.list = list;
            this.low = low;
            this.high = Math.min(high, list.size());
            this.calculationResult = new HashMap<>();
            this.switchResultToUpperCase = switchResultToUpperCase;
        }

        @Override
        public void run() {
            for (int i = low; i < high; i++) {
                String currentKey = switchResultToUpperCase?list.get(i).toUpperCase(): list.get(i);
                if (calculationResult.containsKey(currentKey)) {
                    calculationResult.put(currentKey, calculationResult.get(currentKey) + 1L);
                } else {
                    calculationResult.put(currentKey, 1L);
                }
            }
        }
    }

    private class DuplicateMapper extends Thread {
        private final Map<String, Long> map;
        private final List<String> list;
        private final int low, high;
        LinkedList<String> calculationResult;

        DuplicateMapper(Map<String, Long> map, int low, int high) {
            this.map = map;
            this.list = new ArrayList<>(map.keySet());
            this.low = low;
            this.high = Math.min(high, list.size());
            this.calculationResult = new LinkedList<>();
        }

        @Override
        public void run() {
            for (int i = low; i < high; i++) {
                String currentWord = list.get(i);
                if(map.get(currentWord) > 1) {
                    calculationResult.add(currentWord);
                }
            }
        }
    }
}
