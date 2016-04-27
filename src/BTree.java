/**
 *BTree class is the 'container' class for the Node class.
 *
 * @author Ryan Bailey
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class BTree {

    /**
     * Node class is the manipulator class for the nodes
     * stored in BTree.
     *
     * @author Ryan Bailey
     */
    private class Node{

        protected int numOfKeys;
        protected final long[] key;
        protected final Node parent;
        protected final Node[] child;
        protected boolean isALeaf;

        /**
         *Node constructor
         */
        public Node(){

            this.parent = null;
            child = new Node[2 * order];
            key = new long[(2 * order) - 1];
        }

        /**
         * This function returns the ith child of a node
         *
         * @param index index for child we want returned
         * @return child at ith index
         */
        public Node getChild(int index){

            return child[index];
        }

        //How to handle read/writes of nodes. fseek /Random Access File /Seek file option
        void write(){

            //writes out contents to specific block on disk
        }

        void read(Node node){

            //reads contents from specific block on disk
        }
    }

    /**
     * BTree variables
     */
    private Node root;
    private final int order; //Degree of the BTree (t)
    protected final long[] freq = new long[(int)Math.pow(31,4)]; //Had to make a huge array. It makes me salty.
    private HashMap<Long, Integer> dups;

    /**
     * Constructor for the BTree
     *
     * @param order order/degree of tree. AKA 't'
     */
    public BTree(int order){

        this.order = order;
        root = new Node();
        root.isALeaf = true;
        root.numOfKeys = 0;
        dups = new HashMap<Long, Integer>();
    }

    /**
     * This function searches through the BTree for a certain sequence value.
     * It returns a node if the value is found
     *
     * @param node root node of tree
     * @param key key being searched for
     * @return something
     */
    private Node search(Node node, long key) {
        int i = 0;
        while (i < node.numOfKeys && key > node.key[i]) {
            i++;
        }
        if (i < node.numOfKeys && key == node.key[i]) {
            return node;
        }
        if (node.isALeaf) {
            return null;

        } else {
            return search(node.getChild(i), key);
        }
    }

    /**
     * This function takes in a node and an index on which to split
     * the node. It creates a new node that will serve as the 'right'
     * child and pushes the median value in the node to be split
     * to it's parent node, and then takes the values from (m/2)+1
     * (where 'm' is the median number's index) and puts them in
     * the right child. The left child is the old root, as set in
     * insert.
     *
     * @param parent parent that will take the median value
     * @param index the index for the split
     */
    private void split(Node parent, int index) {

        Node z = new Node();//changed from index to order
        Node y = parent.getChild(index);

        z.isALeaf = y.isALeaf;
        z.numOfKeys = order - 1;

        /**
         * arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
         *
         * Copies an array from the specified source array, beginning at the specified position,
         * to the specified position of the destination array.
         *
         * This is a native call, which reduces runtime. Why? It copies blocks of memory instead
         * of copying single array elements.
         */
        System.arraycopy(y.key, order, z.key, 0, order - 1);

        if(!y.isALeaf) {
            System.arraycopy(y.child, order, z.child, 0, order);
        }

        y.numOfKeys = order - 1;

        System.arraycopy(parent.child, index + 1, parent.child, index + 1 + 1, parent.numOfKeys - index);

        parent.child[index + 1] = z;

        System.arraycopy(parent.key, index, parent.key, index + 1, parent.numOfKeys - index);

        parent.key[index] = y.key[order - 1];
        y.key[order - 1] = 0;

        for(int j = 0; j < order; j++)
        {
            y.key[j + order - 1] = 0; //'delete' old values
        }

        parent.numOfKeys++;

//        y.write();
//        z.write();
//        parent.write();
    }

    /**
     * Insert determines if a node being inserted into
     * is full or not. If the node (in this case being root)
     * is full, split. If not, insert into the node.
     *
     * @param t the tree containing the nodes to insert into
     * @param key the key being inserted
     */
    public void insert(BTree t, long key) {

        Node root = t.root;

        if (root.numOfKeys == (2 * order) - 1) {
            Node s = new Node();
            t.root = s;
            s.isALeaf = false;
            s.numOfKeys = 0;
            s.child[0] = root;
            split(s, 0);
            insertNonFull(s, key);
        }
        else {
            insertNonFull(root, key);
        }
//        QueryGenius(key);
    }

    /**
     * InsertNonFull inserts the key into the node. It finds the proper
     * spot and adjusts the keys in the node (if any) accordingly. If the
     * node being inserted into is full, split.
     *
     * @param node node being inserted into
     * @param key key being inserted
     */
    private void insertNonFull(Node node, long key) {
        //Returns the value of the index where a key value's
        //frequency is updated.
        long insertKey = hashFunc(key);
        /**
         * Checks for duplicates
         * Uses linear probing
         */
        if(search(root, key) != null){
            dups.put(key, (int)++freq[(int)insertKey]);
            return;
        }

        int i = node.numOfKeys;

        if (node.isALeaf) {

            while(i >= 1 && key < node.key[i -1] ) {
                node.key[i] = node.key[i - 1];
                i--;
            }
            dups.put(key, (int)++freq[(int)insertKey]);
            node.key[i] = key;
            node.numOfKeys++;
            // x.write();
        } else {

            int j = 0;

            while(j < node.numOfKeys && key > node.key[j]){
                j++;
            }
            if(node.child[j].numOfKeys == (order * 2) - 1){
                split(node, j);
                if(key > node.key[j]){
                    j++;
                }
            }
            insertNonFull(node.child[j],key);
        }
    }

    /**
     * This function contains the hash function needed to increase
     * the frequency count for keys that are duplicates.
     * @param key key to be inserted
     * @return hash value for key
     */
    private long hashFunc(long key)
    {
        return (((key % freq.length) + key) % freq.length);
    }

//    protected void QueryGenius(long key){
//
//        long keyVal = hashFunc(key);
//
//            System.out.println(GeneConverter.toString(key) + ": " + freq[(int)keyVal]);
//
//    }

    /**
     * Print function for the dump printer.
     */
    public void printIt() {
        ArrayList<Long> keyList = new ArrayList<Long>(dups.keySet());
        Collections.sort(keyList);
        for(long key : keyList) {
            System.out.println(dups.get(key) + " : " + GeneConverter.toString(key));
        }
    }
}