package nl.saxion;

/**
 * Container for bubble sort.
 * <p>
 * Created by Derwin on 10-Sep-16.
 */
public class BubbleSort {

    /**
     * Sort using the bubble sort algorithm.
     *
     * @param array Apply the sorting on this array.
     */
    public static void sort(int[] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            for (int j = 0; j < array.length - i - 1; ++j) {
                if (array[j] < array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
}
