public interface IndexService<T extends Comparable<? super T>> {
    void initialize(T[] elements);
    boolean search(T key);
    void insert(T key);
    void delete(T key);
    int occurrences(T key);
    T[] getArray();

    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen al intervalo dado por
    // leftkey y rightkey. Si el mismo es abierto/cerrado depende de las variables leftIncluded
    // y rightIncluded. True indica que es cerrado. Si no hay matching devuelve arreglo de length 0
    T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded);

    // imprime el contenido del índice ordenado por su key.
    void sortedPrint();
    // devuelve el máximo elemento del índice. Lanza RuntimeException si no hay elementos
    T getMax();
    // devuelve el mínimo elemento del índice. Lanza RuntimeException si no hay elementos
    T getMin();
}
