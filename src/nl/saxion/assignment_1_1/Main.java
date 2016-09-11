package nl.saxion.assignment_1_1;

import nl.saxion.BubbleSort;
import nl.saxion.Generator;

/**
 * Withholds source code for assignment 1.1
 * <p>
 * Instruction:
 * Write a Java programme that generates a certain amount of whole numbers and places these numbers
 * in an array. Sort these numbers using bubble sort by placing them one by one in the right place in an
 * Array List.
 * <p>
 * Determine how much time your programme needed to perform the sorting. Test your programme at
 * least for 25 000, 50 000, 100 000, 200 000, 400 000 and 800 000 numbers and show the results in the
 * report. What strikes you in these measurements?
 * <p>
 * In order to obtain reliable timing information, it is recommended that you perform several
 * measurements (by running the algorithm in 1 programme start multiple times in succession) and take
 * the average over the measurement results, leaving out the highest and lowest measured value(s).
 * Present your measurement results in a table in the report.
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
        BubbleSort.sort(sorted); //Sort using bubble sort
        long end = System.currentTimeMillis();

        long time = end - start;
        System.out.println("time: " + time + " ms");
    }
}
