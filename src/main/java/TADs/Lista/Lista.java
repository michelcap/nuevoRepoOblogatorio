package TADs.Lista;

public interface Lista<T> {
    void add(T value);
    void remove(int position) throws IndexOutOfBoundsException;
    T get(int position) throws IndexOutOfBoundsException;
    int find(T value);
    int size();
    void addFirst(T value);
    void addAt(T value, int position) throws IndexOutOfBoundsException;;
    void addInOrder(T value);
    void imprimir();

}
