public class BoundedQueue<T> {
    T[] elements;
    int first, last, dim=0;

    @SuppressWarnings("unchecked")
    public BoundedQueue(int limit) {
        elements = (T[]) new Object[limit];
    }

    public boolean isEmpty() {
        return dim==0;
    }

    public boolean isFull() {
        return dim==elements.length;
    }

    public void enqueue(T element) {
        if(isFull()) return;
        dim++;
        last = (last == elements.length-1) ? 0 : last+1;
        elements[last] = element;
    }

    public T dequeue() {
        T toReturn = elements[first];
        dim--;
        first = (first == elements.length-1) ? 0 : first+1;
        return toReturn;
    }

    private void dump() {
        for(T element : elements) {
            element = null;
        }
    }
}
