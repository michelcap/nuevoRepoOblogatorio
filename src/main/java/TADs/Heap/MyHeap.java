package TADs.Heap;

import TADs.Heap.*;

public interface MyHeap<K,T> {
    void insert(K key, T data) throws Exception;

    NodoTreeBin<K,T> delete();

    int size();

    String toString();
}
