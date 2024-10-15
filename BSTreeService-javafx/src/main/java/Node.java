import controller.NodeTreeInterface;

public class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
    private T data;
    private Node<T> left;
    private Node<T> right;

    public Node(T data) {
        this.data = data;
    }

    @Override
    public NodeTreeInterface<T> getLeft() {
        return left;
    }

    @Override
    public NodeTreeInterface<T> getRight() {
        return right;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setLeft(Node node) {
        this.left = node;
    }

    public void setRight(Node node) {
        this.right = node;
    }
}
