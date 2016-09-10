package nl.saxion;

import java.util.Arrays;

/**
 * Container for bubble sort.
 * <p>
 * Created by Derwin on 10-Sep-16.
 */
public class MergeSort {

    /**
     * Sort using the bubble sort algorithm.
     *
     * @param array Apply the sorting on this array.
     */
    public static void sort(int[] array) {
        int length = array.length; //Length of the array
        if (length >= 2) {
            int middle = length / 2; //Middle point of the length
            int[] left = Arrays.copyOfRange(array, 0, middle);
            int[] right = Arrays.copyOfRange(array, middle, length);
            sort(left);
            sort(right);
            merge(array, left, right);
        }
    }

    private static int[] merge(int[] array, int[] left, int[] right) {
        int leftLength = left.length;
        int leftCursor = 0; //Left index
        int rightLength = right.length;
        int rightCursor = 0; //Right index

        int arrayCursor = 0; //Merged index

        //Go through both left and right array
        while (leftCursor < leftLength && rightCursor < rightLength) {
            if (left[leftCursor] > right[rightCursor]) {
                array[arrayCursor++] = left[leftCursor++];
            } else {
                array[arrayCursor++] = right[rightCursor++];
            }
        }

        //If left has not been fully filled in merged
        while (leftCursor < leftLength) {
            array[arrayCursor++] = left[leftCursor++];
        }

        //If right has not been fully filled in merged
        while (rightCursor < rightLength) {
            array[arrayCursor++] = right[rightCursor++];
        }
        return array;
    }
}