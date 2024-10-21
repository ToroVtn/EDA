import controller.BSTreeInterface;
import controller.NodeTreeInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private NodeTreeInterface<T> root;
    private int height = 0;

    enum Traversal {BYLEVELS, INORDER, PREORDER, POSTORDER}

    private Traversal traversal = Traversal.INORDER;

    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public void insert(T data) {
        if(root == null) {
            root = new Node<>(data);
            return;
        }

        int h = 0;
        NodeTreeInterface<T> aux = root;
        NodeTreeInterface<T> prev = root;

        while(aux!=null){
            if(data.compareTo(aux.getData())<=0){
                prev = aux;
                aux = aux.getLeft();
                h++;
            } else {
                prev = aux;
                aux = aux.getRight();
                h++;
            }
        }

        if(data.compareTo(prev.getData())<=0){
            prev.setLeft(new Node<>(data));
        } else {
            prev.setRight(new Node<>(data));
        }
        if(h>height) height = h;
    }

    @Override
    public Iterator<T> iterator() {
        switch(traversal){
            case BYLEVELS: return new BSTByLevelIterator();
            case INORDER: return new BSTInOrderIterator();
            case PREORDER: return new BSTPreOrderIterator();
            case POSTORDER: return new BSTPostOrderIterator();
        }
        throw new RuntimeException("Invalid traversal");
    }

    public void remove(T data) {
        if(data == null) throw new IllegalArgumentException("data is null");
        root = root.remove(data);
    }

    @Override
    public void postOrder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(NodeTreeInterface<T> node) {
        if (node == null) return;

        postorderRec(node.getLeft());
        postorderRec(node.getRight());

        System.out.print(node.getData() + " | ");
    }

    @Override
    public void preOrder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(NodeTreeInterface<T> node) {
        if (node == null) return;

        System.out.print(node.getData() + " | ");

        preorderRec(node.getLeft());
        preorderRec(node.getRight());
    }

    @Override
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(NodeTreeInterface<T> node) {
        if (node == null) return;

        inOrderRec(node.getLeft());
        System.out.print(node.getData() + " | ");
        inOrderRec(node.getRight());
    }

    private class BSTInOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();
        private NodeTreeInterface<T> current;
         public BSTInOrderIterator() {
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

    private class BSTPreOrderIterator implements Iterator<T> {
        private NodeTreeInterface<T> current;
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();

        public BSTPreOrderIterator() {
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

    private class BSTPostOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack = new Stack<>();
        private NodeTreeInterface<T> current;

        public BSTPostOrderIterator() {

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

    private class BSTByLevelIterator implements Iterator<T> {
        private Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        public BSTByLevelIterator() {
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
}
