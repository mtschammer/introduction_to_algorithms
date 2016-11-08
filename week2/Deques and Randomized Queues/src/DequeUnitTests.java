import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeUnitTests {
    private static final String FRONT = "front";
    private static final String BACK = "back";

    private static void printDeque(Deque<String> deque) {
        for (String s : deque) {
            System.out.print(String.format("%s ", s));
        }

        System.out.println("");
    }

    private static Deque<String> addItems(Deque<String> deque,
                                          int add, String direction) {
        System.out.println(String.format("\nAdding %d items at %s.", add,
                direction));
        for (int i = 0; i < add; i++) {
            if (direction.equals(FRONT)) deque.addFirst(String.format("%d", i));
            else deque.addLast(String.format("%d", i));

            printDeque(deque);
        }

        assert deque.size() == add;

        return deque;
    }

    private static Deque<String> removeItems(Deque<String> deque,
                                             int remove, String direction) {
        int expectedSize = deque.size() - remove;

        System.out.println(String.format("\nRemoving %d items at %s.", remove,
                direction));
        for (int i = 0; i < remove; i++) {
            printDeque(deque);

            if (direction.equals(FRONT)) deque.removeFirst();
            else deque.removeLast();

        }

        assert deque.size() == expectedSize;

        return deque;

    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque = addItems(deque, 10, FRONT);
        deque = removeItems(deque, 10, FRONT);
        deque = addItems(deque, 10, BACK);
        removeItems(deque, 10, BACK);
        try {
            removeItems(deque, 1, BACK);
        } catch(Exception e){
            assert e instanceof NoSuchElementException;
        }
    }
}
