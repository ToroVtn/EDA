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

    @Override
    public NodeTreeInterface<T> remove(T data) {
        if(data.compareTo(this.data)<0) {
            if(left != null) {
                left = left.remove(data);
            }
            return this;
        }
        if(data.compareTo(this.data)>0) {
            if(right != null) {
                right = right.remove(data);
            }
            return this;
        }

        if(left==null) return right;
        if(right==null) return left;

        this.data = lexiAdjacent(right);
        right = right.remove(this.data);
        return this;
    }

    public T lexiAdjacent(NodeTreeInterface<T> node) {
        while(node.getLeft()!=null){
            node = node.getLeft();
        }
        return node.getData();
    }
}
