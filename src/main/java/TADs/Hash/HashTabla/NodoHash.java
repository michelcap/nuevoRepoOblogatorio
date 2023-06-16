package TADs.Hash.HashTabla;

public class NodoHash<K,V> {
    private K key;
    private V data;
    private boolean isDeleted;
    private int count = 0;

    public NodoHash(K key, V data) {
        this.key = key;
        this.data = data;
        this.isDeleted = false;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
