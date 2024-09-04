public interface IndexService {
    void initialize(int[] elements);
    boolean search(int key);
    void insert(int key);
    void delete(int key);
    int occurrences(int key);
    int[] getArray();
    int getDim();

    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen al intervalo dado por
    // leftkey y rightkey. Si el mismo es abierto/cerrado depende de las variables leftIncluded
    // y rightIncluded. True indica que es cerrado. Si no hay matching devuelve arreglo de length 0
    int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded);

    // imprime el contenido del índice ordenado por su key.
    void sortedPrint();
    // devuelve el máximo elemento del índice. Lanza RuntimeException si no hay elementos
    int getMax();
    // devuelve el mínimo elemento del índice. Lanza RuntimeException si no hay elementos
    int getMin();
}
