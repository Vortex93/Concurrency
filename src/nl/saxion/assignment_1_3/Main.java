package nl.saxion.assignment_1_3;

import nl.saxion.Generator;
import nl.saxion.MergeSort;

import java.util.Arrays;

/**
 * Determine the sorting of the whole array by dividing the array in two halves, as in assignment 1.2, and
 * start a thread for each half, each thread being responsible for sorting the numbers assigned to it. This
 * time, if the amount of numbers is 'too high' (above a certain threshold), the thread will start 2 new
 * threads for sorting the numbers assigned to it, which will each process half of the numbers, and the
 * 'outsourcing' thread will determine the sorting of the row of numbers assigned to it by merging the
 * partial results. This way you will get a 'tree' of active threads. The threads that do not outsource their
 * work (the leaves of the thread tree) sort the numbers array on the basis of bubble sort, while the thread
 * that does outsource work to 2 sub threads determines the sorting of the total on the basis of the partial
 * sortings.
 * <p>
 * Experiment here with the 'too large' threshold and again print out the time needed to determine the
 * sortings. Show your results in a graph.
 * <p>
 * Created by Derwin on 10-Sep-16.
 */
@SuppressWarnings("Convert2Lambda")
public class Main {
//    private static final int AMOUNT = 400000;
//    private static final int THRESHOLD = 1000;


    ///////////////////////////////////////////////////////////////////////////
    // Results
    ///////////////////////////////////////////////////////////////////////////
    //25 000 =
    //50 000 =
    //100 000 =

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private static int threshold;
    private int[] sorted;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        for (int amount = 200000; amount <= 800000; amount *= 2) {
            for (threshold = 500; threshold <= 8000; threshold += 500) {
                System.out.println("t:" + threshold);
                System.out.println("a:" + amount);

                int avg = 0;
                for (int i = 0; i < 100; i++) {
                    sorted = new int[amount];
                    Generator.generate(sorted);
                    long start = System.currentTimeMillis();
                    splitTask(sorted);
                    long end = System.currentTimeMillis();
                    long time = end - start;
                    avg += time;
                }
                avg /= 100;

                System.out.println("time: " + avg + " ms");
                System.out.println();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("===========");
        }
    }

    private static void splitTask(int[] array) {
        int length = array.length;
        if (length > threshold) {
            int middle = length / 2;
            int[] left = Arrays.copyOfRange(array, 0, middle);
            int[] right = Arrays.copyOfRange(array, middle, length);

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    splitTask(left);
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    splitTask(right);
                }
            });
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ignored) {
                //Ignored
            }

            MergeSort.merge(array, left, right);
        } else {
            MergeSort.sort(array);
        }
    }

}
