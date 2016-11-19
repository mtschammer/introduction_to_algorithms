import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        this.first = null;
        this.last = null;
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            if (item == null) {
                throw new java.lang.NullPointerException();
            }

            this.item = item;
            this.next = null;
            this.previous = null;
        }
    }

    private void emptyCheck() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size < 1;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Node newNode = new Node(item);

        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            Node oldFirst = this.first;
            oldFirst.previous = newNode;
            newNode.next = oldFirst;
            this.first = newNode;
        }

        this.size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        Node newNode = new Node(item);

        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            Node oldLast = this.last;
            oldLast.next = newNode;
            newNode.previous = oldLast;
            this.last = newNode;
        }

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        this.emptyCheck();

        Item item = this.first.item;
        this.first = this.first.next;

        if (this.first != null) {
            this.first.previous = null;
        } else {
            this.last = null;
        }

        this.size--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        this.emptyCheck();
        Item item = this.last.item;
        this.last = this.last.previous;

        if (this.last != null) {
            this.last.next = null;
        } else {
            this.first = null;
        }

        this.size--;

        return item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return this.current != null;
        }

        public Item next() {
            if (this.hasNext()) {
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }

            throw new java.util.NoSuchElementException();
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
    }
}