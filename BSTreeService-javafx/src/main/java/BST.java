import controller.BSTreeInterface;
import controller.NodeTreeInterface;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private NodeTreeInterface<T> root;
    private int height = 0;

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
            root = new Node(data);
            return;
        }

        int h = 0;
        NodeTreeInterface<T> aux = root;
        Node<T> prev = (Node<T>) root;

        while(aux!=null){
            if(data.compareTo(aux.getData())<=0){
                prev = (Node<T>) aux;
                aux = aux.getLeft();
                h++;
            } else {
                prev = (Node<T>) aux;
                aux = aux.getRight();
                h++;
            }
        }

        if(data.compareTo(prev.getData())<=0){
            prev.setLeft(new Node(data));
        } else {
            prev.setRight(new Node(data));
        }
        if(h>height) height = h;
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

        System.out.print(node.getData() + " ");
    }

    @Override
    public void preOrder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(NodeTreeInterface<T> node) {
        if (node == null) return;

        System.out.print(node.getData() + " ");

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
        System.out.print(node.getData() + " ");
        inOrderRec(node.getRight());
    }
}
