package TADs.BST;
import TADs.BST.LL.*;


public class TreeBST<K extends Comparable<K>, T> implements MyTreeBST<K, T> {
    private NodeBST<K, T> root;

    @Override
    public T find(K key) {
        return find(root, key);
    }


    private T find(NodeBST<K, T> node, K key) {

        if (node == null) return null;

        else if (node.key.equals(key)) return node.data;

        //si el nodo en el q estas parado es mayor a la key q queres buscar vas a la izq
        else if (node.key.compareTo(key) > 0) return find(node.leftChild, key);

        else return find(node.rightChild, key);

    }

    @Override
    public void insert(K key, T data) {
        root = insert(root, key, data);
    }

    private NodeBST<K, T> insert(NodeBST<K, T> node, K key, T data) {


        if (node == null) return new NodeBST<>(key, data);

        if (node.key.compareTo(key) > 0) node.leftChild = insert(node.leftChild, key, data);

        else if (node.key.compareTo(key) < 0) node.rightChild = insert(node.rightChild, key, data);

        else node.data = data;

        return node;
    }


    @Override
    public void delete(K key) {
        root = delete(root, key);
    }

    private NodeBST<K, T> delete(NodeBST<K, T> node, K key) {
        if (node == null) return null;


        if (node.key.compareTo(key) > 0) node.leftChild = delete(node.leftChild, key);

        else if (node.key.compareTo(key) < 0) node.rightChild = delete(node.rightChild, key);

        else {
            if (node.leftChild == null && node.rightChild == null) return null; // sin hijos
            else if (node.leftChild == null) return node.rightChild; // con hijo derecho
            else if (node.rightChild == null) return node.leftChild; // con hijo izq

            else { // tiene dos hijos, buscamos el nodo mas chico a la derecha y lo cambiamos
                NodeBST<K, T> masChicoDer = masChico(node.rightChild);
                node.data = masChicoDer.data;
                node.key = masChicoDer.key;
                node.rightChild = delete(node.rightChild, masChicoDer.key);
            }
        }
        return node;
    }

    // Encuentra el nodo con la key más chica en el subarbol proporcionado
    private NodeBST<K, T> masChico(NodeBST<K, T> root) {
        if (root.leftChild == null)
            return root;
        else {
            return masChico(root.leftChild);
        }
    }


    @Override
    public int size() {
        return size(root);
    }


    private int size(NodeBST<K, T> nodeBST){
        if (nodeBST == null) return 0;

        //sumo uno cada vez q pase por un nodo y aplico recursion
        return 1 + size(nodeBST.leftChild) + size(nodeBST.rightChild);
    }


    @Override
    public int countLeaf() {
        return countLeaf(root);
    }

    private int countLeaf(NodeBST<K, T> nodeBST){
        if (nodeBST == null) return 0;

        //si los hijos del nodo en el q estoy son nulos, sumo uno
        if (nodeBST.leftChild == null && nodeBST.rightChild == null) return 1;


        return countLeaf(nodeBST.leftChild) + countLeaf(nodeBST.rightChild);
    }

    @Override
    public int countCompleteElements() {
        return countCompleteElements(root);
    }

    private int countCompleteElements(NodeBST<K, T> nodeBST){
        if (nodeBST == null) return 0;

        if(nodeBST.leftChild == null || nodeBST.rightChild == null) return 0 ;

        return 1 + countCompleteElements(nodeBST.leftChild) + countCompleteElements(nodeBST.rightChild);
    }

    // -----  -----  -----  preOrder  -----  -----  -----  -----
    @Override
    public LL<K> preOrder() {
        return preOrder(root);
    }
    LL<K> arbolPreOrder = new LL<>();

    private LL<K> preOrder(NodeBST<K,T> nodeBST){
        if (nodeBST ==null) return null;

        arbolPreOrder.add(nodeBST.key);

        preOrder(nodeBST.leftChild);
        preOrder(nodeBST.rightChild);
        return arbolPreOrder;
    }

    // -----  -----  -----  inOrder  -----  -----  -----  -----

    @Override
    public LL<K> inOrder() {
        return inOrder(root);
    }
    LL<K> arbolInOrder = new LL<>();

    private LL<K> inOrder(NodeBST<K,T> nodeBST){
        if (nodeBST ==null) return null;


        inOrder(nodeBST.leftChild);

        arbolInOrder.add(nodeBST.key);

        inOrder(nodeBST.rightChild);
        return arbolInOrder;
    }

    // -----  -----  -----  postOrder  -----  -----  -----  -----


    @Override
    public LL<K> postOrder() {
        return postOrder(root);
    }

    LL<K> arbolPostOrder = new LL<>();

    private LL<K> postOrder(NodeBST<K,T> nodeBST){
        if (nodeBST ==null) return null;

        postOrder(nodeBST.leftChild);
        postOrder(nodeBST.rightChild);

        arbolPostOrder.add(nodeBST.key);


        return arbolPostOrder;
    }

}
