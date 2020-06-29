package de.kneitzel.sort;

public class ThreadedQuickSortWithLimitedThreads<T extends Comparable<T>> extends ThreadedQuickSort<T> {

    /**
     * Maximum number of Threads to start.
     */
    public static final int THREAD_LIMIT = 8;

    /**
     * Count of Threads
     */
    private volatile int threadCount = 0;

    /**
     * Lock object for sync block.
     */
    private final Object lock = new Object();

    /**
     * Sorts the Array of T elements.
     * @param array Array of T elements.
     * @param <T> A type that is Comparable.
     */
    public static <T extends Comparable<T>> void sort(final T[] array) {
        new ThreadedQuickSort<T>().quickSort(array, 0, array.length-1);
    }

    @Override
    Thread startThread(final Runnable runnable) {
        if (canStartThread()) {
            Thread thread = new Thread(runnable);
            thread.start();
            return thread;
        } else {
            runnable.run();
            return null;
        }
    }

    boolean canStartThread() {
        if (threadCount >= THREAD_LIMIT) return false;
        synchronized (lock) {
            if (threadCount >= THREAD_LIMIT) return false;
            threadCount++;
            return true;
        }
    }

    @Override
    void waitForThread(final Thread thread) {
        try {
            if (thread == null) return;
            thread.join();

            synchronized (lock) {
                threadCount--;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
