package TADs.Stack;

public class NodeQ<T extends Comparable<T>> {
    public T data;
    public NodeQ<T> next;

    // Nuevo constructor
    public NodeQ(T data) {
        this.data = data;
    }
}
