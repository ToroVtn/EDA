import java.util.Arrays;

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

    @Override
    public void insert(int key) {
        if(arr.length == dim) Arrays.copyOf(arr, dim + CHUNK_SIZE);
        int index = getClosestPosition(key);
        int i = dim-1;
        while (i != index){
            arr[i+1] = arr[i];
            i--;
        }
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

    private int getClosestPosition(int key){
        int low = 0;
        int high = arr.length-1;
        int mid = (low + high)/2;
        while(low <= high){
            mid = (low + high)/2;
            if (key == arr[mid]){
                while (arr[mid] == key){
                    mid++;
                }
                return mid-1;
            }
            else if (key > arr[mid]) // x is on the right side
                low = mid + 1;
            else                       // x is on the left side
                high = mid - 1;
        }
        return mid;
    }
}