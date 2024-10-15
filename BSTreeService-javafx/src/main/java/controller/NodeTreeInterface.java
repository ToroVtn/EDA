package controller;

public interface NodeTreeInterface<T> {
    NodeTreeInterface<T> getLeft();

    NodeTreeInterface<T> getRight();

    T getData();
}
