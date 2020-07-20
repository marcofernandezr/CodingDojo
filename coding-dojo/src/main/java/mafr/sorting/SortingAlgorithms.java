package mafr.sorting;

public class SortingAlgorithms {



    public static void quicksort(int [] arr) {
        quicksort(arr, 0, arr.length-1);
    }

    private static void quicksort(int[] arr, int left, int right) {
        if(left >= right) {
            return;
        }
        int pivot =  arr[(int) (left + (Math.random() * (right-left)))];

        int index = quicksort(arr, left, right, pivot);
        quicksort(arr, left, index-1);
        quicksort(arr, index, right);
    }

    private static int quicksort(int[] arr, int left, int right, int pivot) {
        while(left < right) {

            while(arr[left] < pivot) left++;
            while(arr[right] > pivot) right--;

            if(left<right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    private static void swap(int[] arr, int left, int right) {
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }


    public static void mergesort(int[] arr) {
        mergesort(arr, new int[arr.length],  0, arr.length-1);
    }

    private static void mergesort(int[] arr, int[] tmp, int left, int right) {
        if(left >= right) {
            return;
        }
        int center = (left +  right) / 2;
        mergesort(arr, tmp, left, center);
        mergesort(arr, tmp, center+1, right);
        merge(arr, tmp, left, right);
    }

    private static void merge(int[] arr, int[] tmp, int left, int right) {
        int leftIndex = left;
        int tmpIndex = left;
        int center = (left +  right) / 2;
        int rightIndex = center+1;

        while(leftIndex <= center && rightIndex <= right) {
            if(arr[leftIndex] < arr[rightIndex]) {
                tmp[tmpIndex] = arr[leftIndex];
                leftIndex++;
            } else {
                tmp[tmpIndex] = arr[rightIndex];
                rightIndex++;
            }
            tmpIndex++;
        }
        System.arraycopy(arr, leftIndex, tmp,tmpIndex, center - leftIndex + 1);
        System.arraycopy(arr, rightIndex, tmp,tmpIndex, right - rightIndex + 1);
        System.arraycopy(tmp, left, arr, left, right-left+1);
    }
}
