import java.util.ArrayList;
import java.util.Collections;
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
    private int maxLevel;

    /**
     * Level of the SkipList. An empty skip list has a level of 1
     */
    private int level = 1;

    /**
     * Get the level of the skip list
     *
     * @return {@code int} level of the skip list
     */
    public int level() {
        return level;
    }

    private int size;

    /**
     * Get the size of the skip list
     *
     * @return {@code int} size of the skip list
     */
    public int size() {
        return size;
    }


    public SkipList() {
        size = 0;
        maxLevel = 0;
        // a SkipListNode with value null marks the beginning
        head = new Node(null, null, 1);
        // null marks the end
        head.forwards.add(null);
    }

    /**
     * Main entry
     *
     * @param args {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        int MAXIMUM = 200;
        int TEST_SIZE = 100;
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
     * Remove an element by the key
     *
     * @param key {@code K} key of the element
     * @return {@code V} value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 5 Part 1-2 -- skip list deletion
        ArrayList<Node> update = new ArrayList<>(maxLevel+1);
        Node current = head;
        for (int i =level+1; i>=0; i--){
            while(current.forwards.get(i)!= null && lessThan(current.forwards.get(i).key, key))
                current = current.forwards.get(i);
            update.add(current);
        }
        // update reverse order
        Collections.reverse(update);
        System.out.println(update);

//
//        * reached level 0 and forward pointer to
//        right, which is possibly our desired node.*/
        current = current.forwards.get(0);

        // If current node is target node
        if(current != null && current.key.equals(key))
        {
        /* start from lowest level and rearrange
           pointers just like we do in singly linked list
           to remove target node */
            for(int i=0; i<=level+1;i++)
            {
            /* If at level i, next node is not target
               node, break the loop, no need to move
              further level */
                if(update.get(i).forwards.get(i) != current)
                    break;

                update.get(i).forwards.set(i, current.forwards.get(i));
            }
         // Remove levels having no elements
            while(level>0 && head.forwards.get(level) == null) {
                level--;

            }
        }
        return current.value;
    }

    public String toStringR(Node node, int level) {

        StringBuilder outString = new StringBuilder();
        if (node == null || node.value == null) {
            return null;
        }
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

        ArrayList<K> baseK = new ArrayList<>();

        for (int l = 0; l < maxLevel; l++) {
            outString.append(String.format("LEVEL: %d ", l));
            outString.append(toStringR(head.forwards.get(l), l));
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
        return Integer.min(newLevel, maxLevel+1);
    }

    /**
     * Insert a Key Value pair into the  an element by the key
     *
     * @param key {@code K} key of the element to be added
     * @return {@code boolean} sucess of insert
     */
    public boolean insert(K key, V value) {
        if (contains(key))
            return false; //TODO MAKE IT SO IT UPDATES THE key val pair
        size++;
        int level = randomLevel();

            // should only happen once
        while (level > maxLevel) {
            head.forwards.add(null);
            maxLevel++;
        }
        Node newNode = new Node(key, value, level);
        Node current = head;
        do {
            current = findNext(key, current, level);
            newNode.forwards.add(0, current.forwards.get(level));
            current.forwards.set(level, newNode);
        } while (level-- > 0);
        return true;
    }

    /**
     * Returns the SkipList node with greatest key value <= key
     *
     * @param key
     * @return
     */
    private Node find(K key) {
        return find(key, head, maxLevel);
    }

    /**
     * Returns the SkipList node with greatest key value <= key
     * Starts at node start and level
     *
     * @param key
     * @param current
     * @param level
     * @return
     */

    private Node find(K key, Node current, int level) {
        do {
            current = findNext(key, current, level);
        } while (level-- > 0);
        return current;
    }

    /**
     * Returns the node at a given level with highest key value less than key
     *
     * @param key
     * @param current
     * @param level
     * @return
     */
    private Node findNext(K key, Node current, int level) {
        Node next = current.forwards.get(level);
        while (next != null) {
            if (lessThan(key, next.key)) // searchKey < next.key //TODO check
                break;
            current = next;
            next = current.forwards.get(level);
        }
        return current;
    }

    /**
     * Search for an element by the key
     *
     * @param key {@code K} key of the element
     * @return {@code V} value of the target element
     */
    public V search(K key) {
        // TODO: Lab 5 Part 1-3 -- skip list node search
        Node node = find(key);
        if (node != null && node.value != null && equalTo(node.key, key)) {
            return node.value;
        } else {
            return null;
        }
    }

    /******************************************************************************
     * Utility Functions                                                           *
     ******************************************************************************/

    public boolean contains(K key) {
        Node node = find(key);
        return node != null && node.value != null && equalTo(node.key, key);
    }

    private boolean lessThan(K a, K b) {
        return a.compareTo(b) < 0;
    }

    private boolean equalTo(K a, K b) {
        return a.compareTo(b) == 0;
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
            return String.format("%s:%s", key, value);
        }
    }
}
