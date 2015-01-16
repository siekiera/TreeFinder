package pl.edu.pw.elka.treefinder.logic.algorithm.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Kopiec binarny
 * <p/>
 * Data utworzenia: 12.01.15 21:42
 *
 * @author Michał Toporowski
 */
public class Heap<T> {
    private final Comparator<T> comparator;
    private final Map<T, Integer> indexMap;
    private int capacity;
    private int size = 0;
    private T[] hTable;

    public Heap(int capacity, Comparator<T> comparator) {
        this.comparator = comparator;
        this.capacity = capacity;
        this.hTable = (T[]) new Object[capacity];
        this.indexMap = new HashMap<>();
    }

    /**
     * Wstawia obiekt do kopca
     *
     * @param object
     */
    public void insert(T object) {
        // sprawdzenie pojemności
        checkCapacity();
        //wstaw nowy obiekt jako ostatni lisc
        hTable[size++] = object;
        //przesuwaj go do gory, dopoki nierownosc kopca nie bedzie spełniona, albo dojdzie
        //do korzenia
        int index = moveUp(size - 1);
        indexMap.put(object, index);
    }

    /**
     * Pobiera element ze szczytu kopca i usuwa go
     *
     * @return element ze szczytu kopca
     */
    public T pop() {
        T result = null;
        if (size > 0) {
            result = hTable[0];
            hTable[0] = hTable[--size];
            heapify(0);
            indexMap.remove(result);
        }
        return result;
    }

    /**
     * Wykonuje operację decreaseKey (zmniejszenie wartości klucza)
     *
     * @param key klucz
     */
    public void decreaseKey(T key) {
        int oldIndex = indexMap.get(key);
        int newIndex = moveUp(oldIndex);
        indexMap.put(key, newIndex);
    }

    /**
     * Przesuwa element do góry dopoki nierownosc kopca nie bedzie spełniona, albo dojdzie
     * do korzenia
     *
     * @param index pierwotny indeks elementu
     * @return ostateczny indeks elementu
     */
    private int moveUp(int index) {
        //dla komorki o indeksie i jej rodzic to i/2 (dzielenie calkowite)
        //odnoszac sie do komorek tablicy trzeba odejmowac 1, poniewaz jest ona numerowana
        //od 0 do n-1, a nie 1 do n
        int i = index + 1;
        while (i > 1 && comparator.compare(hTable[i / 2 - 1], hTable[i - 1]) > 0) {
            swapArrayElems(i / 2 - 1, i - 1);
            i /= 2;
        }
        return i - 1;
    }

    /**
     * Weryfikuje, że kopiec zmieści jeszcze jeden węzeł i rozszerza go w razie potrzeby
     */
    private void checkCapacity() {
        if (capacity <= size) {
            capacity *= 2;
            T[] tmp = (T[]) new Object[capacity];
            System.arraycopy(hTable, 0, tmp, 0, hTable.length);
            hTable = tmp;
        }
    }

    /**
     * Operacja przesuwania elementów w dół kopca (po usunięciu)
     * @param node
     */
    private void heapify(int node) {
        int leftIdx = 2 * node;
        int rightIdx = 2 * node + 1;
        int largestIdx = node;
        if (leftIdx < size && comparator.compare(hTable[leftIdx], hTable[largestIdx]) < 0) {
            largestIdx = leftIdx;
        }
        if (rightIdx < size && comparator.compare(hTable[rightIdx], hTable[largestIdx]) < 0) {
            largestIdx = rightIdx;
        }
        if (largestIdx != node) {
            swapArrayElems(largestIdx, node);
            heapify(largestIdx);
        }
    }

    private void swapArrayElems(int i, int j) {
        T tmp = hTable[i];
        hTable[i] = hTable[j];
        hTable[j] = tmp;
    }
}
