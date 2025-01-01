
/**
 * Implementation of a self-balancing AVL Tree, an extension of the CleverSIDC framework.
 * AVL trees maintain balance to ensure efficient search, insertion, and deletion operations.
 * @author Rania Maoukout(40249281) & Barbara Eguche(40245327)
 */
public class AvlTree extends CleverSIDC {
    /**
     * A protected static inner class representing a node in an AVL tree.
     */
    protected static class AvlTreeNode {
        private int height; private long key; private String value; private AvlTreeNode left, right;

        /**
         * Parameterized constructor.
         * @param newKey the key of new node
         * @param newValue the value associated with the key
         */
        public AvlTreeNode(long newKey, String newValue) {
            setKey(newKey);
            setValue(newValue);
            setHeight(1);
        }

        /**
         * This method returns the height of a node.
         * @return the height of node
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * This method sets the height of a node.
         * @param newHeight the new height
         */
        public void setHeight(int newHeight) {
            height = newHeight;
        }

        /**
         * This method returns the key of a node.
         * @return the key of the node
         */
        public long getKey() {
            return this.key;
        }

        /**
         * This method sets the key of a node.
         * @param newKey the new key
         */
        public void setKey(long newKey) {
            key = newKey;
        }

        /**
         * This method returns the value of a node.
         * @return the value of the node
         */
        public String getValue() {
            return this.value;
        }

        /**
         * This method sets the value of a node.
         * @param newValue the new value
         */
        public void setValue(String newValue) {
            value = newValue;
        }

        /**
         * This method returns the left node of the current node.
         * @return the left node of the current node
         */
        public AvlTreeNode getLeft() {
            return this.left;
        }

        /**
         * This method returns the right node of the current node.
         * @return the right node of the current node
         */
        public AvlTreeNode getRight() {
            return this.right;
        }
    }

    AvlTreeNode root;

    /**
     * This method gets the height of the specified AVL tree node.
     * @param node the AVL tree node for which to retrieve the height
     * @return the height of the node. If the node is null, returns 0.
     */
    private int getHeightOfNode(AvlTreeNode node) {
        if(node == null) return 0;
        return node.getHeight();
    }

    /**
     * This method calculates the balance factor of the specified AVL tree node.
     * @param node the AVL tree node for which to calculate the balance factor
     * @return the balance factor, computed as the difference between the height of the left subtree and the height of the right subtree. If the node is null, returns 0.
     */
    private int getBalanceFactor(AvlTreeNode node) {
        return getHeightOfNode(node.getLeft()) - getHeightOfNode(node.getRight());
    }

    /**
     * This method returns the maximum of two integers.
     * @param left the first integer
     * @param right the second integer
     * @return the larger of the two integers. If they are equal, returns either one.
     */
    private int getMax(int left, int right) {
        return (left > right)? left : right;
    }

