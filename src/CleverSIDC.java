import java.util.Random;

/**
 * CleverSIDC class represents a data structure that can be either a CircularArray or an AVLTree based on a specified threshold.
 * It provides methods for managing and interacting with the underlying data structure, including key generation, insertion, removal,
 * and retrieval operations, as well as operations specific to either the CircularArray or AVLTree.
 * Additionally, it contains overridden methods for both CircularArray and AVLTree operations when applicable.
 * The choice between CircularArray and AVLTree is determined by the size threshold specified during initialization.
 * Implements the main functionalities of the CleverSIDC data structure.
 * @author Rania Maoukout & teammate
 */
public class CleverSIDC {
    private CircularEntryArray sidcArray; private AvlTree sidcTree; private AvlTree.AvlTreeNode sidcNode;
    private int sizeOfThreshold; Random random = new Random();
    private final String[] firstNames = {"Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Harry"};
    private final String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Gill"};

    /**
     * This method defines the size of the list.
     * @param size the size to be set for the CleverSIDC list
     */
    public void setSIDCThreshold(int size) {
        sizeOfThreshold = size;
        if(sizeOfThreshold <= 500) {
            sidcArray = new CircularEntryArray();
        } else {
            sidcTree = new AvlTree();
        }
    }

    /**
     * This method randomly generates new non-existing keys of 8 digits.
     * @return the generated key.
     */
    public long generate() {
        long newKey;

        if(sizeOfThreshold <= 500) {
            do {
                newKey = 10000000L + (Math.abs(random.nextLong()) % 90000000L);
            } while(sidcArray.findKeyInArray(newKey) != -1);
            return newKey;
        }
        if(sidcTree.root == null) return (10000000L + (Math.abs(random.nextLong()) % 90000000L));
        do {
            newKey = 10000000L + (Math.abs(random.nextLong()) % 90000000L);
        } while(sidcTree.findKeyInTree(sidcTree.root, newKey) != null);
        return newKey;
    }

