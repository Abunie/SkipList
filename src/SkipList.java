import java.util.ArrayList;

/**
 * Lab 6: Java Collection Framework, Skip List and Apache ANT <br />
 * The {@code SkipList} class
 * @param <K>           {@code K} key of each skip list node
 * @param <V>           {@code V} value of each skip list node
 */
public class SkipList<K extends Comparable<K>, V> {

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
            for (int i = 0; i < level; i++)
                forwards.add(null);
        }
        public String toString() {
            return String.format("%s(%s,%d)", value, key, forwards.size());
        }
    }

    /**
     * Level of the skip list. An empty skip list has a level of 1
     */
    private int level = 1;

    /**
     * Size of the skip list
     */
    private int size = 0;

    /**
     * Insert an new element into the skip list
     * @param key       {@code K} key of the new element
     * @param value     {@code V} value of the new element
     */
    public void insert(K key, V value) {
        // TODO: Lab 5 Part 1-1 -- skip list insertion
        
    	// search and maintain a list of pointers - updates - containing all the turning nodes
    	
    		// key found: 
    	if(search(key) != null) {
    		// update value and done, return;
    		getNode(key).value = value;
    		return;
    		// key not found: 	
    	} else {
            // create a new node with RANDOM level and point its forward in each level to null first;
    	}
    	
    	// from level 0 to min(currentLevel, newLevel) -1:
    	//	set new node's forward to the forward of updates;
    	//	set forward of update to the new node;
    	
    	// if newLevel > currentLevel: 
    	//	raise head's level and point head's new forwards to the new node
    }

    /**
     * Remove an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 5 Part 1-2 -- skip list deletion
    	// search and maintain a list of pointers - updates - containing all the turning nodes
    	
    	// turning nodes as:
    	//	current.key < key <= forward.key;
    	//	(forward being null is NOT a turning node);
    	
    		// key not found: 	
    	if (search(key) == null) {
    		// deletion failed, and return null;
    		return null;
    		// key found:
    	} else {
    		
    	}
    	
    	// in each level of updates:
    	// 	if forward is not null:
    	//		then unlink the current node: 
    	//			set forward of update to forward's forward;
    	// remove levels where forward of head is null;
    	
    	// return forwards value;
    	
        return null;
    }
    
    
    
    /**
     * Search for an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code Node} node of the target element
     */
    public Node getNode(K key) {
    	//TODO:
    	return null;
    }

    /**
     * Search for an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the target element
     */
    public V search(K key) {
        // TODO: Lab 5 Part 1-3 -- skip list node search
        
    	// start from head's top level (fastest list)
    	
    	// forward is null, or current.key < key <= forward.key:
    	//	forward is not null and forward.key = key:
    	//		return forward.value;
    	// 	already at lowest level: 
    	//		target not found, return null;
    	//	Otherwise: 
    	//		move down to level-1 (current being a turning node)
    	
    	// Otherwise: 
    	//	move forward
    	
        return null;
    }

    /**
     * Get the level of the skip list
     * @return          {@code int} level of the skip list
     */
    public int level() {
        return level;
    }

    /**
     * Get the size of the skip list
     * @return          {@code int} size of the skip list
     */
    public int size() {
        return size;
    }

    /**
     * Print the skip list
     * @return          {@code String} the string format of the skip list
     */
    public String toString() {
        // TODO: Lab 5 Part 1-4 -- skip list printing

        return null;
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        SkipList<Integer, String> list = new SkipList<Integer, String>();
        int[] keys = new int[10];
        for (int i = 0; i < 10; i++) {                          // Insert elements
            keys[i] = (int) (Math.random() * 200);
            list.insert(keys[i], "\"" + keys[i] + "\"");
        }

        System.out.println(list);

        for (int i = 0; i < 10; i += 3) {
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

}
