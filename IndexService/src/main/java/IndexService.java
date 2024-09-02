public interface IndexService {
    void initialize(int[] elements);
    boolean search(int key);
    void insert(int key);
    void delete(int key);
    int occurrences(int key);
}
