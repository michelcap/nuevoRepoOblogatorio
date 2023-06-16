package TADs.Hash.HashTabla;

public interface HashTabla<K,V> {

    public void put(K key, V value) throws Exception;

    public NodoHash<K, V> get(K key);

    public boolean contains(K key);

    public void remove(K clave);

    public NodoHash<K, V>[] getTabla();

    public void upDate(K key, V data);
}
