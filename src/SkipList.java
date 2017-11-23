import java.util.ArrayList;
import java.util.Random;


/**
 * Lab 6: Java Collection Framework, Skip List and Apache ANT <br />
 * The {@code SkipList} class
 *
 * @param <K> {@code K} key of each skip list node
 * @param <V> {@code V} value of each skip list node
 */
public class SkipList<K extends Comparable<K>, V> {

    private static final double PROBABILITY = 0.5;
    private Random rand = new Random();
    private Node head;
    private ArrayList<Node> update;

    /**
     * Level of the SkipList. An empty skip list has a level of 1
     */
    private int level = 0;
    private int maxLevel;
    private int size;

    public SkipList() {
        size = 0;
        // a SkipListNode with value null marks the beginning
        head = new Node(null, null, 0);
    }

    /**
     * Main entry
     *
     * @param args {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        int MAXIMUM = 200;
        int TEST_SIZE = 10;
        SkipList<Integer, String> list = new SkipList<Integer, String>();
        int[] keys = new int[TEST_SIZE];
        for (int i = 0; i < TEST_SIZE; i++) {                          // Insert elements
            keys[i] = (int) (Math.random() * MAXIMUM);
            list.insert(keys[i], "\"" + keys[i] + "\"");
            System.out.println(list);
        }

        System.out.println(list);

        for (int i = 0; i < TEST_SIZE; i += 3) {
            int key = keys[i];
            // Search elements
            System.out.println(String.format("Find element             %3d: value=%s", key, list.search(key)));
            // Remove some elements
            System.out.println(String.format("Remove element           %3d: value=%s", key, list.remove(key)));
            // Search the removed elements
            System.out.println(String.format("Find the removed element %3d: value=%s", key, list.search(key)));
        }
        System.out.println(list);
    }

    /**
     * Get the level of the skip list
     *
     * @return {@code int} level of the skip list
     */
    public int level() {
        return level;
    }

    /**
     * Get the size of the skip list
     *
     * @return {@code int} size of the skip list
     */
    public int size() {
        return size;
    }

    /**
     * Remove an element by the key
     *
     * @param key {@code K} key of the element
     * @return {@code V} value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 5 Part 1-2 -- skip list deletion
        return null;
    }

    private String toStringR(Node node, int level) {
        if (node == null || node.value == null)
            return null;
        StringBuilder outString = new StringBuilder();
        outString.append(node);
        outString.append("->");
        outString.append(toStringR(node.forwards.get(level), level));
        return outString.toString();
    }

    /**
     * Print the SkipList
     *
     * @return {@code String} the string format of the skip list
     */
    public String toString() {
        // TODO: Lab 5 Part 1-4 -- skip list printing
        StringBuilder outString = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            outString.append(String.format("LEVEL: %d ", i));
            outString.append(toStringR(head.forwards.get(i), i));
            outString.append("\n");
        }
        return outString.toString();
    }

    /**
     * Return a random level
     *
     * @return {@code int} random integer from 0 to maxLevel+1 (inclusive)
     */
    private int randomLevel() {
        int newLevel = 0;
        while (rand.nextFloat() < PROBABILITY)
            newLevel++;
        newLevel = Integer.min(newLevel, maxLevel + 1);
        if (newLevel > maxLevel)
            maxLevel = newLevel;
        return newLevel;
    }

    /**
     * Insert a Key Value pair into the  an element by the key
     *
     * @param key {@code K} key of the element to be added
     * @return {@code boolean} sucess of insert
     */
    public void insert(K key, V value) {
        if (search(key) != null) {
            return;
        }

        int newLevel = randomLevel();

        // fix head
        if (newLevel > level) {
            Node temp = head;
            head = new Node(null, null, newLevel);
            for (int i = 0; i <= level; i++) {
                head.forwards.set(i, temp.forwards.get(i));
            }
            level = newLevel;
        }

        // clean updates
        search(key);

        Node newNode = new Node(key, value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.forwards.set(i, update.get(i).forwards.get(i));
            update.get(i).forwards.set(i, newNode);
        }
        size++;
    }

    /**
     * Search for an element by the key
     *
     * @param key {@code K} key of the element
     * @return {@code V} value of the target element
     */
    public Node search(K key) {
        update = new ArrayList<>();
        for (int i = 0; i <= level; i++) {
            update.add(null);
        }
        Node current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forwards.get(i) != null && key.compareTo(current.forwards.get(i).key) > 0)
                current = current.forwards.get(i);
            update.set(i, current);
        }

        current = current.forwards.get(0);

        if (current != null && key.equals(current.key)) {
            return current;
        } else {
            return null;
        }
    }


    /**
     * The {@code Node} class for {@code SkipList}
     */
    private class Node {
        public K key;
        public V value;
        public ArrayList<Node> forwards = new ArrayList<Node>();

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            for (int i = 0; i <= level; i++)
                forwards.add(null);
        }

        public String toString() {
            return String.format("%s", value);
        }
    }
}
