package TADs.Heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyHeapImplTest {

    @Test
    void insert() throws Exception {
        MyHeapImpl<Integer,String> monticulo = new MyHeapImpl<>("maximo");
        monticulo.insert(10, "a");
        monticulo.insert(4, "b");
        monticulo.insert(15, "c");
        monticulo.insert(5, "d");
        monticulo.insert(30, "e");
        monticulo.insert(6, "f");
        monticulo.insert(1, "g");
        monticulo.insert(3, "h");
        monticulo.insert(25, "i");
        monticulo.insert(12, "j");
        monticulo.insert(0, "k");
        monticulo.insert(20, "l");
        assertEquals(12,monticulo.size());
    }

    @Test
    void delete() throws Exception {
        MyHeapImpl<Integer,String> monticulo = new MyHeapImpl<>("maximo");
        monticulo.insert(10, "a");
        monticulo.insert(4, "b");
        monticulo.insert(15, "c");
        monticulo.insert(5, "d");
        monticulo.insert(30, "e");
        monticulo.insert(6, "f");
        monticulo.insert(1, "g");
        monticulo.insert(3, "h");
        monticulo.insert(25, "i");
        monticulo.insert(12, "j");
        monticulo.insert(0, "k");
        monticulo.insert(20, "l");
        assertEquals(12,monticulo.size());
        assertEquals("e",monticulo.delete().getData());
        assertEquals(11,monticulo.size());
    }

    @Test
    void size() {
    }

    @Test
    void isEmpty() throws Exception {
        MyHeapImpl<Integer,String> monticulo = new MyHeapImpl<>("maximo");
        monticulo.insert(10, "a");
        assertEquals("a",monticulo.delete().getData());
        assertTrue(monticulo.isEmpty());
    }

    @Test
    void testToString() throws Exception {
        MyHeapImpl<Integer,String> monticulo = new MyHeapImpl<>("minimo");
        monticulo.insert(10, "a");
        monticulo.insert(4, "b");
        monticulo.insert(15, "c");
        monticulo.insert(5, "d");
        monticulo.insert(30, "e");
        monticulo.insert(6, "f");
        monticulo.insert(1, "g");
        monticulo.insert(3, "h");
        monticulo.insert(25, "i");
        monticulo.insert(12, "j");
        monticulo.insert(0, "k");
        monticulo.insert(20, "l");
        System.out.println(monticulo);
    }

}