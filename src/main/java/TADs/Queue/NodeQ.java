package TADs.Queue;

public class NodeQ<T extends Comparable<T>> {

    public T data;
    public NodeQ<T> next;

    public int priority;

    public NodeQ(T data, int priority) {
        this.data = data;
        this.priority = priority;
        this.next = null;
    }

}
