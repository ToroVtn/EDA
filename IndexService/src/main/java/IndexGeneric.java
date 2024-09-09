import java.lang.reflect.Array;
import java.util.Arrays;

public class IndexGeneric<T extends Comparable<? super T>> implements IndexService<T>{
    private T[] arr;
    private int dim=0;
    private int CHUNK_SIZE = 10;

    @SuppressWarnings("unchecked")
    public IndexGeneric(Class<T>clazz){
        if(dim != 0) return;
        arr = (T[]) Array.newInstance(clazz, CHUNK_SIZE);
    }

    @SuppressWarnings("unchecked")
    public void initialize(T[] elements) {
        Class<T> clazz = (Class<T>) elements[0].getClass();
        arr = (T[]) Array.newInstance(clazz, elements.length);
        for(T element : elements){
            insert(element);
        }
    }

    @Override
    public void insert(T key) {
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
    public boolean search(T key) {
        return arr[getClosestPosition(key)].compareTo(key) == 0;
    }

    @Override
    public T[] getArray() {
        return Arrays.copyOf(arr, dim);
    }

    private int getClosestPosition(T key){
        if(dim == 0) return 0;
        int low = 0;
        int high = dim-1;
        int mid;
        while(low <= high){
            mid = (low + high)/2;
            if (key.compareTo(arr[mid]) == 0){
                while (mid<dim && key.compareTo(arr[mid]) == 0){
                    mid++;
                }
                return mid;
            }
            else if (key.compareTo(arr[mid]) < 0) // x is on the right side
                high = mid - 1;
            else                       // x is on the left side
                low = mid + 1;
        }
        return low;
    }

    @Override
    public void delete(T key) {
        int index = getClosestPosition(key);
        if(key.compareTo(arr[index]) != 0) return;
        while(index != dim-1){
            arr[index] = arr[index+1];
            index++;
        }
        dim--;
    }

    @Override
    public int occurrences(T key) {
        int sum = 0;
        int i = getClosestPosition(key);
        while (i >= 0 && arr[i] == key){
            sum++;
            i--;
        }
        return sum;
    }

    @Override
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        if(leftKey.compareTo(rightKey) > 0) throw new IllegalArgumentException("left must be greater than right");

        IndexService<T> result = new IndexGeneric<>(leftKey.getClass());
        int leftIndex = getClosestPosition(leftKey);
        if(leftIncluded) {
            leftIndex--;
            while(leftIndex >= 0 && arr[leftIndex].compareTo(leftKey) == 0){
                leftIndex--;
            }
            leftIndex++;
        }
        int i;
        for (i = leftIndex; arr[i].compareTo(rightKey) < 0; i++) {
            result.insert(arr[i]);
        }
        if(rightIncluded) {
            for (i = i; arr[i].compareTo(rightKey) <= 0; i++) {
                result.insert(arr[i]);
            }
        }
        return result.getArray();
    }

    @Override
    public void sortedPrint() {
        for (int i = 0; i < dim; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    @Override
    public T getMax() {
        return arr[dim-1];
    }

    @Override
    public T getMin() {
        return arr[0];
    }
}
