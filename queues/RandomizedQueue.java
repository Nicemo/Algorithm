import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item>  {
    private int tail;
    private int size;
    private Item[] q;
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        tail = 0;
        size = 0;
    }
    private void resize(int n) {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < size; i++){
            copy[i] = q[i];
        }
        q = copy;
    }
    public boolean isEmpty() {
        return size == 0;
    }                 // is the randomized queue empty?
    public int size() {
        return size;
    }                        // return the number of items on the randomized queue
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (size == q.length) resize(2 * q.length);
        q[tail++] = item;
        size++;
    }           // add the item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(tail);
        Item item = q[index];

        q[index]    = q[--tail];
        q[tail]     = null;
        size--;
        if (size > 0 && size == q.length / 4)
            resize(q.length / 2);

        return item;
    }                   // remove and return a random item
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        return q[StdRandom.uniform(size)];
    }                     // return a random item (but do not remove it)
    public Iterator<Item> iterator() { return new ArrayIterator(); }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] array;

        public ArrayIterator() {
            array = (Item[]) new Object[size];
            for (int j = 0; j < size; j++)
                array[j] = q[j];
            for (int j = 0; j < size; j++) {
                int r = StdRandom.uniform(j+1);
                Item tmp = array[j];
                array[j] = array[r];
                array[r] = tmp;
            }
        }

        public boolean hasNext() { return i < size; }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (i >=  size)
                throw new java.util.NoSuchElementException();
            return array[i++];
        }
}