    /**
     * This method randomly generates new student names as String values.
     * @return the generated student names.
     */
    public String generateValues() {
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    /**
     * This method returns all keys in CleverSIDC as a sorted sequence.
     * @param cleverSidc the CleverSIDC object in context
     */
    public void allKeys(CleverSIDC cleverSidc) {
        if(sizeOfThreshold <= 500) {
            cleverSidc.sidcArray.mergeSortCircularArray(0, sidcArray.getArraySize() - 1);
            cleverSidc.sidcArray.printCircularArray();
        } else {
            cleverSidc.sidcTree.inOrderTraversal(sidcTree.root);
        }
    }

    /**
     * This method adds an entry for the given key and value.
     * @param cleverSidc the CleverSIDC object in context
     * @param key the new key entry
     * @param value the value associated with the key entry
     */
    public void add(CleverSIDC cleverSidc, long key, String value) {
        if(sizeOfThreshold <= 500) {
            cleverSidc.sidcArray.addKeyToArray(key, value);
        } else {
            cleverSidc.sidcTree.root = cleverSidc.sidcTree.addNodeToTree(cleverSidc.sidcTree.root, key, value);
        }
    }

    /**
     * This method removes the entry for the given key.
     * @param cleverSidc the CleverSIDC object in context
     * @param key the key to be removed
     * @return The removed key.
     */
    public long remove(CleverSIDC cleverSidc, long key) {
        if(sizeOfThreshold <= 500) {
            return cleverSidc.sidcArray.removeKeyFromArray(key);
        }
        sidcNode = cleverSidc.sidcTree.deleteNodeFromTree(sidcTree.root, key);
        if(sidcNode == null) return 0;
        return sidcNode.getKey();
    }

    /**
     * This method returns the values of the given key.
     * @param cleverSidc the CleverSIDC object in context
     * @param key the key for which values are to be retrieved
     * @return The value of the given key.
     */
    public String getValues(CleverSIDC cleverSidc, long key) {
        if(sizeOfThreshold <= 500) {
            return cleverSidc.sidcArray.getValueInArray(key);
        }
        sidcNode = cleverSidc.sidcTree.findKeyInTree(sidcTree.root, key);
        if(sidcNode == null) return "";
        return sidcNode.getValue();
    }

    /**
     * This method returns the predecessor key of the given key.
     * @param cleverSidc the CleverSIDC object in context
     * @param key the key for which the predecessor is to be found
     * @return The predecessor key.
     */
    public long prevKey(CleverSIDC cleverSidc, long key) {
        if(sizeOfThreshold <= 500) {
            return cleverSidc.sidcArray.getKeyAtIndex(cleverSidc.sidcArray.findKeyInArray(key) - 1);
        }
        sidcNode = cleverSidc.sidcTree.findKeyInTree(sidcTree.root, key).getLeft();
        if(sidcNode == null) {
            System.out.println("The given key doesn't have a previous key i.e, there is no key to the left of the given key.\n");
            return 0;
        }
        return sidcNode.getKey();
    }

    /**
     * This method returns the successor key of the given key.
     * @param cleverSidc the CleverSIDC object in context
     * @param key the key for which the successor is to be found
     * @return The successor key.
     */
    public long nextKey(CleverSIDC cleverSidc, long key) {
        if(sizeOfThreshold <= 500) {
            return cleverSidc.sidcArray.getKeyAtIndex(cleverSidc.sidcArray.findKeyInArray(key) + 1);
        }
        sidcNode = cleverSidc.sidcTree.findKeyInTree(sidcTree.root, key).getRight();
        if(sidcNode == null) {
            System.out.println("The given key doesn't have a next key i.e, there is no key to the right of the given key.\n");
            return 0;
        }
        return sidcNode.getKey();
    }

    /**
     * This method returns the number of keys that are within the specified range of key1 and key2.
     * @param key1 the starting key of the range
     * @param key2 the ending key of the range
     * @return The number of keys within the specified range.
     */
    public int rangeKey(long key1, long key2) {
        if(sizeOfThreshold <= 500) {
            return sidcArray.rangeOfKeyInArray(key1, key2);
        }
        return sidcTree.rangeOfKeyInTree(sidcTree.root, key1, key2);
    }

    // ---------------------------------------------------------------- //
    //                        OVERRIDDEN METHODS                        //
    // ---------------------------------------------------------------- //
    /**
     * This method retrieves the key at the specified index in the circular array.
     * @param index the index of the key to retrieve
     * @return the key at the specified index.
     * @throws IndexOutOfBoundsException if the provided index is out of bounds.
     */
    public long getKeyAtIndex(int index) {
        return -1;
    }

    /**
     * This method finds the index of the specified key in the circular array.
     * @param key the key to search for in the array
     * @return the index of the key if found, or -1 if the key is not present in the array.
     */
    public int findKeyInArray(long key) {
        return -1;
    }

    /**
     * This method retrieves the value associated with the specified key in the circular array.
     * @param key the key for which to retrieve the associated value
     * @return the value associated with the key. If the key does not exist, returns a default message.
     */
    public String getValueInArray(long key) {
        return "";
    }

    /**
     * This method applies the merge sort algorithm to sort a subarray of the circular array.
     * @param left the starting index of the subarray to be sorted
     * @param right the ending index of the subarray to be sorted
     */
    public void mergeSortCircularArray(int left, int right) {}

    /**
     * This method prints the key-value pairs in the circular array.
     */
    public void printCircularArray() {
        if(sidcArray != null) {
            System.out.println("\n~~~ Displaying the contents of the circular array;");
            sidcArray.printCircularArray();
        }
    }

    /**
     * This method adds a key-value pair to the array if the key is not already present.
     * @param keyToAdd the new key to add
     * @param valueToAdd the value associated with the key
     */
    public void addKeyToArray(long keyToAdd, String valueToAdd) {}

    /**
     * This method removes the specified key from the array and returns the removed key.
     * @param keyToRemove the key to be removed from the array
     * @return the removed key, or -1 if the key is not found in the array.
     */
    public long removeKeyFromArray(long keyToRemove) {
       return -1;
    }

    /**
     * This method finds the range of indices in the array for keys falling within the specified range (key1, key2).
     * @param key1 the lower bound of the key range
     * @param key2 the upper bound of the key range
     * @return the number of elements in the array with keys in the specified range.
     * Returns -1 if one of the keys does not exist in the array, or 0 if the index of key1 is after that of key2.
     */
    public int rangeOfKeyInArray(long key1, long key2) {
       return -1;
    }

    /**
     * This method recursively searches for a key in the AVL tree starting from the given node.
     * @param node the root node of the current subtree
     * @param keyToFind the key to search for in the AVL tree
     * @return the AVL tree node containing the specified key, or null if the key is not found.
     */
    public AvlTree.AvlTreeNode findKeyInTree(AvlTree.AvlTreeNode node, long keyToFind) {
        return null;
    }

    /**
     * This method performs an in-order traversal of the AVL tree starting from the given node, printing the key-value pairs of each node.
     * @param node the root node of the current subtree
     */
    public void inOrderTraversal(AvlTree.AvlTreeNode node) {}

    /**
     * This method adds a new node with the specified key and value to the AVL tree rooted at the given node.
     * After insertion, updates the balance factor of each node and performs necessary rotations to maintain the AVL tree balance.
     * @param node the root node of the current subtree
     * @param newKey the key of the new node to be added
     * @param newValue the value associated with the new key
     * @return the root node of the updated AVL tree.
     */
    public AvlTree.AvlTreeNode addNodeToTree(AvlTree.AvlTreeNode node, long newKey, String newValue) {
        return null;
    }

    /**
     * This method deletes the node with the specified key from the AVL tree rooted at the given node.
     * After deletion, updates the balance factor of each node and performs necessary rotations to maintain the AVL tree balance.
     * @param node the root node of the current subtree
     * @param keyToRemove the key of the node to be removed from the AVL tree
     * @return the root node of the updated AVL tree.
     */
    public AvlTree.AvlTreeNode deleteNodeFromTree(AvlTree.AvlTreeNode node, long keyToRemove) {
        return null;
    }

    /**
     * This method counts the number of nodes in the AVL tree rooted at the given node whose keys fall within the specified range [key1, key2].
     * @param node the root node of the current subtree
     * @param key1 the lower bound of the key range
     * @param key2 the upper bound of the key range
     * @return the count of nodes with keys in the specified range.
     */
    public int rangeOfKeyInTree(AvlTree.AvlTreeNode node, long key1, long key2) {
        return -1;
    }
}
