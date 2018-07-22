import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> Setinel;

    private void checkItem(Item one) {
        if (one == null) throw new IllegalArgumentException();
    }
    private class Node<Item> {
        private Item Item;
        private Node<Item> next;
        private Node<Item> last;
        public Node(Item item) {Item = item;}
    }
    public Deque(){
        Setinel = new Node<Item>(null);
        Setinel.next = Setinel;
        Setinel.last = Setinel;
        size = 0;

    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void addFirst(Item item) {
        checkItem(item);
        size++;
        Node<Item> one = new Node<>(item);
        one.next = Setinel.next;
        one.last = Setinel;
        Setinel.next = one;
        one.next.last = one;
        one = null;
    }
    public void addLast(Item item) {
        checkItem(item);
        size++;
        Node<Item> one = new Node<>(item);
        one.last = Setinel.last;
        one.next = Setinel;
        Setinel.last = one;
        one.last.next = one;
        one = null;
    }
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<Item> one = Setinel.next;
        Setinel.next = one.next;
        Setinel.next.last = Setinel;
        Item result = one.Item;
        one = null;
        size--;
        return result;
    }
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<Item> one = Setinel.last;
        Setinel.last = one.last;
        Setinel.last.next = Setinel;
        Item result = one.Item;
        one = null;
        size--;
        return result;
    }
    public Iterator<Item> iterator() {
        return new listIterator<Item>(Setinel);
    }

    private class listIterator<Item> implements Iterator<Item> {
        private Node<Item> curr;

        public listIterator(Node<Item> item) {
            curr = item;
        }

        @Override
        public boolean hasNext() {
            return curr.next != curr;
        }
        @Override
        public void remove()      { throw new UnsupportedOperationException();  }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<Item> one = curr.next;
            curr.next = one.next;
            curr.next.last = curr;
            Item result = one.Item;
            one = null;
            return result;
        }
    }
}
