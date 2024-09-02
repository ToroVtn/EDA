public class ArraysUtilities {
    public static void main(String[] args) {
        int[] unsorted = new int[] {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40};
        mergeSort( unsorted); ;

        for (int i : unsorted) {
            System.out.print(i + " ");
        }
    }

    public static void mergeSort(int[] unsorted) {
        mergeSort(unsorted, 0, unsorted.length-1);
    }

    public static void mergeSort(int[] unsorted, int l, int r) {
        if (l>=r) return;

        int m = (l + r) / 2;
        mergeSort(unsorted, l, m);
        mergeSort(unsorted, m+1, r);

        merge(unsorted, l, m, r);
    }

    private static void merge(int[] unsorted, int l, int m, int r) {
        int n1 = m-l+1;
        int n2 = r-m;
        int[] left = new int[n1];
        int[] right = new int[n2];
        for(int i = 0; i < m-l+1; i++) {
            left[i] = unsorted[i+l];
        }
        for(int i = 0; i < r-m; i++) {
            right[i] = unsorted[i+m+1];
        }

        int i = 0, j = 0, k=l;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                unsorted[k++] = left[i++];
            } else
                unsorted[k++] = right[j++];
        }

        while (i < n1)  unsorted[k++] = left[i++];
        while (j < n2)  unsorted[k++] = right[j++];
    }

    //34 10 8 60 21 17 28 30 2 70 50 15 62 40
    //   8 10   34
    //  10 34    8 60
    //
    public static void quicksort(int[] unsorted) {
        quicksort (unsorted, unsorted.length-1);
    }

    public static void quicksort(int[] unsorted, int cantElements) {
        quicksortHelper (unsorted, 0, cantElements);
    }

    private static void quicksortHelper (int[] unsorted, int leftPos, int rightPos) {
        if (rightPos <= leftPos )
            return;

        // tomamos como pivot el primero. Podria ser otro elemento
        int pivotValue= unsorted[leftPos];

        // excluimos el pivot del cjto.
        swap(unsorted, leftPos, rightPos);

        // particionar el cjto sin el pivot
        int pivotPosCalculated= partition(unsorted, leftPos, rightPos-1, pivotValue);


        // el pivot en el lugar correcto
        swap(unsorted, pivotPosCalculated, rightPos);


        // salvo unsorted[middle] todo puede estar mal
        // pero cada particion es autonoma
        quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
        quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos );

    }

    static private int partition(int[] unsorted, int leftPos, int rightPos, int pivotValue) {
        while(leftPos <= rightPos) {
            while (leftPos <= rightPos && unsorted[leftPos] <= pivotValue) {
                leftPos++;
            }
            while (leftPos <= rightPos && unsorted[rightPos] >= pivotValue) {
                rightPos--;
            }
            if (leftPos <= rightPos) {
                swap(unsorted, leftPos, rightPos);
            }
        }
        return leftPos;
    }

    // 3 2 10 1 40 60 70 80 /15

    static private void swap(int[] unsorted, int pos1, int pos2) {
        int auxi= unsorted[pos1];
        unsorted[pos1]= unsorted[pos2];
        unsorted[pos2]= auxi;
    }



}