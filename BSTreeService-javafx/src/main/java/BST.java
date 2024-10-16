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

    public void remove(T data) {
        root = remove(data, root);
    }

    private NodeTreeInterface<T> remove(T data, NodeTreeInterface<T> node) {
        if(node==null) return null;

        if(node.getData().compareTo(data)==0){
            if(node.getLeft()==null) return node.getRight();
            if(node.getRight()==null) return node.getLeft();
            node.setData(lexiadjacent(node).getData());
            return node;
        }

        if(data.compareTo(node.getData())<0) {
            node.setLeft(remove(data, node.getLeft()));
            return node;
        }

        node.setRight(remove(data, node.getRight()));
        return node;
    }

    private NodeTreeInterface<T> lexiadjacent(NodeTreeInterface<T> candidate){
        while(candidate.getLeft()!=null){
            if(candidate.getRight()==null) {
                NodeTreeInterface<T> temp = candidate;
                candidate = candidate.getLeft();
                temp.setLeft(null);

            }
            else candidate = candidate.getRight();
        }
        return candidate;
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
}
