import java.util.Arrays;

/**
 *BTree class is the 'container' class for the Node class.
 *
 * TODO: Move manipulation code into Node
 *
 * @author Ryan Bailey
 */
public class BTree {

    /**
     * Node class is the manipulator class for the nodes
     * stored in BTree.
     *
     * @author Ryan Bailey
     */
    private class Node{

        protected int numOfKeys, kidCount;
        protected final long[] key, freq;
        protected final Node parent;
        protected final Node[] child;
        protected boolean isALeaf;

        /**
         *Node constructor
         */
        public Node(){

            kidCount = 0;
            this.parent = null;
            child = new Node[2 * order];
            key = new long[(2 * order) - 1];
            freq = new long[(2 * order) - 1];
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
            //reads contents form specific block on disk
        }
        //		void generateFileOffset(){
        //
        //		}
    }

    /**
     * BTree variables
     */
    private Node root;
    private final int order; //Degree of the BTree

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
    }

    /**
     * This function searches through the BTree for a certain sequence value.
     * It returns a node if the value is found
     *
     * @param node root node of tree
     * @param key key being searched for
     * @return something
     */
    private long search(Node node, long key){

        int i = 0;

        while(i < node.numOfKeys && key > node.key[i]){
            i++;
        }
        if(i < node.numOfKeys && key == node.key[i]){
            return node.key[i];
        }
        else if(node.isALeaf){
            return 0;
        } else {
            //node.read(node.child[i]);
            return search(node.child[i], key);
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

        for(int j = 0; j < order - 1; j++) {
            z.key[j] = y.key[j + order];
        }

        if(!y.isALeaf) {
            for(int j = 0; j < order; j++) {
                z.child[j] = y.child[j + order];
            }
        }

        y.numOfKeys = order - 1;

        for(int j = parent.numOfKeys; j > index; j--) {
            parent.child[j + 1] = parent.child[j];
        }
        parent.child[index + 1] = z;

        for(int j = parent.numOfKeys - 1; j >= index; j--) {
            parent.key[j+1] = parent.key[j];
        }
        parent.key[index] = y.key[order - 1];
        y.key[order - 1] = 0;

        for(int j = 0; j < order; j++)
        {
            y.key[j + order - 1] = 0; //'delete' old values
        }

        parent.numOfKeys++;

        System.out.println("PARENT: " + Arrays.toString(parent.key));
        System.out.println("LEFT: " + Arrays.toString(y.key));
        System.out.println("RIGHT: " + Arrays.toString(z.key));

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

        if(search(node, key) != 0){
            System.out.println("BOO!");
            return;
        }
        int i = node.numOfKeys;

        if (node.isALeaf) {

            while(i >= 1 && key < node.key[i - 1] ) {
                node.key[i] = node.key[i - 1];
                i--;
            }

            node.key[i] = key;
            node.numOfKeys++;
            System.out.println("Inserting Key: " + key);
            if(node.numOfKeys == (2 * order) - 1)
                System.out.println("PARENT BEFORE SPLIT: " + Arrays.toString(node.key));
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
}