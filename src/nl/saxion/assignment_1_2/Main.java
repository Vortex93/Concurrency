package nl.saxion.assignment_1_2;

import nl.saxion.BubbleSort;
import nl.saxion.Generator;

import java.util.Arrays;

/**
 * Withholds source code for assignment 1.2
 * <p>
 * Instruction:
 * The same as in assignment 1.1, but now have the sorting (bubble sort) performed by 2 threads each
 * processing one half of the array. The main thread can then simply determine the sorting of the whole
 * array by merging these assorted partial results. How much time was needed now? What is the
 * connection with the measurement results from the previous assignment? How do you explain this?
 * <p>
 * Created by Derwin on 02-Sep-16.
 */
public class Main {
    private static final int AMOUNT = 800000;

    ///////////////////////////////////////////////////////////////////////////
    // Results
    ///////////////////////////////////////////////////////////////////////////
    //25 000 = 317 ms
    //50 000 = 1081 ms
    //100 000 = 4126 ms
    //200 000 = 16439 ms
    //400 000 = 64748 ms

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private int[] sorted = new int[AMOUNT];


    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Generator.generate(sorted);

        long start = System.currentTimeMillis();
        //Split the array
        int[] left = Arrays.copyOfRange(sorted, 0, sorted.length / 2);
        int[] right = Arrays.copyOfRange(sorted, sorted.length / 2, sorted.length);

        //Create 2 threads and specify their runnables
        Thread thread1 = new Thread(() -> BubbleSort.sort(left));
        Thread thread2 = new Thread(() -> BubbleSort.sort(right));

        //Start both threads
        thread1.start();
        thread2.start();

        try {
            //Wait for both threads to finish
            thread1.join();
            thread2.join();
        } catch (InterruptedException ignored) {
            //Ignore
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("time: " + time + " ms");
    }
}