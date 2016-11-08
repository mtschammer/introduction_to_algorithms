public class RandomizedQueueUnitTests {
    private static void printRandomizedQueue(RandomizedQueue<String> rq) {
        for (String s : rq) {
            System.out.print(String.format("%s ", s));
        }

        System.out.println("");
    }

    private static void printRQSize(RandomizedQueue<String> rq) {
        System.out.println(String.format("Queue size: %d", rq.size()));
    }

//    private static void printRQLength(RandomizedQueue<String> rq) {
//        System.out.print(String.format("Length: %d ", rq.length()));
//    }

    private static void testIterator(){
        System.out.println("\nTESTING ITERATOR");
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(String.format("Test_%d", i));
        }

        for (int i = 0; i < 5; i++) {
            printRandomizedQueue(rq);
        }

        System.out.println("Nested test:");
        for (String s : rq) {
            for (String s2 : rq) {
                System.out.print(String.format("(s: %s, s2: %s) ", s, s2));
            }
            System.out.println("");
        }
    }

    private static void testResizing(){
        System.out.println("\nTESTING RESIZING");
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 511; i++) {
            rq.enqueue(String.format("Test_%d", i));
        }
        System.out.print("(512, 511): ");
//        printRQLength(rq);
        printRQSize(rq);

        rq.enqueue("LAST");
        System.out.print("(1024, 512): ");
//        printRQLength(rq);
        printRQSize(rq);

        rq.dequeue();
        System.out.print("(1024, 511): ");
//        printRQLength(rq);
        printRQSize(rq);

        for (int i = 0; i < 254; i++) {
            rq.dequeue();
        }
        System.out.print("(1024, 257): ");
//        printRQLength(rq);
        printRQSize(rq);

        rq.dequeue();
        System.out.print("(512, 256): ");
//        printRQLength(rq);
        printRQSize(rq);


        rq.enqueue("ONE ABOVE QUARTER FULL");
        System.out.print("(512, 257): ");
//        printRQLength(rq);
        printRQSize(rq);
    }

    public static void testSampling(){
        System.out.println("\nTESTING SAMPLING");
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 511; i++) {
            rq.enqueue(String.format("Test_%d", i));
        }
        printRQSize(rq);
        System.out.println("Samples:");
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        printRQSize(rq);
    }

    public static void testDequeuing(){
        System.out.println("\nTESTING DEQUEUING");
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 511; i++) {
            rq.enqueue(String.format("Test_%d", i));
        }
        printRQSize(rq);
        System.out.println("Dequeues:");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        printRQSize(rq);
    }

    public static void main(String[] args) {
        testIterator();
        testResizing();
        testSampling();
        testDequeuing();
    }
}
