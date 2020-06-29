package de.kneitzel.sort;

/**
 * Implementation of the QuickSort algorithm.
 * <p>
 *     This is an implementation of the QuickSort ALgorithm that sorts an array of T (T implements Comparable).
 * </p>
 */
public class QuickSort <T extends Comparable<T>> {

    /**
     * Sorts the Array of T elements.
     * @param array Array of T elements.
     * @param <T> A type that is Comparable.
     */
    public static <T extends Comparable<T>> void sort(final T[] array) {
        new QuickSort<T>().quickSort(array, 0, array.length-1);
    }

    void quickSort(final T[] arary, final int leftBorder, final int rightBorder) {
        if (leftBorder >= rightBorder) return;

        int pivotIndex = divide(arary, leftBorder, rightBorder);
        quickSort(arary, leftBorder, pivotIndex - 1);
        quickSort(arary, pivotIndex + 1, rightBorder);
    }

    /**
     * Divide the array into 2 arrays. Pivot element can be any element, we take the right one.
     * @param array Array with values to sort.
     * @param leftBorder left border.
     * @param rightBorder right border.
     * @return element which divides the array into 2 arrays.
     */
    int divide(final T[] array, final int leftBorder, final int rightBorder) {
        int leftIndex = leftBorder;
        int rightIndex = rightBorder-1;
        int pivotIndex = rightBorder;

        T pivotValue = array[pivotIndex];

        while (leftIndex < rightIndex) {
            while (leftIndex <= rightIndex && array[leftIndex].compareTo(pivotValue) < 0)
                leftIndex++;

            while (rightIndex >= leftIndex && array[rightIndex].compareTo(pivotValue) >= 0)
                rightIndex--;

            if (leftIndex < rightIndex) {
                swap(array, leftIndex, rightIndex);
            }
        }

        if (array[leftIndex].compareTo(pivotValue) > 0)
            swap(array, leftIndex, pivotIndex);

        return leftIndex;
    }

    /**
     * Gets the index of the pivot element.
     * <
     * @param leftIndex Left index.
     * @param rightIndex Right index.
     * @return The middle between left and right index.
     */
    int choosePivotIndex(int leftIndex, int rightIndex) {
        return (leftIndex + rightIndex) / 2;
    }

    /**
     * Swap 2 elements in the Array.
     * @param array Array to swap elements in.
     * @param index1 Index of first element to swap.
     * @param index2 Index of second element to swap.
     */
    void swap(T[] array, int index1, int index2) {
        if (index1 != index2) {
            T tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
        }
    }
}
