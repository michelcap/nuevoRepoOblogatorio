package TADs.BST;

public class NodeBST<K, T> {
    K key;
    T data;
    NodeBST<K, T> leftChild;
    NodeBST<K, T> rightChild;

    NodeBST(K key, T data) {
        this.key = key;
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }
}
