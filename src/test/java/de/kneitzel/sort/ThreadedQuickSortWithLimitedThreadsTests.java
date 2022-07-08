package de.kneitzel.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ThreadedQuickSortWithLimitedThreadsTests {
    /**
     * Tests the ThreadedQuickSortWithLimitedThreads.sort method.
     */
    @Test
    public void testSort() {
        Integer[] arrayToSort = { 19, 1, 18, 2, 5, 9, 3};
        Integer[] expectedArray = { 1, 2, 3, 5, 9, 18, 19};

        ThreadedQuickSortWithLimitedThreads.sort(arrayToSort);
        assertArrayEquals(expectedArray, arrayToSort);
    }
}
