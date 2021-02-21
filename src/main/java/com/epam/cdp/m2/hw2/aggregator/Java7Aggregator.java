package com.epam.cdp.m2.hw2.aggregator;

import java.util.*;

import javafx.util.Pair;

public class Java7Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        Map<String, Long> wordsCountMap = countWords(words);
        LinkedList<Pair<String, Long>> orderedList = makeListOfPairsFromMapOrderedByValue(wordsCountMap);
        return getFirstElementsFromList(limit, orderedList);
    }

    private Map<String, Long> countWords(List<String> words) {
        Map<String, Long> wordsCount = new HashMap<>();
        for (String word : words) {
            if (wordsCount.containsKey(word)) {
                wordsCount.put(word, wordsCount.get(word) + 1);
            } else {
                wordsCount.put(word, 1L);
            }
        }
        return wordsCount;
    }

    private <T> LinkedList<T> getFirstElementsFromList(long limit, LinkedList<T> orderedList) {
        LinkedList<T> listToReturn = new LinkedList<>();
        for (long i = 0; i < limit; i++) {
            if (orderedList.isEmpty()) {
                break;
            }
            T firstPair = orderedList.getFirst();
            listToReturn.add(firstPair);
            orderedList.removeFirst();
        }
        return listToReturn;
    }

    private LinkedList<Pair<String, Long>> makeListOfPairsFromMapOrderedByValue(Map<String, Long> mapForCalculating) {
        LinkedList<Pair<String, Long>> orderedList = new LinkedList<>();
        for (String word : mapForCalculating.keySet()) {
            Pair<String, Long> newPairToInsert = new Pair<>(word, mapForCalculating.get(word));
            if (orderedList.isEmpty()) {
                orderedList.add(newPairToInsert);
                continue;
            }
            if (addPairIfListContainsOnePosition(orderedList, newPairToInsert)) continue;
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
        return orderedList;
    }

    private boolean addPairIfListContainsOnePosition(LinkedList<Pair<String, Long>> orderedList, Pair<String, Long> newPairToInsert) {
        if (orderedList.size() == 1) {
            if (newPairToInsert.getValue() > orderedList.getFirst().getValue()) {
                orderedList.addFirst(newPairToInsert);
            } else {
                orderedList.add(newPairToInsert);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        Map<String, Long> mapWordsCounters = countWordsAndSwitchThemToUppercase(words);
        List<String> listOfDuplicates = getAllDuplicatesFromMap(mapWordsCounters);
        LinkedList<String> orderedList = sortListByWordsLengthDescending(listOfDuplicates);
        return getFirstElementsFromList(limit, orderedList);
    }

    private LinkedList<String> sortListByWordsLengthDescending(List<String> listOfDuplicates) {
        LinkedList<String> orderedList = new LinkedList<>();
        for (String duplicate : listOfDuplicates) {
            if (insertStringIntoEmptyList(orderedList, duplicate)) continue;
            if (insertStringIntoOrderedListWithOneElement(orderedList, duplicate)) continue;
            insertStringIntoOrderedListWithTwoOrMoreElements(orderedList, duplicate);
        }
        return orderedList;
    }

    private boolean insertStringIntoEmptyList(LinkedList<String> orderedList, String duplicate) {
        if (orderedList.isEmpty()) {
            orderedList.add(duplicate);
            return true;
        }
        return false;
    }

    private boolean insertStringIntoOrderedListWithOneElement(LinkedList<String> orderedList, String duplicate) {
        if (orderedList.size() == 1) {
            if (duplicate.length() > orderedList.getFirst().length()) {
                orderedList.addLast(duplicate);
            } else {
                orderedList.addFirst(duplicate);
            }
            return true;
        }
        return false;
    }

    private void insertStringIntoOrderedListWithTwoOrMoreElements(LinkedList<String> orderedList, String duplicate) {
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

    private List<String> getAllDuplicatesFromMap(Map<String, Long> mapWordsCounters) {
        List<String> listOfDuplicates = new LinkedList<>();
        for (String word : mapWordsCounters.keySet()) {
            if (mapWordsCounters.get(word.toUpperCase()) > 1) {
                listOfDuplicates.add(word.toUpperCase());
            }
        }
        return listOfDuplicates;
    }

    private Map<String, Long> countWordsAndSwitchThemToUppercase(List<String> words) {
        Map<String, Long> mapWordsCounters = new HashMap<>();
        for (String word : words) {
            if (mapWordsCounters.containsKey(word.toUpperCase())) {
                mapWordsCounters.put(word.toUpperCase(), mapWordsCounters.get(word.toUpperCase()) + 1);
            } else {
                mapWordsCounters.put(word.toUpperCase(), 1L);
            }
        }
        return mapWordsCounters;
    }
}