    /**
     * This method finds and returns the AVL tree node with the minimum value in the given subtree.
     * @param node the root node of the subtree to search
     * @return the AVL tree node with the minimum value in the subtree.
     */
    private AvlTreeNode nodeWithMinimumValue(AvlTreeNode node) {
        AvlTreeNode current = node;
        while(current.getLeft() != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * This method performs a left rotation operation on the specified AVL tree node.
     * @param node the root node of the subtree requiring a left rotation
     * @return the new root node after the left rotation.
     */
    private AvlTreeNode leftRotation(AvlTreeNode node) {
        AvlTreeNode x = node.right;
        AvlTreeNode y = x.left;
        x.left = node;
        node.right = y;

        node.setHeight(1 + getMax(getHeightOfNode(node.getLeft()), getHeightOfNode(node.getRight())));
        x.setHeight(1 + getMax(getHeightOfNode(x.getLeft()), getHeightOfNode(x.getRight())));
        return x;
    }

    /**
     * This method performs a right rotation operation on the specified AVL tree node.
     * @param node the root node of the subtree requiring a right rotation
     * @return the new root node after the right rotation.
     */
    private AvlTreeNode rightRotation(AvlTreeNode node) {
        AvlTreeNode x = node.left;
        AvlTreeNode y = x.right;
        x.right = node;
        node.left = y;

        node.setHeight(1 + getMax(getHeightOfNode(node.getLeft()), getHeightOfNode(node.getRight())));
        x.setHeight(1 + getMax(getHeightOfNode(x.getLeft()), getHeightOfNode(x.getRight())));
        return x;
    }

    /**
     * This method recursively searches for a key in the AVL tree starting from the given node.
     * @param node the root node of the current subtree
     * @param keyToFind the key to search for in the AVL tree
     * @return the AVL tree node containing the specified key, or null if the key is not found.
     */
    @Override
    public AvlTreeNode findKeyInTree(AvlTreeNode node, long keyToFind) {
        //key not found
        if(node == null) return null;

        //find the position of the key
        if(keyToFind == node.getKey()) return node;
        if(keyToFind < node.getKey()) {
            return findKeyInTree(node.getLeft(), keyToFind);
        } else if(keyToFind > node.getKey()) {
            return findKeyInTree(node.getRight(), keyToFind);
        } else return node;
    }

    /**
     * This method performs an in-order traversal of the AVL tree starting from the given node, printing the key-value pairs of each node.
     * @param node the root node of the current subtree
     */
    @Override
    public void inOrderTraversal(AvlTreeNode node) {
        if(node != null) {
            inOrderTraversal(node.getLeft());
            System.out.println(node.getKey() + " -- " + node.getValue());
            inOrderTraversal(node.getRight());
        }
    }

    /**
     * This method adds a new node with the specified key and value to the AVL tree rooted at the given node.
     * After insertion, updates the balance factor of each node and performs necessary rotations to maintain the AVL tree balance.
     * @param node the root node of the current subtree
     * @param newKey the key of the new node to be added
     * @param newValue the value associated with the new key
     * @return the root node of the updated AVL tree.
     */
    @Override
    public AvlTreeNode addNodeToTree(AvlTreeNode node, long newKey, String newValue) {

        //find the position and insert the new node
        if(node == null) return (new AvlTreeNode(newKey, newValue));
        if(newKey < node.getKey()) {
            node.left = addNodeToTree(node.getLeft(), newKey, newValue);
        } else if(newKey > node.getKey()) {
            node.right = addNodeToTree(node.getRight(), newKey, newValue);
        } else return node;

        //update the balance factor of each node, and balance the tree
        node.setHeight(1 + getMax(getHeightOfNode(node.getLeft()), getHeightOfNode(node.getRight())));

        int balanceFactor = getBalanceFactor(node);
        if(balanceFactor > 1) {
            if(newKey < node.getLeft().getKey()) {
                return rightRotation(node);
            } else if(newKey > node.getLeft().getKey()) {
                node.left = leftRotation(node.getLeft());
                return rightRotation(node);
            }
        }
        if(balanceFactor < -1) {
            if(newKey > node.getRight().getKey()) {
                return leftRotation(node);
            } else if(newKey < node.getRight().getKey()) {
                node.right = rightRotation(node.getRight());
                return leftRotation(node);
            }
        }
        return node;
    }

    /**
     * This method deletes the node with the specified key from the AVL tree rooted at the given node.
     * After deletion, updates the balance factor of each node and performs necessary rotations to maintain the AVL tree balance.
     * @param node the root node of the current subtree
     * @param keyToRemove the key of the node to be removed from the AVL tree
     * @return the root node of the updated AVL tree.
     */
    @Override
    public AvlTreeNode deleteNodeFromTree(AvlTreeNode node, long keyToRemove) {
        //find the node to be deleted and remove it
        if(node == null) return null;
        if(keyToRemove < node.getKey())
            node.left = deleteNodeFromTree(node.getLeft(), keyToRemove);
        else if(keyToRemove > node.getKey())
            node.right = deleteNodeFromTree(node.getRight(), keyToRemove);
        else {
            if((node.getLeft() == null) || (node.getRight() == null)) {
                AvlTreeNode temp = null;
                if(temp == node.getLeft()) temp = node.getRight();
                else temp = node.getLeft();
                node = temp;
            } else {
                AvlTreeNode temp = nodeWithMinimumValue(node.getRight());
                node.key = temp.getKey();
                node.right = deleteNodeFromTree(node.getRight(), temp.getKey());
            }
        }
        if(node == null) return null;

        //update the balance factor of each node, and balance the tree
        node.setHeight(1 + getMax(getHeightOfNode(node.getLeft()), getHeightOfNode(node.getRight())));
        int balanceFactor = getBalanceFactor(node);
        if(balanceFactor > 1) {
            if(getBalanceFactor(node.left) < 0) {
                node.left = leftRotation(node.left);
            }
            return rightRotation(node);
        }
        if(balanceFactor < -1) {
            if(getBalanceFactor(node.right) > 0) {
                node.right = rightRotation(node.right);
            }
            return leftRotation(node);
        }
        return node;
    }

    /**
     * This method counts the number of nodes in the AVL tree rooted at the given node whose keys fall within the specified range [key1, key2].
     * @param node the root node of the current subtree
     * @param key1 the lower bound of the key range
     * @param key2 the upper bound of the key range
     * @return the count of nodes with keys in the specified range.
     */
    @Override
    public int rangeOfKeyInTree(AvlTreeNode node, long key1, long key2) {
        if(node == null) return 0;

        int count = 0;
        if(node.key > key1 && node.key < key2) count += 1; //count the current node
        if(node.key > key1) count += rangeOfKeyInTree(node.getLeft(), key1, key2);
        if(node.key < key2) count += rangeOfKeyInTree(node.getRight(), key1, key2);
        return count;
    }
}
