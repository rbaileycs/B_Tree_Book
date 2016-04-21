import java.util.Arrays;

public class BTree {

    private class Node{
        //			When a BTreeNode is full, it equals one disk block
        //			Keep three nodes in memory

        //			Frequency and key array max sizes are 2t*1, where t is order.
        //			File offset for children max size is 2t, where t is order.
        //			File offset is where the node begins in memory.

        protected int numOfKeys, kidCount;
        protected long key[], freq[];
        protected Node parent, child[];
        protected boolean isALeaf;

        public Node(Node parent){

            kidCount = 0;
            this.parent = parent;
            key = new long[(2*order)-1];
            child = new Node[2*order];
            freq = new long[2*order-1];
        }

        /**
         * This function returns the ith child of a node
         * @param index
         * @return
         */
        public Node getChild(int index){

            return child[index];
        }

        //How to handle read/writes of nodes. fseek /Random Access File /Seek file option
        void write(){
            //writes out contents to specific block on disk
        }
        void read(){
            //reads contents form specific block on disk
        }
        //		void generateFileOffset(){
        //
        //		}
    }

    /*
     * Beginning of BTree class logic
     */
    private Node root;
    private int order; //Degree of the BTree

    /**
     * Constructor for the BTree
     * @param order
     */
    public BTree(int order){

        this.order = order;
        root = new Node(null);
        root.isALeaf = true;
        root.numOfKeys = 0;
        //root.write();
    }

    /**
     * This function searches through the BTree for a certain sequence value. It returns a node
     * if the value is found
     * @param root
     * @param key
     * @return
     */
    public Node search(Node root, long key){

        int i = 0;

        while(i < root.numOfKeys && key > root.key[i]){
            i++;
        }
        if(i <= root.numOfKeys && key == root.key[i]){
            return root;
        }
        if(root.isALeaf){
            return null;
        }
        else{
            return search(root.getChild(i),key);
        }
    }

    /*
     *insert at a leaf
     *keys are sorted in the nodes
     */
    public void split(Node parent, int index) {

        Node z = new Node(null);//changed from index to order
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

        for(int j = parent.numOfKeys - 1; j > index; j--) {
            parent.key[j + 1] = parent.key[j];
        }
        parent.key[index] = y.key[order - 1];
        y.key[order - 1] = 0;

        for(int j = 0; j < order - 1; j++)
        {
            y.key[j + order] = 0; //'delete' old values
        }

        parent.numOfKeys++;

//        y.write();
//        z.write();
//        parent.write();
    }

    public void insert(BTree t, long key) {

        Node root = t.root;

        if (root.numOfKeys == (2 * order) - 1) {
            Node s = new Node(null);
            t.root = s;
            s.isALeaf = false;
            s.numOfKeys = 0;
            s.child[0] = root; // changed from 1 to 0
            split(s, 0); //changed to 0 from 1
            insertNonFull(s, key);
        }
        else {
            insertNonFull(root, key);
        }
    }

    public void insertNonFull(Node node, long key) {

        int i = node.numOfKeys;

        if (node.isALeaf) {
            while(i >= 1 && key < node.key[i - 1] ) {
                node.key[i] = node.key[i - 1];
                i--;
            }
            node.key[i] = key;
            node.numOfKeys++;
            System.out.println(Arrays.toString(node.key));
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


