package nl.saxion.assignment_1;

import nl.saxion.BubbleSort;
import nl.saxion.Generator;

/**
 * Withholds source code for assignment 1.1
 * <p>
 * Created by Derwin on 02-Sep-16.
 */
public class Main {
    private static final int AMOUNT = 200000;

    ///////////////////////////////////////////////////////////////////////////
    // Results
    ///////////////////////////////////////////////////////////////////////////
    //25 000 = 984 ms
    //50 000 = 3999 ms
    //100 000 = 16457 ms

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
        BubbleSort.sort(sorted);
        long end = System.currentTimeMillis();

        long time = end - start;
        System.out.println("time: " + time + " ms");
    }
}
