package assignment_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Derwin on 02-Sep-16.
 */
public class Main {

    private List<Integer> array = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();

        //result
        //25000 = 1883 ms
        //50000 = 7638 ms
        //100000 = 30794 ms
    }

    private void run() {
        generateNumber(100000);
        for (Integer integer : array) {
            System.out.println(integer);
        }

        System.out.println("=======");

        long start = System.currentTimeMillis();
        sort(array);
        long end = System.currentTimeMillis();

        for (Integer integer : array) {
            System.out.println(integer);
        }

        long time = end - start;
        System.out.println("time: " + time + " ms");
    }

    private void generateNumber(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            array.add(random.nextInt(100) + 1);
        }
    }

    private void sort(List<Integer> array) {
        int length = array.size();

        do {
            for (int i = 0; i < length - 1; i++) {
                int left = array.get(i);
                int right = array.get(i + 1);

                if (left > right) {
                    array.set(i, right);
                    array.set(i + 1, left);
                }
            }
        } while ((length--) >= 0);
    }

}
