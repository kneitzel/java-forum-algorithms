package de.kneitzel.sort;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortTests {

    /**
     * Tests the QuickSort.sort method.
     */
    @Test
    public void testSort() {
        Integer[] arrayToSort = { 19, 1, 18, 2, 5, 9, 3};
        Integer[] expectedArray = { 1, 2, 3, 5, 9, 18, 19};

        QuickSort.sort(arrayToSort);
        Assert.assertArrayEquals(expectedArray, arrayToSort);
    }
}
