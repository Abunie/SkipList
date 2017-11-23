import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GenericRedBlackTreeTest {
    private SkipList<Integer, String> skiplist;
    private int TEST_SIZE = 20;
    private int MAXIMUM = 200;
    private int[] keys = new int[TEST_SIZE];

    @Before
    public void setUp() {
        skiplist = new SkipList<Integer, String>();
        for (int i = 0; i < TEST_SIZE; i++) {                          // Insert elements
            keys[i] = (int) (Math.random() * MAXIMUM);
            skiplist.insert(keys[i], "\"" + keys[i] + "\"");
        }
        System.out.println(skiplist);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Convert a GenericRedBlack tree String value to a integer correctly
     *
     * @param value {@code String}
     * @return {@code int}
     */
    private int convertValue(String value) {
        return Integer.parseInt(value.replace("\"", ""));
    }

    @Test
    public void test_printRBT() {
        System.out.println(skiplist);
    }

    @Test
    public void test_findOne() {
        assertNotNull(skiplist.search(keys[0]));
    }

    @Test
    public void test_findAll() {
        for (int i = 0; i < TEST_SIZE; i += 3) {
            assertNotNull(skiplist.search(keys[i]));
        }
    }

    @Test
    public void test_findFail() {
        assertEquals(skiplist.search(-3), null);
    }

    @Test
    public void test_removeOne() {
        System.out.println(String.format("Removing key %d", keys[0]));
        System.out.println(String.format("Before:\n%s", skiplist));

        assertNotNull(skiplist.remove(keys[0]));

        System.out.println(String.format("After:\n%s", skiplist));
        System.out.println();
    }

    @Test
    public void test_removeAll() {
        // ensure that we don't repeat deleting keys would raise an error!
        List<Integer> deletedKeys = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; i++) {
            if (!deletedKeys.contains(keys[i])) {
                System.out.println(String.format("Removing key %d", keys[i]));
                System.out.println(String.format("Before:\n%s", skiplist));

                // check if proper value has been removed
                assertNotNull(skiplist.remove(keys[i]));

                System.out.println(String.format("After:\n%s", skiplist));
                System.out.println();

                deletedKeys.add(keys[i]);
            }
        }
        System.out.println(skiplist);
    }

    @Test
    public void test_removeSome() {
        // ensure that we don't repeat deleting keys would raise an error!
        List<Integer> deletedKeys = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; i = +3) {
            if (!deletedKeys.contains(keys[i])) {
                System.out.println(String.format("Removing key %d", keys[i]));
                System.out.println(String.format("Before:\n%s", skiplist));

                // check if proper value has been removed
                assertEquals(keys[i], convertValue(skiplist.remove(keys[i])));

                System.out.println(String.format("After:\n%s", skiplist));
                System.out.println();

                deletedKeys.add(keys[i]);
            }
        }
        System.out.println(skiplist);
    }
}