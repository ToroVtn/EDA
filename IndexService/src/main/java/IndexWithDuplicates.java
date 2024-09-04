import java.util.Arrays;
import java.util.NoSuchElementException;

public class IndexWithDuplicates implements IndexService{
    private int CHUNK_SIZE = 10;
    private int[] arr = new int[CHUNK_SIZE];
    private int dim=0;
    @Override
    public void initialize(int[] elements) {
        if(elements == null) throw new IllegalArgumentException("elements cannot be null");

        for(int elem : elements){
            insert(elem);
        }
    }

    @Override
    public boolean search(int key) {
        return arr[getClosestPosition(key)] == key;
    }

    public int[] getArray(){
        return Arrays.copyOf(arr, dim);
    }

    @Override
    public void insert(int key) {
        if(arr.length == dim) Arrays.copyOf(arr, dim + CHUNK_SIZE);
        int index = getClosestPosition(key);
        int i = Math.max(dim - 1, 0);
        while (i >= index && i>=0){
            arr[i+1] = arr[i];
            i--;
        }
        dim++;
        arr[index] = key;
    }

    @Override
    public void delete(int key) {
        int index = getClosestPosition(key);
        if(arr[index] != key) return;
        while(index != dim-1){
            arr[index] = arr[index+1];
            index++;
        }
        dim--;
    }

    @Override
    public int occurrences(int key) {
        int sum = 0;
        int i = getClosestPosition(key);
        while (arr[i] == key && i >= 0){
            sum++;
            i--;
        }
        return sum;
    }

    //returns the last appearance, or if absent, the first higher number (its a binary search)
    private int getClosestPosition(int key){
        if(dim == 0) return 0;
        int low = 0;
        int high = dim-1;
        int mid;
        while(low <= high){
            mid = (low + high)/2;
            if (key == arr[mid]){
                while (mid<dim && arr[mid] == key){
                    mid++;
                }
                return mid;
            }
            else if (key < arr[mid]) // x is on the right side
                high = mid - 1;
            else                       // x is on the left side
                low = mid + 1;
        }
        return low;
    }

    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded){
        if(leftKey>rightKey) return new int[0];

        IndexService result = new IndexWithDuplicates();
        int leftIndex = getClosestPosition(leftKey);
        if(leftIncluded) {
            leftIndex--;
            while(leftIndex >= 0 && arr[leftIndex]==leftKey){
                leftIndex--;
            }
            leftIndex++;
        }
        int i;
        for (i = leftIndex; arr[i] < rightKey; i++) {
            result.insert(arr[i]);
        }
        if(rightIncluded) {
            for (i = i; arr[i] <= rightKey; i++) {
                result.insert(arr[i]);
            }
        }
        return result.getArray();
    }

    public void sortedPrint(){
        for (int i = 0; i < dim; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public int getMax(){
        if(dim == 0) throw new NoSuchElementException("Array is empty");
        return arr[dim-1];
    }

    public int getMin(){
        if(dim == 0) throw new NoSuchElementException("Array is empty");
        return arr[0];
    }
}