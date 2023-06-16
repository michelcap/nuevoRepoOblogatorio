package TADs.BST.LL;

public class LL<T extends  Comparable<T>> implements Lista<T>{

    NodeLinkedList<T> head;


    @Override
    public void add(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        // si la lista está vacía
        if (head == null) {
            head = nuevoNodo;
        } else {
            // si la lista no está vacía
            // mientras que no esté parado en el último, sigo avanzando
            NodeLinkedList<T> tempNodeLinkedList = head;
            while (tempNodeLinkedList.next != null) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }
            // una vez ya estoy en el último, en el next pongo el que quiero poner
            tempNodeLinkedList.next = nuevoNodo;
        }
    }


    @Override
    public void remove(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("La posición no puede ser negativa.");
        }

        if (head == null) {
            throw new IllegalStateException("No se puede eliminar de una lista vacía.");
        }

        if (position == 0) {
            head = head.next;
            return;
        }

        NodeLinkedList<T> tempNodeLinkedList = head;
        for (int i = 0; i < position - 1; i++) {
            if (tempNodeLinkedList.next == null) {
                throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
            }
            tempNodeLinkedList = tempNodeLinkedList.next;
        }

        if (tempNodeLinkedList.next == null) {
            throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
        }

        tempNodeLinkedList.next = tempNodeLinkedList.next.next;
    }




    @Override
    public T get(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("La posición no puede ser negativa.");
        }

        if (head == null) {
            throw new IllegalStateException("No se puede obtener un elemento de una lista vacía.");
        }

        NodeLinkedList<T> tempNodeLinkedList = head;
        for (int i = 0; i < position; i++) {
            if (tempNodeLinkedList.next == null) {
                throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
            }
            tempNodeLinkedList = tempNodeLinkedList.next;
        }

        return tempNodeLinkedList.value;
    }

    public int size() {
        int size = 0;
        NodeLinkedList<T> current = head;

        while (current != null) {
            size++;
            current = current.next;
        }

        return size;
    }



    @Override
    public int find(T value){
        NodeLinkedList<T> tempNodeLinkedList = head;
        int i = 0;

        while (tempNodeLinkedList != null) {
            if (tempNodeLinkedList.value.equals(value)) {
                System.out.println("El valor " + value + " está en la posición " + i);
                return i;
            }

            tempNodeLinkedList = tempNodeLinkedList.next;
            i++;
        }

        System.out.println("El valor " + value + " no está! :(");
        return -1;
    }



    @Override
    public void addFirst(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        nuevoNodo.next = head;
        head = nuevoNodo;
    }

    @Override
    public void addAt(T value, int position) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException("Posición inválida: " + position);
        }

        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        if (position == 0) {
            addFirst(value);
        } else {
            NodeLinkedList<T> tempNodeLinkedList = head;
            for (int i = 0; i < position - 1; i++) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }
            nuevoNodo.next = tempNodeLinkedList.next;
            tempNodeLinkedList.next = nuevoNodo;
        }
    }



    @Override
    public void addInOrder(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        // Si la lista está vacía o el nuevo nodo debe ser el primero
        if (head == null || head.value.compareTo(nuevoNodo.value) > 0) {
            nuevoNodo.next = head;
            head = nuevoNodo;
        } else {
            NodeLinkedList<T> tempNodeLinkedList = head;

            // Recorrer la lista mientras el siguiente nodo no sea null y su valor sea menor que el valor a insertar
            while (tempNodeLinkedList.next != null && tempNodeLinkedList.next.value.compareTo(value) < 0) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }

            // Insertar el nuevo nodo después del nodo temporal
            nuevoNodo.next = tempNodeLinkedList.next;
            tempNodeLinkedList.next = nuevoNodo;
        }
    }


    @Override
    public void imprimir() {

        NodeLinkedList<T> temporaryNodeLinkedList = head;

        while (temporaryNodeLinkedList.next != null){
            System.out.print(temporaryNodeLinkedList.value + " --> ");
            temporaryNodeLinkedList = temporaryNodeLinkedList.next;
        }
        System.out.println(temporaryNodeLinkedList.value);
    }

    // -----  ------  -----  ------  -----  ------  -----  ------  -----  ------  -----  ------
    //aca arranca el main
    public static void main(String[] args) {
        LL<Integer> listita = new LL<>();



        //pruebo lo de añadir
        listita.add(1);
        listita.add(2);
        listita.add(3);
        listita.add(4);

        listita.imprimir();

        //pruebo el remove
        listita.remove(1);

        //pruebo el get
        System.out.println("aver si esto funciona");
        System.out.println(listita.get(2));

        //pruebo el addFirst
        listita.addFirst(7);
        System.out.println();

        //pruebo el addAt
        listita.addAt(0, 0);
        System.out.println();
        listita.imprimir();
        //pruebo el esta
        listita.find(0);
        listita.find(7);
        listita.find(4);
        listita.find(45);


        // -----  ------  -----  ------  -----  ------  -----  ------  -----  ------  -----  ------
        // voy a intentar lo de insertar ordenadamente


    }
}





