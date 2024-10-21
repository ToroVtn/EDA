import controller.BSTreeInterface;
import controller.NodeTreeInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


// acepta repetidos
public class AVL<T extends Comparable<? super T>> implements BSTreeInterface<T> {
    private Node root;

    public void printHierarchy() {
        printHierarchy("",  root);
    }

    public void printHierarchy(String initial, Node current) {

        if (current == null) {
            System.out.println(initial +  "\\___ " + "null");
            return;
        }

        System.out.println(initial + "\\___ " + current);

        if ( current.left != null || current.right != null) {
            printHierarchy(initial + "    " ,  current.left) ;
            printHierarchy(initial + "    " ,   current.right) ;

        }

    }

    private Node rightRotate(Node pivote) {

        Node newRoot = pivote.left;
        pivote.left= newRoot.right;
        newRoot.right = pivote;


        // Update heights
        updateHeights(pivote, newRoot);

        return newRoot;
    }

    private Node leftRotate(Node pivote) {

        Node newRoot = pivote.right;
        pivote.right= newRoot.left;
        newRoot.left = pivote;

        //  Update heights
        updateHeights(pivote, newRoot);

        return newRoot;
    }

    private void updateHeights(Node pivote, Node newRoot) {
        pivote.height = Math.max(pivote.left==null?-1:pivote.left.height, pivote.right==null?-1:pivote.right.height) + 1;
        newRoot.height = Math.max(newRoot.left==null?-1:newRoot.left.height, newRoot.right==null?-1:newRoot.right.height) + 1;
    }

    private int getBalance(Node currentNode) {
        if (currentNode == null)
            return 0;

        return (currentNode.left==null ? -1 : currentNode.left.height)-
                (currentNode.right==null ? -1 : currentNode.right.height);

    }

    @Override
    public void insert(T myData) {
        if (myData == null)
            throw new RuntimeException("element cannot be null");

        root= insert(root, myData);

    }

    private Node insert(Node currentNode, T myData) {
        if (currentNode == null)
            return new Node(myData);

        if (myData.compareTo(currentNode.data) <= 0)
            currentNode.left= insert(currentNode.left, myData);
        else
            currentNode.right= insert(currentNode.right, myData);

        // agregado para AVL
        int i = currentNode.left==null ? -1 : currentNode.left.height;
        int d = currentNode.right==null ? -1 : currentNode.right.height;
        currentNode.height = 1 + Math.max(i, d);

        int balance = getBalance(currentNode);

        // Op: Left
        if (balance > 1 && myData.compareTo(currentNode.left.data) <= 0)
            return rightRotate(currentNode);

        // Op: Right
        if (balance < -1 && myData.compareTo(currentNode.right.data) > 0)
            return leftRotate(currentNode);

        // Op: Left Right
        if (balance > 1 && myData.compareTo(currentNode.left.data) > 0) {
            currentNode.left = leftRotate(currentNode.left);
            return rightRotate(currentNode);
        }

        // Op: Right Left
        if (balance < -1 && myData.compareTo(currentNode.right.data) <= 0) {
            currentNode.right = rightRotate(currentNode.right);
            return leftRotate(currentNode);
        }

        return currentNode;
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        if (root == null)
            return -1;

        return root.height;
    }

    public boolean contains(T myData) {
        Node aux = root;
        while (aux != null) {
            if(myData.compareTo(aux.data) == 0) return true;
            if(myData.compareTo(aux.data) < 0) aux = aux.left;
            else aux = aux.right;
        }
        return false;
    }

    public T  getMax() {
        if(root == null) throw new RuntimeException("empty tree");
        Node aux = root;
        while (aux.right != null) {
            aux = aux.right;
        }
        return aux.data;
    }

    public T  getMin() {if(root == null) throw new RuntimeException("empty tree");
        Node aux = root;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux.data;
    }

    public void printByLevels() {
        if (root == null) {
            return;
        }

        // create an empty queue and enqueue the root node
        Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        queue.add(root);

        NodeTreeInterface<T >currentNode;

        // hay elementos?
        while (!queue.isEmpty())
        {
            currentNode = queue.remove();
            System.out.print(currentNode + " ");

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }

    }

    enum Traversal {BYLEVELS, INORDER, PREORDER, POSTORDER}

    private Traversal traversal = Traversal.BYLEVELS;

    public void setTraversal(Traversal traversal){
        this.traversal = traversal;
    }

    @Override
    public Iterator<T> iterator() {
        switch(traversal){
            case BYLEVELS: return new AVLByLevelIterator();
            case INORDER: return new AVLInOrderIterator();
            case PREORDER: return new AVLPreOrderIterator();
            case POSTORDER: return new AVLPostOrderIterator();
        }
        throw new RuntimeException("Invalid traversal");
    }

    private class AVLInOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();
        private NodeTreeInterface<T> current;
        public AVLInOrderIterator() {
            if(root != null) stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while(current!=null){
                stack.push(current);
                current = current.getLeft();
            }
            NodeTreeInterface<T> toReturn = stack.pop();
            current = toReturn.getRight();
            return toReturn.getData();
        }
    }

    private class AVLPreOrderIterator implements Iterator<T> {
        private NodeTreeInterface<T> current;
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();

        public AVLPreOrderIterator() {
            if(root != null) stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            if(current == null) current = stack.pop();

            if(current.getRight() != null) stack.push(current.getRight());

            NodeTreeInterface<T> toReturn = current;
            current = current.getLeft();
            return toReturn.getData();
        }
    }

    private class AVLPostOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();
        private NodeTreeInterface<T> current;

        public AVLPostOrderIterator() {

        }

        @Override
        public T next() {
            throw new RuntimeException("not implemented");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("not implemented");
        }
    }

    private class AVLByLevelIterator implements Iterator<T> {
        private Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        public AVLByLevelIterator() {
            if(root != null) queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            NodeTreeInterface<T> current = queue.remove();

            if(current.getLeft() != null) queue.add(current.getLeft());
            if(current.getRight() != null) queue.add(current.getRight());

            return current.getData();
        }
    }

    class Node implements NodeTreeInterface<T> {

        private T data;
        private Node left;
        private Node right;

        // para AVL
        private int height;

        @Override
        public String toString() {
            return data + " h=" + height;
        }

        public Node(T myData) {
            this.data= myData;

            this.height= 0;
        }

        public T getData() {
            return data;
        }

        @Override
        public void setLeft(NodeTreeInterface<T> left) {

        }

        @Override
        public void setRight(NodeTreeInterface<T> right) {

        }

        @Override
        public void setData(T data) {

        }

        @Override
        public NodeTreeInterface<T> remove(T data) {
            return null;
        }

        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        public NodeTreeInterface<T> getRight() {
            return right;
        }
    }

    public static void main(String[] args) {

        AVL<Integer>  myTree= new AVL<>();
        myTree.insert(1);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(2);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(4);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(7);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(15);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(3);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(10);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(17);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(19);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();

        myTree.insert(16);
        myTree.printHierarchy();
        System.out.println();
        System.out.println();
    }

    @Override
    public void remove(T i) {
        throw new RuntimeException("Not implemented in this AVL version");
    }

    @Override
    public void postOrder() {

    }

    @Override
    public void preOrder() {

    }

    @Override
    public void inOrder() {

    }
}
