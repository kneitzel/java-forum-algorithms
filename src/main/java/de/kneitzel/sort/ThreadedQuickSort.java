package de.kneitzel.sort;

public class ThreadedQuickSort<T extends Comparable<T>> extends QuickSort<T> {

    /**
     * Sorts the Array of T elements.
     * @param array Array of T elements.
     * @param <T> A type that is Comparable.
     */
    public static <T extends Comparable<T>> void sort(final T[] array) {
        new ThreadedQuickSort<T>().quickSort(array, 0, array.length-1);
    }

    @Override
    void quickSort(final T[] arary, final int leftBorder, final int rightBorder) {
        if (leftBorder >= rightBorder) return;

        int pivotIndex = divide(arary, leftBorder, rightBorder);

        Thread thread = startThread(() -> quickSort(arary, leftBorder, pivotIndex - 1));
        quickSort(arary, pivotIndex + 1, rightBorder);
        waitForThread(thread);
    }

    Thread startThread(final Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    void waitForThread(final Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
