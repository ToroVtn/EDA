import java.util.Iterator;
import java.util.NoSuchElementException;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{
    private int size =0;
    private T max;
    private Node root;

    // insert resuelto en la clase SortedLinkedList, iterativo
    public boolean insert1(T data) {

        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev= null;
        Node current = root;

        while (current!=null && current.data.compareTo(data) < 0) {
            // avanzo
            prev= current;
            current= current.next;
        }

        // repetido?
        if (current!=null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed. %s repeated", data));
            return false;
        }

        Node aux= new Node(data, current);
        // es el lugar para colocarlo
        if (current == root) {
            // el primero es un caso especial: cambia root
            root= aux;
        }
        else {
            // nodo interno
            prev.next= aux;
        }

        return true;
    }


    // insert resuelto en la clase SortedLinkedList, recursivo
    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta = new boolean[1];
        root= insertRec(data, root, rta);
        return rta[0];
    }


    public Node insertRec(T data, Node current, boolean[] rta) {
        if(current!=null && data.compareTo(current.data)==0) {
            System.out.printf("Insertion failed. %s repeated%n", data);
            rta[0] = false;
            return current;
        }

        if(current!=null && data.compareTo(current.data) < 0){
            current.next = insertRec(data, current.next, rta);
            return current;
        }

        rta[0] = true;
        return new Node(data, current);
    }

    // insert resuelto delegando al nodo todo
    public boolean insert(T data) {
        if(data == null) throw new IllegalArgumentException("data cannot be null");
        if(root == null){
            root = new Node(data, null);
            max = data;
            size++;
            return true;
        }
        boolean[] rta = new boolean[1];
        root = root.insert(data, rta);
        if(rta[0])  size++;
        return rta[0];
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }


    // delete resuelto todo en la clase SortedLinkedList, iterativo
    @Override
    public boolean remove(T data) {
        if(data == null) throw new IllegalArgumentException();

        Node current = root;
        Node prev = root;
        while (current != null && current.data.compareTo(data) < 0) {
            prev = current;
            current = current.next;
        }

        if(current!=null && current.data.compareTo(data) == 0){
            if(current==root){
                root=root.next;
            } else {
                prev.next = current.next;
                if (data.compareTo(max) == 0) {
                    max = prev.data;
                }
                size--;
                return true;
            }
        }
        return false;
    }


    // delete resuelto todo en la clase SortedLinkedList, recursivo
//	@Override
    public boolean remove2(T data) {
        // completar
        return true;

    }


    public Node removeRec(T data, Node current, boolean[] rta) {

        // completar
        return null;
    }


    // delete resuelto delegando al nodo
//	@Override
    public boolean remove3(T data) {
        // completar
        return true;
    }



    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
//        int rta= 0;
//
//        Node current = root;
//
//        while (current!=null ) {
//            // avanzo
//            rta++;
//            current= current.next;
//        }
//        return rta;
        return size;
    }


    @Override
    public void dump() {
        Node current = root;

        while (current!=null ) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == null || !  (other instanceof SortedLinkedList) )
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;

        Node current = root;
        Node currentOther= auxi.root;
        while (current!=null && currentOther != null ) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current= current.next;
            currentOther= currentOther.next;
        }

        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = root;
        int pos= 0;

        while (current!=null ) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current= current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public T getMin() {
        if (root == null)
            return null;

        return root.data;
    }


    @Override
    public T getMax() {
//
//        if (root == null)
//            return null;
//
//        Node current = root;
//
//
//        while (current.next !=null ) {
//            // avanzo
//            current= current.next;
//        }
//
//        return current.data;
        return max;
    }

    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node current = root;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                if(!hasNext()) throw new NoSuchElementException();
                T aux = current.data;
                current = current.next;
                return aux;
            }
        };
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data= data;
            this.next= next;
        }

        public Node insert(T data, boolean[] rta) {
            if(this.data.compareTo(data)==0){
                System.out.printf("Insertion failed. %s repeated%n", data);
                rta[0] = false;
                return this;
            }
            if(this.data.compareTo(data) < 0){
                if(next == null){
                    max = data;
                    rta[0] = true;
                    next = new Node(data, null);
                    return this;
                }
                next = next.insert(data, rta);
                return this;
            }
            rta[0] = true;
            return new Node(data, this);
        }
    }

    public IteratorWithOp<T> iterWithOp(){
        return new IteratorWithOp<T>() {
            Node current = root;
            Node prev, prev2;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {//1 3 5
                prev2 = prev; //reference to change the next for removal
                prev = current; //element to erase
                current = current.next;
                return prev.data;
            }// p2 = 0 ; p = 1 ; c = 3

            public void remove(){
                if(prev==null) throw new IllegalStateException("must call next() before remove");
                if(prev==root){
                    root = current;
                    return;
                }
                prev2.next = current;
            }

            public void insert(T data) {
                if(data == null)
                    throw new IllegalArgumentException("invalid data for insertion at this position");

                Node aux= new Node(data, current);
                if(current==root){
                    if(root.data.compareTo(data) <= 0) throw new IllegalArgumentException("invalid data for insertion at this position");
                    root = current = aux;
                    return;
                }
                if(current==null){
                    if(prev.data.compareTo(data) >= 0) throw new IllegalArgumentException("invalid data for insertion at this position");
                    prev.next = current = aux;
                    return;
                }
                prev.next = current = aux;
            }
        };
    }



    public static void main(String[] args) {
        SortedLinkedList<String> l = new SortedLinkedList<>();

        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
        System.out.println(l.size() );
        System.out.println(l.getMin() );
        System.out.println(l.getMax() );
        System.out.println();

        System.out.println(l.insert("hola"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();

        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
        System.out.println();

        System.out.println(l.insert("tal"));
        System.out.println("size " + l.size());
        l.dump();
        System.out.println();

        System.out.println(l.insert("ah"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();

        System.out.println(l.insert("veo"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();

        System.out.println(l.insert("bio"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();

        System.out.println(l.insert("tito"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();


        System.out.println(l.insert("hola"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();


        System.out.println(l.insert("aca"));
        System.out.println("size " +l.size());
        l.dump();
        System.out.println();

        System.out.println(l.size() );
        System.out.println(l.getMin() );
        System.out.println(l.getMax() );
        System.out.println();

        IteratorWithOp<String> iter = l.iterWithOp();
        iter.insert("a");
        l.dump();
    }


}