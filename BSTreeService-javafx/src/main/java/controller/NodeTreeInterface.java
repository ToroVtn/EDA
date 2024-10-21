package controller;

public interface NodeTreeInterface<T> {
    NodeTreeInterface<T> getLeft();

    NodeTreeInterface<T> getRight();

    T getData();

    void setLeft(NodeTreeInterface<T> left);

    void setRight(NodeTreeInterface<T> right);

    void setData(T data);

    NodeTreeInterface<T> remove(T data);
}
