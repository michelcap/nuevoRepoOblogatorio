package TADs;

public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private Object elements[];
    private int cursor = 0; // Índice de recorrido

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (E) elements[i];
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newSize = elements.length * 2;
            Object[] newElements = new Object[newSize];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    // Eliminamos el Iterator
    // Añadimos nuestros propios métodos para el recorrido
    public boolean hasNext() {
        return cursor < size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        if (cursor >= size) {
            throw new IllegalStateException("No hay más elementos");
        }
        return (E) elements[cursor++];
    }

    // Añadimos un método para reiniciar el recorrido
    public void resetCursor() {
        cursor = 0;
    }
}
