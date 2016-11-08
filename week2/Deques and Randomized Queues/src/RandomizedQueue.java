import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size = 0;

    private static final int REMOVE = 0, ADD = 1;
    private static final double QUARTER_FULL = 0.25;


    // construct an empty randomized queue
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[512];
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

    private void copyQueue(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];

        for(int i = 0; i < this.size; i++){
            newQueue[i] = this.queue[i];
            this.queue[i] = null;
        }

        this.queue = newQueue;
    }

    private void resizeQueue(int operation, int oldSize, int newSize) {
        double oldUtilizedRatio = (double) oldSize / this.queue.length;
        double newUtilizedRatio = (double) newSize / this.queue.length;

        if (operation == ADD && newSize == this.queue.length) {
            this.copyQueue(this.queue.length * 2);
        } else if (operation == REMOVE && oldUtilizedRatio > QUARTER_FULL
                && newUtilizedRatio <= QUARTER_FULL) {

            // +1 to round up result if length is uneven number.
            this.copyQueue((this.queue.length + 1) / 2);
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
            int index = StdRandom.uniform(0, this.size - 1);
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
            int index = StdRandom.uniform(0, this.size - 1);
            item = this.queue[index];
        }
        return item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        int iterSize;
        int[] indexes;

        public RandomizedQueueIterator() {
            this.iterSize = size;
            this.indexes = new int[size];
            for (int i = 0; i < this.iterSize; i++) {
                this.indexes[i] = i;
            }
        }

        public boolean hasNext() {
            return this.iterSize > 0;
        }

        public Item next() {
            Item item;

            if (this.iterSize > 1) {
                int nextIndex = StdRandom.uniform(0, this.iterSize - 1);
                item = queue[this.indexes[nextIndex]];
                this.indexes[nextIndex] = this.indexes[iterSize - 1];
                this.indexes[iterSize - 1] = -1;
            } else{
                item = queue[this.indexes[0]];
            }

            this.iterSize--;
            return item;
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