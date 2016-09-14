package nl.saxion.assignment_1;

import java.util.Random;

/**
 * Created by Derwin on 09-Sep-16.
 */
public class Generator {

    private static Random random = new Random();

    public static void generate(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000) + 1;
        }
    }


}
