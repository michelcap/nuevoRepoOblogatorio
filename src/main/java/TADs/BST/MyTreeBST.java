package TADs.BST;
import TADs.BST.LL.*;

public interface MyTreeBST<K extends Comparable<K>, T> {

    T find(K key);

    void insert(K key, T data);

    void delete(K key);
    int size();

    int countLeaf();

    int countCompleteElements();
    LL<K > preOrder();

    LL<K > inOrder();
    LL<K > postOrder();

}