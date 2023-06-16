package TADs.Queue;

public interface MyQueue<T> {
    void enqueue(T element);
    T dequeue();
    boolean isEmpty();

    void imprimir();

    public void enqueueWithPriority(T element, int prioridad);

}

