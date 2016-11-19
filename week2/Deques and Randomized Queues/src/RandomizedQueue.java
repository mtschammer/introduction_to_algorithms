import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int REMOVE = 0, ADD = 1;
    private static final double QUARTER_FULL = 0.25, FULL = 1.0;
    private Item[] queue;
    private int size = 0;


    // construct an empty randomized queue
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[1];
    }

    private void emptyCheck() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    private void nullCheck(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
    }

    private void copyQueue(int currentSize, int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];

        for (int i = 0; i < currentSize; i++) {
            newQueue[i] = this.queue[i];
            this.queue[i] = null;
        }

        this.queue = newQueue;
    }

    private void resizeQueue(int operation, int oldSize, int newSize) {
        double oldUtilizedRatio = (double) oldSize / this.queue.length;
        double newUtilizedRatio = (double) newSize / this.queue.length;

        if (operation == ADD && newUtilizedRatio == FULL) {
            this.copyQueue(oldSize + 1, this.queue.length * 2);
        } else if (operation == REMOVE && oldUtilizedRatio > QUARTER_FULL
                && newUtilizedRatio <= QUARTER_FULL) {

            // +1 to newSize to round up result if length is uneven number.
            this.copyQueue(oldSize - 1, (this.queue.length + 1) / 2);
        }
    }

    public boolean isEmpty() {
        return this.size < 1;
    }

    // return the number of items on the queue
    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        nullCheck(item);
        this.queue[this.size] = item;
        this.resizeQueue(ADD, this.size, this.size + 1);
        this.size++;
    }

//    public int length(){
//        return this.queue.length;
//    }

    // remove and return a random item
    public Item dequeue() {
        emptyCheck();

        Item item;
        if (this.size == 1) {
            item = this.queue[0];
            this.queue[0] = null;
        } else {
            int index = StdRandom.uniform(0, this.size);
            item = this.queue[index];
            this.queue[index] = this.queue[this.size - 1];
            this.queue[size - 1] = null;
        }
        this.resizeQueue(REMOVE, this.size, this.size - 1);
        this.size--;
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        emptyCheck();
        Item item;
        if (this.size == 1) {
            item = this.queue[0];
        } else {
            int index = StdRandom.uniform(0, this.size);
            item = this.queue[index];
        }
        return item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int iterSize;
        private Item[] iterQueue;

        public RandomizedQueueIterator() {
            this.iterSize = size;
            this.iterQueue = (Item[]) new Object[this.iterSize];
            for (int i = 0; i < this.iterSize; i++) {
                this.iterQueue[i] = queue[i];
            }
        }

        public boolean hasNext() {
            return this.iterSize > 0;
        }

        public Item next() {
            if (this.hasNext()) {
                Item item;

                if (this.iterSize > 1) {
                    int nextIndex = StdRandom.uniform(0, this.iterSize);
                    item = this.iterQueue[nextIndex];
                    this.iterQueue[nextIndex] = this.iterQueue[iterSize - 1];
                    this.iterQueue[iterSize - 1] = null;
                } else {
                    item = iterQueue[0];
                }

                this.iterSize--;
                return item;
            }

            throw new java.util.NoSuchElementException();
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
    }
}