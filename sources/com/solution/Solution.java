package com.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    
    public static Integer getShortestTransformationLength(String start, String end, String[] dictionary) {  
        if (!bothWordsAreInTheDictionary(start, end, dictionary)) return -1;
        
        HashMap<String, ArrayList<String>> relatedWordsMap = generateRelatedWordsMap(dictionary);
        Integer depth = 0;
        Boolean intersected = false;
        
        Queue<String> toVisitFromStart = new LinkedList<>();
        Queue<String> toVisitFromEnd = new LinkedList<>();
        HashSet<String> visitedFromStart = new HashSet<>();
        HashSet<String> visitedFromEnd = new HashSet<>();
        
        toVisitFromStart.add(start);
        visitedFromStart.add(start);
        toVisitFromEnd.add(end);
        visitedFromEnd.add(end);
        
        // apply bidirectional breadth-first search from start and end words
        while (!toVisitFromStart.isEmpty() && !toVisitFromEnd.isEmpty()) {
            // search from start
            depth++;
            intersected = searchAtParticularDepth(relatedWordsMap, toVisitFromStart, visitedFromStart, visitedFromEnd);
            if (intersected) return depth-1;
            // search from end
            depth++;
            intersected = searchAtParticularDepth(relatedWordsMap, toVisitFromEnd, visitedFromEnd, visitedFromStart);
            if (intersected) return depth-1;
        }
        
        return -1;
    }
    
    public static boolean bothWordsAreInTheDictionary(String word1, String word2, String[] dictionary) {
        boolean firstWordIn = false;
        boolean secondWordIn = false;
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i].equals(word1)) {
                firstWordIn = true;
            }
            if (dictionary[i].equals(word2)) {
                secondWordIn = true;
            }
            if (firstWordIn && secondWordIn) return true;
        }
        return false;
    }
    
    public static HashMap<String, ArrayList<String>> generateRelatedWordsMap(String[] dictionary) {
        HashMap<String, ArrayList<String>> relatedWordsMap = new HashMap<>();
        
        for (int i = 0; i < dictionary.length; i++) {
            String word = dictionary[i];
            for (int j = 0; j < word.length(); j++) {
                String w = word.substring(0, j) + "*" + word.substring(j+1);
                addRelatedWordMapping(relatedWordsMap, word, w);
            }
        }
        
        return relatedWordsMap;
    }
    
    public static void addRelatedWordMapping(HashMap<String, ArrayList<String>> dictionary, String originalWord, String relatedWord) {
        ArrayList<String> list;
        
        if (dictionary.containsKey(relatedWord)) {
            list= dictionary.get(relatedWord);
        } else {
            list = new ArrayList<>();
        }
        list.add(originalWord);
        dictionary.put(relatedWord, list);
    }
    
    public static Boolean searchAtParticularDepth(HashMap<String, ArrayList<String>> dictionary, 
                            Queue<String> toVisitFromStart, HashSet<String> visitedFromStart, HashSet<String> visitedFromGoal) {
        for (int i = 0; i < toVisitFromStart.size(); i++) {
            String word = toVisitFromStart.poll();
            
            // if frontiers of two searches intersect, we found our solution
            if (visitedFromGoal.contains(word)) {
                return true;
            }
            
            // get all words which can be produced by replacing one character in the current word
            ArrayList<String> words = getAllPossibleIntermediateWordsFromCurrentWord(word, dictionary);
            
            // add selected words to frontier
            for (String w : words) {
                if (!visitedFromStart.contains(w)) {
                    toVisitFromStart.add(w);
                    visitedFromStart.add(w);
                }
            }
        }
        return false;
    }
    
    public static ArrayList<String> getAllPossibleIntermediateWordsFromCurrentWord(String currentWord,
                                            HashMap<String, ArrayList<String>> dictionary) {
        ArrayList<String> words = new ArrayList<>();
        
        for (int j = 0; j < currentWord.length(); j++) {
            String w = currentWord.substring(0, j) + "*" + currentWord.substring(j+1);
            ArrayList<String> candidateWords = dictionary.get(w);
            candidateWords.remove(currentWord);
            words.addAll(candidateWords);
        }
        
        return words;
    }

}
