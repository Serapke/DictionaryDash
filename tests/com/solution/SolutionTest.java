package com.solution;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolutionTest {
    
    private static final String EXAMPLE_START = "hit";
    private static final String EXAMPLE_END = "cog";
    private static final String[] EXAMPLE_DICTIONARY = { "hit", "dot", "dog", "cog", "hot", "log" };
    private static final Integer EXAMPLE_ANSWER = 4;
    
    private static final String[] DICTIONARY = { "hit", "dot", "dog", "cog", "hot", "log", "man" };
    private static final Integer IMPOSSIBLE_ANSWER = -1;
    private static final Integer SAME_WORDS_ANSWER = 0;
    private static final Integer SINGLE_TRANSFORMATION_ANSWER = 1;

    @Test
    public void testWithGivenExample() {
        assertEquals(EXAMPLE_ANSWER, Solution.getShortestTransformationLength(EXAMPLE_START, EXAMPLE_END, EXAMPLE_DICTIONARY));
    }
    
    @Test
    public void testWithSameStartAndEndWords() {
        assertEquals(SAME_WORDS_ANSWER, Solution.getShortestTransformationLength("hit", "hit", EXAMPLE_DICTIONARY));
    }
    
    @Test
    public void testWithSingleTransformation() {
        assertEquals(SINGLE_TRANSFORMATION_ANSWER, Solution.getShortestTransformationLength("hit", "hot", EXAMPLE_DICTIONARY));
    }
    
    @Test
    public void testWithWordNotInDictionary() {
        assertEquals(IMPOSSIBLE_ANSWER, Solution.getShortestTransformationLength("hit", "bog", EXAMPLE_DICTIONARY));
    }
    
    @Test
    public void testImpossible() {
        assertEquals(IMPOSSIBLE_ANSWER, Solution.getShortestTransformationLength("hit", "man", DICTIONARY));
    }

}
