package mafr.sorting;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SortingAlgorithmsTest {

    @Test
    public void shouldQuicksort() {
        int[] arr = new int[]{3, 4, 1, 5, 2};
        SortingAlgorithms.quicksort(arr);
        assertArrayEquals(new int[] {1,2,3,4,5}, arr);
    }

    @Test
    public void shouldMergesort() {
        int[] arr = new int[]{3, 4, 1, 5, 2};
        System.out.println(Arrays.toString(arr));
        assertArrayEquals(new int[] {1,2,3,4,5}, arr);
    }

}