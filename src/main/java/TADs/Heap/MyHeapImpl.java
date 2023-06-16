package TADs.Heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyHeapImpl<K, T> implements MyHeap<K, T> {
    private NodoTreeBin[] treeArray;
    private K key;
    private T data;
    private NodoTreeBin<K, T> parent;
    private NodoTreeBin<K, T> raiz;
    private int size = 0;
    private int cantidadNodos = 0;
    private String maxMin;

    public MyHeapImpl(String tipoHeap) {
        maxMin = tipoHeap;
        int capacidad = 10;
        cantidadNodos = (int) Math.pow(2, capacidad);
        int aux = cantidadNodos;
        while (aux > 1) {
            aux = aux / 2;
            cantidadNodos = cantidadNodos + aux;
        }
        treeArray = new NodoTreeBin[cantidadNodos];
    }

    public MyHeapImpl(String maxMinIn, int capacidad) throws Exception {
        maxMin = maxMinIn;
        if (capacidad < 0) {
            throw new Exception("Capacidad debe de ser mayor o igual a 0");
        }
        cantidadNodos = (int) Math.pow(2, capacidad);
        int aux = cantidadNodos;
        while (aux > 1) {
            aux = aux / 2;
            cantidadNodos = cantidadNodos + aux;
        }
        treeArray = new NodoTreeBin[cantidadNodos];
    }

    public NodoTreeBin<K, T> getParent() {
        return parent;
    }

    public void setParent(NodoTreeBin<K, T> parent) {
        this.parent = parent;
    }

    public NodoTreeBin<K, T> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoTreeBin<K, T> raiz) {
        this.raiz = raiz;
    }

    public int getSize() {
        return size;
    }

    public int getCantidadNodos() {
        return cantidadNodos;
    }

    public void insert(K key, T data) throws Exception {
        if (maxMin.equals("maximo") || maxMin.equals("minimo")) {
            if (getSize() >= treeArray.length) {
                expandirCapacidad();
            }

            treeArray[getSize()] = new NodoTreeBin<>(key, data);

            heapUp(getSize());
            //buildTree();
            setRaiz(treeArray[0]);
            size++;
        } else {
            throw new Exception("No se definio si quiere un HEAP Maximo o Minimo");
        }
    }

    public NodoTreeBin<K, T> delete() {
        if (size <= 0) {
            throw new IllegalStateException("Monticulo Vacio");
        }
        NodoTreeBin<K, T> nodo = treeArray[0];
        treeArray[0] = treeArray[size - 1];
        treeArray[size - 1] = null;
        size--;
        heapDown(0);
        //buildTree();
        setRaiz(treeArray[0]);
        return nodo;
    }

    public int size() {
        return getSize();
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    private void heapUp(int index) {
        int parentIndex = (index - 1) / 2;
        if (maxMin.equals("maximo")) {
            if (parentIndex >= 0 && (treeArray[index].compareTo(treeArray[parentIndex])) > 0) {
                swap(index, parentIndex);
                heapUp(parentIndex);
            }
        } else if (maxMin.equals("minimo")) {
            if (parentIndex >= 0 && (treeArray[index].compareTo(treeArray[parentIndex])) < 0) {
                swap(index, parentIndex);
                heapUp(parentIndex);
            }
        }
    }

    private void heapDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int largestIndex = index;

        if (maxMin.equals("maximo")) {
            if (leftChildIndex < size && treeArray[leftChildIndex].compareTo(treeArray[largestIndex]) > 0) {
                largestIndex = leftChildIndex;
            }

            if (rightChildIndex < size && treeArray[rightChildIndex].compareTo(treeArray[largestIndex]) > 0) {
                largestIndex = rightChildIndex;
            }
        } else if (maxMin.equals("minimo")) {
            if (leftChildIndex < size && treeArray[leftChildIndex].compareTo(treeArray[largestIndex]) < 0) {
                largestIndex = leftChildIndex;
            }

            if (rightChildIndex < size && treeArray[rightChildIndex].compareTo(treeArray[largestIndex]) < 0) {
                largestIndex = rightChildIndex;
            }
        }

        if (largestIndex != index) {
            swap(index, largestIndex);
            heapDown(largestIndex);
        }
    }

    private void swap(int index1, int index2) {
        NodoTreeBin<K, T> temp = treeArray[index1];
        treeArray[index1] = treeArray[index2];
        treeArray[index2] = temp;
    }

    private void buildTree() {
        int index = 0;
        while (treeArray[index] != null) {
            NodoTreeBin<K, T> nodo = treeArray[index];
            nodo.setLeftChild(treeArray[(2 * index) + 1]);
            nodo.setRightChild(treeArray[(2 * index) + 2]);
            index++;
        }
    }

    private void expandirCapacidad() {
        treeArray = Arrays.copyOf(treeArray, treeArray.length * 2);
    }

    public String toString() {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<NodoTreeBin<K,T>> level = new ArrayList<NodoTreeBin<K,T>>();
        List<NodoTreeBin<K,T>> next = new ArrayList<NodoTreeBin<K,T>>();

        level.add(getRaiz());
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (NodoTreeBin<K,T> n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    // Aca es para alterar y visualizar lo que segun guste
                    String aa = n.getKey().toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeftChild());
                    next.add(n.getRightChild());

                    if (n.getLeftChild() != null) nn++;
                    if (n.getRightChild() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<NodoTreeBin<K,T>> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
        return "HEAP "+ maxMin.toUpperCase();
    }
}
