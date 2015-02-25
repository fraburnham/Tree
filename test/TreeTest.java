import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

public class TreeTest extends TestCase {
    private class IntegerTree implements ITree<Integer> {
        @Override
        public boolean nodeDataEquals(Integer a, Integer b) {
            return a.equals(b);
        }

        @Override
        public boolean nodeDataGreater(Integer a, Integer b) {
            return a > b;
        }

        @Override
        public String toString(Tree.Node node) {
            if (node == null) {
                return "Root";
            }
            return node.data.toString();
        }
    }

    Tree defaultTree = new Tree();
    IntegerTree defaultITree = new IntegerTree();
    ArrayList<Integer> addedNodes = new ArrayList<Integer>(1000);

    public TreeTest() {
        Random rand = new Random();

        //add parent node and put on List
        defaultTree.rootNode.addChild(defaultTree.newNode(0));
        addedNodes.add(0);

        for (int i = 1; i < 1000; i++) {
            int nodeData = rand.nextInt((1000000 - -1000000) + 1) + -1000000;
            int nodeParent;
            if (addedNodes.size() <= 1) {
                nodeParent = 0;
            } else {
                nodeParent = addedNodes.get(rand.nextInt(addedNodes.size() - 1));
            }
            //find the node and add the child
            defaultTree.findNode(defaultITree, nodeParent, defaultTree.rootNode).addChild(defaultTree.newNode(nodeData));
            addedNodes.add(nodeData);
        }
    }

    //Passing
    /**
     * Test that Tree<T>.newNode sets the node's data properly
     * @throws Exception
     */
    public void testNewNode() throws Exception {
        Tree<Integer> t = new Tree<Integer>();
        for(int i = 0; i < 1000; i++) {
            Random rand = new Random();
            Integer val = rand.nextInt(Integer.MAX_VALUE-1);
            assertEquals(t.newNode(val).getData(), val);
        }
    }

    public void testFindNode() throws Exception {

    }

    //Passing
    /**
     * Test the ability to randomly create and find nodes using a depth first
     * search
     * @throws Exception
     */
    public void testFindNode1() throws Exception {
        Random rand = new Random();

        for(int i = 0; i < 10000; i++) {
            int r = rand.nextInt((1000000 - -1000000) + 1) + -1000000;
            boolean shouldBe = addedNodes.contains(r);
            Tree<Integer>.Node ret =
                    defaultTree.findNode(defaultITree, r, defaultTree.rootNode);
            if (shouldBe) {
                assertEquals(false, (ret == null));
                assertEquals(r, (int)ret.getData());
            } else {
                assert((ret == null));
            }
        }

        //then loop over everything it is supposed to contain and verify
        for (int val : addedNodes) {
            Tree<Integer>.Node ret =
                    defaultTree.findNode(defaultITree, val, defaultTree.rootNode);
            assertEquals(false, (ret == null));
            assertEquals(val, (int)ret.getData());
        }
    }

    public void testPathToRoot() throws Exception {

    }

    public void testGetPath() throws Exception {

    }
}
