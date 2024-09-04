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

    private int getClosestPosition(int key){
        if(dim == 0) return 0;
        int low = 0;
        int high = dim-1;
        int mid = (low + high)/2;
        while(low <= high){
            mid = (low + high)/2;
            if (key == arr[mid]){
                while (arr[mid] == key && mid<dim){
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

    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded){
        if(leftKey>rightKey) return new int[0];

        IndexService result = new IndexWithDuplicates();
        int leftIndex = getClosestPosition(leftKey);
        int rightIndex = getClosestPosition(rightKey);
        if(arr[leftIndex]==leftKey) {
            if(!leftIncluded){
                leftIndex++;
            }
            else {
                while(leftIndex>=0 && arr[leftIndex] == leftKey){
                    leftIndex--;
                }
                leftIndex++;
            }
        }
        if(arr[rightIndex]==rightKey) {
            if(!rightIncluded){
                while(arr[rightIndex] == rightKey){
                    rightIndex--;
                }
            }
        }
        for(int i=leftIndex; i<=rightIndex; i++){
            result.insert(arr[i]);
        }
        result.sortedPrint();
        return result.getArray();
    }

    public int getDim(){
        return dim;
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