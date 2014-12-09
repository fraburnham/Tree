import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Frank Burnham on 12/2/14.
 * A simple way to represent trees.
 */
public class Tree<T> {
    /**
     * Tree.Node is a class that describes a node (or leaf) of the tree.
     */
    public class Node {
        /** Any data the node needs to store. */
        public T data;
        /** This node's root (parent). This value is null for the root node. */
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
         * Get this node's data.
         * @return the data held by this node
         */
        public T getData() {
            return data;
        }

        /**
         * Set this node's data.
         * @param newData the new data for the node
         */
        public void setData(T newData) {
            data = newData;
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

        /**
         * Remove a child from this node.
         * @param childIndex the child's index in List<Node> children
         */
        public void removeChild(int childIndex) {
            children.remove(childIndex);
        }
    }

    /** A Tree always needs a root node. */
    public Node rootNode = new Node();

    /**
     * Create a new Node to add to the tree, or replace an existing node.
     * @param data the node's default data
     * @return the created node
     */
    public Node newNode(T data) {
        return new Node(data);
    }

    /**
     * Push the children of node n onto stack s
     * @param s the existing stack
     * @param n the node to use
     * @return the new stack with the node's children in the front
     */
    private Stack<Node> pushChildren(Stack<Node> s, Node n) {
        int numChildren = n.getNumChildren();
        for (int i = --numChildren; i >= 0; i--) {
            //add children to the s
            s.push(n.getChild(i));
        }
        return s;
    }

    /**
     * Internal loop algo to visit and compare at every node. Builds a stack
     * as it travels depth first down the tree.
     * @param comp ITree interface passed to perform equality checks
     * @param value Value to pass to comp for equality check
     * @param s the stack that the calling method should place a rootNode onto
     * @return the first node that matches if it exists, null otherwise
     */
    private Node depthFirstSearchLoop(ITree<T> comp, T value, Stack<Node> s) {
        while (!s.empty()) {
            Node n = s.pop();
            //run check, return the matching node if we have a match
            if (comp.nodeDataEquals(value, n.data)) {
                return n;
            }
            //get children
            s = pushChildren(s, n);
        }
        //visited every node and none of them matched
        return null;
    }

    /**
     * Internal loop to search the tree limited by depth from the node(s) on s.
     * @param comp ITree interface passed to perform equality check
     * @param value Value to pass to comp for equality check
     * @param s the stack that the calling method should place a rootNode onto
     * @param depth the maximum vertical distance from the rootNode
     * @return the first node that matches if it exists, null otherwise
     */
    private Node depthLimitedSearchLoop(ITree<T> comp, T value, Stack<Node> s, int depth) {
        //push all the children that are within depth onto the stack
        for (int x = 0; x <= depth; x++) {
            Stack<Node> nodes = new Stack<Node>();
            int len = s.size();

            for (int i = 0; i < len; i++) {
                nodes.push(s.pop());

                if (comp.nodeDataEquals(value, nodes.peek().data)) {
                    return nodes.pop();
                }
            }

            while (!nodes.empty()) {
                s = pushChildren(s, nodes.pop());
            }
        }
        //visited every node and none of them matched
        return null;
    }

    /**
     * Find a node whose data matches value in this tree, limited by depth.
     * @param comp ITree interface passed to perform equality check
     * @param value Value to search for
     * @param rootNode Node to start searching from
     * @param depth Maximum vertical distance from the rootNode
     * @return the first node that matches if it exists, null otherwise
     */
    public Node findNode(ITree<T> comp, T value, Node rootNode, int depth) {
        Stack<Node> s = new Stack<Node>();
        s.push(rootNode);
        return depthLimitedSearchLoop(comp, value, s, depth);
    }

    /**
     * Find a node whose data matches value in this tree.
     * @param comp ITree interface passed to perform equality check
     * @param value Value to search for
     * @param rootNode The node to start searching from, depth first
     * @return the first node that matches if it exists, null otherwise
     */
    public Node findNode(ITree<T> comp, T value, Node rootNode) {
        Stack<Node> s = new Stack<Node>();
        s.push(rootNode);
        return depthFirstSearchLoop(comp, value, s);
    }

    /**
     * Find the path from Node n to the rootNode. A rootNode is a node whose
     * rootNode == null.
     * @param n the node to start from
     * @return a stack of nodes from root to n
     */
    public Stack<Node> pathToRoot(Node n) {
        Stack<Node> path = new Stack<Node>();
        path.push(n); //push self onto stack

        while(n.getRoot() != null) {
            n = n.getRoot();
            path.push(n);
        }

        return path;
    }

    /**
     * Find the shortest path between any two nodes on the same tree.
     * @param a starting node
     * @param b ending node
     * @return a stack of nodes with a as the start and b as the end
     */
    public Stack<Node> getPath(Node a, Node b) {
        Stack<Node> pathA = pathToRoot(a);
        Stack<Node> pathB = pathToRoot(b);

        //might be able to iterate over this...
        while (!pathA.empty() && !pathB.empty()) {
            pathA.pop();
            Node tb = pathB.pop();
            if (pathA.peek() != pathB.peek()) { //found the first node that is the diff in both paths to root
                pathB.push(tb);
                while(!pathA.empty()) { //pop off pathA and push onto pathB until pathA is empty
                    pathB.push(pathA.pop());
                }
            }
        }

        return pathB;
    }

    /**
     * Print a tree (or a sub-tree). Each node is passed to ITree.print().
     * @param rootNode Node to start printing from
     */
    public void printTree(ITree<T> it, Node rootNode) {
        Stack<Node> s = new Stack<Node>();
        s.push(rootNode);

        while (!s.empty()) {
            //print this node
            Node n = s.pop();
            System.out.println(it.toString(n.getRoot()) + ": " + it.toString(n));
            //push children on the stack
            s = pushChildren(s, n);
        }
    }
}
