package org.nekobehemoth.hw02.shifting;

import java.util.Arrays;

public class ArrayOperations {
    public static void shiftLeftSystemCopy(int[] array, int positions) {
        if (positions >= array.length) positions %= array.length;
        int[] temp = new int[array.length - 1 - positions];
        System.arraycopy(array, positions + 1, temp, 0, array.length - 1 - positions);
        System.arraycopy(array, 0, array, array.length - positions - 1, positions + 1 );
        System.arraycopy(temp, 0, array, 0, temp.length);
    }
    /** Shift array elements using manual for loop */
    public static void shiftLeftManualLoop(int[] array, int positions) {
        for (int i = positions; i > 0; i--) {
            int last = array[array.length - 1];
            for (int j = array.length - 1; j > 0; j-- ) {
                array[j] = array[j - 1];
            }
            array[0] = last;
        }
    }
}
