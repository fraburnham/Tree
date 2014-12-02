import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Frank Burnham on 12/2/14.
 * A simple way to represent trees.
 */
public class Tree<T> {
    public class Node {
        /** Any data the node needs to store. */
        public T data;
        /** This node's root. This value is null for the root node. */
        private Node root = null;
        /** This node's children. Children are added with addChild. */
        private List<Node> children = new ArrayList<Node>();

        /**
         * Default constructor.
         */
        public Node() {}

        /**
         * Construct a node with data.
         * @param data the node's default data
         */
        public Node(T data) {
            this.data = data;
        }

        /**
         * Create a new child node.
         * @param root this child's root node
         */
        public Node(Node root) {
            this.root = root;
        }

        /**
         * Set the root node for this node.
         * @param root the desired root node
         */
        public void setRoot(Node root){
            this.root = root;
        }

        /**
         * Get the root node for this node.
         * @return the root node this node is connected to
         */
        public Node getRoot(){
            return root;
        }

        /**
         * Get the child at the given index.
         * @param child the index of the desired child
         * @return the child Node
         */
        public Node getChild(int child) {
            if(child >= children.size()) {
                /*
                TODO:
                this needs to raise an exception
                which means defining an exception class
                 */
                return null;
            }
            return children.get(child);
        }

        /**
         * Get the number of children to this node.
         * @return number of children
         */
        public int getNumChildren() {
            return children.size();
        }

        /**
         * Add a child to this node.
         * @param child the child Node to add to this node
         */
        public void addChild(Node child) {
            child.setRoot(this);
            children.add(child);
        }
    }

    /** A Tree always needs a root node. */
    public Node rootNode = new Node();

    /**
     * Internal loop algo to visit and compare at every node. Builds a stack
     * as it travels depth first down the tree.
     * @param comp Compare interface passed to perform equality checks
     * @param value Value to pass to comp for equality check
     * @param s the stack that the calling method should place a rootNode onto
     * @return the first node that matches if it exists, null otherwise
     */
    private Node depthFirstSeachLoop(Compare<T> comp, T value, Stack<Node> s) {
        while (!s.empty()) {
            Node n = s.pop();
            //run check, return the matching node if we have a match
            if (comp.equals(value, n.data)) {
                return n;
            }
            //get children
            int numChildren = n.getNumChildren();
            for (int i = 0; i < numChildren; i++) {
                //add children to the s
                s.add(n.getChild(i));
            }
        }
        //visited every node and none of them matched
        return null;
    }

    /**
     * Find a node whose data matches value in this tree.
     * @param comp Compare interface passed to perform equality check
     * @param value Value to search for
     * @return the first node that matches if it exists, null otherwise
     */
    public Node findNode(Compare<T> comp, T value) {
        Stack<Node> s = new Stack<Node>();
        s.add(rootNode);
        return depthFirstSeachLoop(comp, value, s);
    }

    /**
     * Find a node whose data matches value in this tree.
     * @param comp Compare interface passed to perform equality check
     * @param value Value to search for
     * @param rootNode The node to start searching from, depth first
     * @return the first node that matches if it exists, null otherwise
     */
    public Node findNode(Compare<T> comp, T value, Node rootNode) {
        Stack<Node> s = new Stack<Node>();
        s.add(rootNode);
        return depthFirstSeachLoop(comp, value, s);
    }

    public Node newNode(T data) {
        return new Node(data);
    }
    /*
    On a tree we should be able to do things like
    findNode
    getPath - what is a meaningful way to represent a path? maybe
    [rootNode childindex childindex childindex etc] as an array
    maybe some tree creation methods
     */
}
