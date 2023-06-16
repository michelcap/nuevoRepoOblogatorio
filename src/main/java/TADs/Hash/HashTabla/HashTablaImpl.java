package TADs.Hash.HashTabla;

public class HashTablaImpl<K, V> implements HashTabla<K, V> {
    private NodoHash<K, V>[] tabla;
    private K key;
    private V data;
    private int size = 0;
    private float loadFactor = 0;
    private int capacidad = 1;

    public HashTablaImpl() {
        capacidad = 47;
        tabla = new NodoHash[capacidad];
    }

    public HashTablaImpl(int cap) throws Exception {
        if (cap < 0) {
            throw new Exception("Capacidad debe de ser mayor o igual a 0");
        }
        capacidad = cap;
        tabla = new NodoHash[capacidad];
    }

    // Método para verificar si un número es primo
    private static boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }

        return true;
    }

    public NodoHash<K, V>[] getTabla() {
        return tabla;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public void put(K key, V data) throws Exception {
        loadFactor = (float) size / capacidad;
        if (loadFactor <= 0.7) {
            int index = getIndex(key);
            NodoHash<K, V> bucket = tabla[index];
            if (bucket == null) {
                bucket = new NodoHash<>(key, data);
                tabla[index] = bucket;
                size++;
            } else {
                index++;
                if (index > capacidad-1) {
                    index = 0;
                }
                bucket = tabla[index];
                while (bucket != null && !bucket.isDeleted()) {
                    index++;
                    if (index < capacidad) {
                        bucket = tabla[index];
                    } else {
                        index = 0;
                    }
                }
                bucket = new NodoHash<>(key, data);
                tabla[index] = bucket;
                size++;
            }
        } else {
            //System.out.println("Se redimencionara HashTable, capacidad actual: " + capacidad);
            expandirCapacidad(key,data);
        }
    }

    @Override
    public boolean contains(K key) {
        boolean retorno = false;
        int index = getIndex(key);
        NodoHash<K,V> bucket = tabla[index];
        NodoHash<K, V> nulo = new NodoHash<>(null, null);
        if (bucket != null) {
            while (bucket != null && !bucket.getKey().equals(key)) {
                index++;
                if (index < capacidad) {
                    bucket = tabla[index];
                } else {
                    index = 0;
                }
            }
            if (bucket != null && bucket.getKey().equals(key) && !bucket.isDeleted()) {
                retorno = true;
            }
        }
        return retorno;
    }

    @Override
    public void remove(K key) {
        boolean retorno = false;
        int index = getIndex(key);
        NodoHash<K,V> bucket = tabla[index];
        while (!bucket.getKey().equals(key)) {
            index++;
            bucket = tabla[index];
        }
        if (bucket.getKey().equals(key)) {
            bucket.setDeleted(true);
        }
    }

    @Override
    public NodoHash<K,V> get(K key) {
        NodoHash<K,V> retorno = null;
        int index = getIndex(key);
        NodoHash<K,V> bucket = tabla[index];
        while (bucket != null && !bucket.getKey().equals(key) && !bucket.isDeleted()) {
            index++;
            if (index < capacidad) {
                bucket = tabla[index];
            } else {
                index = 0;
            }
        }
        if (bucket != null && bucket.getKey().equals(key)) {
            retorno = bucket;
        }
        return retorno;
    }

    @Override
    public void upDate(K key, V data) {
        int index = getIndex(key);
        NodoHash<K,V> bucket = tabla[index];
        if (bucket != null && !bucket.getKey().equals(key) && !bucket.isDeleted()) {
            bucket = new NodoHash<>(key, data);
            tabla[index] = bucket;
        }
    }



    private int getIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % tabla.length;
    }

    private void expandirCapacidad(K newKey, V newData) throws Exception {
        int largoActual = tabla.length+1;
        int largoFuturo = largoActual * 2;
        NodoHash<K, V>[] auxTabla = tabla;
        while (!esPrimo(largoFuturo)) {
            largoFuturo++; // Incrementar hasta encontrar un número primo
        }
        NodoHash<K, V>[] nuevaTabla = new NodoHash[largoFuturo];;
        capacidad = largoFuturo;
        size = 0;
        for (int i = 0; i < largoActual-1; i++) {
            if (auxTabla[i]!= null) {
                K key = auxTabla[i].getKey();
                V data = auxTabla[i].getData();
                int index = getIndex(key);
                NodoHash<K, V> bucket = nuevaTabla[index];
                if (bucket == null) {
                    bucket = new NodoHash<>(key, data);
                    nuevaTabla[index] = bucket;
                    size++;
                } else {
                    index++;
                    if (index > capacidad-1) {
                        index = 0;
                    }
                    bucket = nuevaTabla[index];
                    while (bucket != null && !bucket.isDeleted()) {
                        index++;
                        if (index > capacidad-1) {
                            index = 0;
                        }
                        bucket = nuevaTabla[index];
                    }
                    bucket = new NodoHash<>(key, data);
                    nuevaTabla[index] = bucket;
                    size++;
                }
            }
        }
        tabla = nuevaTabla;
        loadFactor = (float)size / capacidad;
        if (loadFactor <= 0.7) {
            int index = getIndex(newKey);
            NodoHash<K, V> bucket = tabla[index];
            if (bucket == null) {
                bucket = new NodoHash<>(newKey, newData);
                tabla[index] = bucket;
                size++;
            } else {
                index++;
                if (index > capacidad-1) {
                    index = 0;
                }
                bucket = tabla[index];
                while (bucket != null && !bucket.isDeleted()) {
                    if (index > capacidad-1) {
                        index = 0;
                    }
                    index++;
                    bucket = tabla[index];
                }
                bucket = new NodoHash<>(newKey, newData);
                tabla[index] = bucket;
                size++;
            }
        }
        //System.out.println("Proceso finalizado, nueva capacidad: " + capacidad);
    }
}




