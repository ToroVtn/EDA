package controller;

public interface BSTreeInterface<T> {
    int getHeight();

    NodeTreeInterface<T> getRoot();

    void insert(T i);
    void remove(T i);
    void postOrder();
    void preOrder();
    void inOrder();
}
