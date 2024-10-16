import controller.NodeTreeInterface;

public class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
    private T data;
    private NodeTreeInterface<T> left;
    private NodeTreeInterface<T> right;

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

    public void setLeft(NodeTreeInterface<T> node) {
        this.left = node;
    }

    public void setRight(NodeTreeInterface<T> node) {
        this.right = node;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }
}
