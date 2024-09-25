import java.util.Iterator;

public interface IteratorWithOp<T> extends Iterator<T> {
    void insert(T data);
}
