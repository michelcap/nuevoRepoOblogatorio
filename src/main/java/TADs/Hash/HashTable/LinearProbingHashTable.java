package TADs.Hash.HashTable;

import Entidades.User;

import TADs.Hash.HashTable.Entry;
import TADs.MyArrayList;
import java.util.List;

public class LinearProbingHashTable<K, V> implements HashTable<K, V> {
    private Entry<K, V>[] table;
    private static final int START_CAPACITY = 10;
    private int size;
    private static final float LOAD_FACTOR = 0.75f; // que tan llena tiene estar la tabla para hacer el resize


    public LinearProbingHashTable() {
        table = new Entry[START_CAPACITY];
    }

    public MyArrayList<Entry<K, V>> getEntries() {
        MyArrayList<Entry<K, V>> entries = new MyArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }



    private int hash(K key) {
        String keyString;
        if (key instanceof User) {
            keyString = ((User) key).getName(); // Reemplaza 'getName()' con el método correcto si es necesario
        } else {
            keyString = key.toString();
        }

        int hashValue = 0;
        int prime = 7;

        for (int i = 0; i < keyString.length(); i++) {
            hashValue += keyString.charAt(i) * Math.pow(prime, i);
        }

        return Math.abs(hashValue % table.length);
    }

    public K getUser(K key) {
        int index = hash(key);

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                return table[index].key;
            }
            index++;
            if (index >= table.length) {
                index = 0;
            }
        }
        return null;  // Retorna null si el usuario no se encuentra.
    }






    @Override
    public void put(K key, V value) {
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }

        int index = hash(key);

        while (table[index] != null && !key.equals(table[index].key)) {
            index++;
            if (index >= table.length) index = 0;
        }

        if (table[index] == null) {
            size++;
        }

        table[index] = new Entry<>(key, value);
    }

    // estas funciones de abajo son para el resize
    private void resize() {
        int newSize = findNextPrime(table.length * 2);

        Entry<K, V>[] oldTable = table;
        table = new Entry[newSize];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    private int findNextPrime(int min) {
        int prime = min;

        while (!isPrime(prime)) {
            prime++;
        }

        return prime;
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;

        if (num % 2 == 0 || num % 3 == 0) return false;

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    // estas funciones de arriba son para el resize

    @Override
    public V get(K key) {
        int index = hash(key);

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                return table[index].value;
            }

            index++;
            if (index >= table.length) {
                index = 0;
            }
        }

        return null;  // Retorna null si la clave no se encuentra.
    }



//  ---------------- contains ---------------------------

    @Override
    public boolean contains(K key) {
        int index = hash(key);

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                return true;
            }

            index++;
            if (index >= table.length) {
                index = 0;
            }
        }

        return false;
    }

    // ----------- el remove -----------
    private static final Entry DELETED = new Entry(null, null);

    @Override
    public void remove(K key) {
        int index = hash(key);

        while (table[index] != null) {
            if (table[index].key != null && table[index].key.equals(key)) {
                table[index] = null;
                size--;
                break;
            }

            index++;
            if (index >= table.length) {
                index = 0;
            }
        }
    }



}

