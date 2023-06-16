package TADs.Stack;

import java.util.EmptyStackException;

public class Stack<T extends Comparable<T>> implements MyStack<T>{

    public NodeQ<T> head;
    @Override
    public void pop() throws EmptyStackException {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        head = head.next;
    }

    @Override
    public T top() throws EmptyStackException {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return head.data;
    }


    @Override
    public void push(T element) {
        NodeQ<T> nuevoNodo = new NodeQ<>(element);

        nuevoNodo.next = head;
        head = nuevoNodo;
    }


    @Override
    public boolean isEmpty() {
        return (head == null);
    }

    @Override
    public void makeEmpty() {
        head = null;
    }


    @Override
    public void imprimir() {
        NodeQ<T> temporaryNode = head;

        while (temporaryNode.next != null){
            System.out.print(temporaryNode.data + " --> ");
            temporaryNode = temporaryNode.next;
        }
        System.out.println(temporaryNode.data);

    }

    // -----  ------  -----  ------  -----  ------  -----  ------  -----  ------  -----  ------
    //aca arranca el main

    public static void main(String[] args){
        Stack<Integer> miStack = new Stack<>();

        System.out.println("mi lista esta vacia? "+ miStack.isEmpty());
        System.out.println("el primer valor es " +miStack.top());

        System.out.println();

        miStack.push(2);
        miStack.push(3);
        miStack.push(34);
        System.out.println("mi lista esta vacia? "+ miStack.isEmpty());


        miStack.imprimir();
        System.out.println("el primer valor es " +miStack.top());

        miStack.pop();
        miStack.pop();

        miStack.imprimir();

        System.out.println();

        miStack.push(1);
        miStack.push(2);
        miStack.push(3);
        miStack.push(4);
        miStack.push(5);


        miStack.imprimir();
        miStack.makeEmpty();
        System.out.println( "mi lista esta vacia?" + miStack.isEmpty());


    }
}
