package nl.saxion.assignment_2;

import nl.saxion.Generator;
import nl.saxion.MergeSort;

/**
 * Withholds source code for assignment 1.2
 * <p>
 * Created by Derwin on 02-Sep-16.
 */
public class Main {
    private static final int AMOUNT = 800000;

    ///////////////////////////////////////////////////////////////////////////
    // Results
    ///////////////////////////////////////////////////////////////////////////
    //25 000 = 7ms
    //50 000 = 13ms
    //100 000 = 19ms
    //200 000 = 31ms
    //400 000 = 56ms
    //800 000 = 122ms

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
        MergeSort.sort(sorted);
        long end = System.currentTimeMillis();

        long time = end - start;
        System.out.println("time: " + time + " ms");
    }
}