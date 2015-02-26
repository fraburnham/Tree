import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

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

    Tree<Integer> defaultTree = new Tree<Integer>();
    IntegerTree defaultITree = new IntegerTree();
    ArrayList<Integer> addedNodes = new ArrayList<Integer>(1000);

    /**
     * Default constructor builds the default tree.
     */
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

    /**
     * Generate a dummy tree with a known format
     * @return root node of dummy tree
     */
    private Tree<Integer>.Node formattedDummyTree() {
        //Gross verbose
        Tree<Integer>.Node r = defaultTree.rootNode;
        r.setData(50);
        Tree<Integer>.Node a = r.addChild(defaultTree.newNode(7));
        Tree<Integer>.Node b = r.addChild(defaultTree.newNode(8));
        Tree<Integer>.Node c = r.addChild(defaultTree.newNode(13));
        Tree<Integer>.Node aa = a.addChild(defaultTree.newNode(37));
        Tree<Integer>.Node ab = a.addChild(defaultTree.newNode(26));
        Tree<Integer>.Node ac = a.addChild(defaultTree.newNode(100));
        Tree<Integer>.Node ba = b.addChild(defaultTree.newNode(29));
        Tree<Integer>.Node bb = b.addChild(defaultTree.newNode(70));
        Tree<Integer>.Node ca = c.addChild(defaultTree.newNode(6));
        Tree<Integer>.Node cb = c.addChild(defaultTree.newNode(17));
        Tree<Integer>.Node baa = ba.addChild(defaultTree.newNode(2));
        Tree<Integer>.Node baaa = baa.addChild(defaultTree.newNode(525));
        Tree<Integer>.Node baab = baa.addChild(defaultTree.newNode(78));
        Tree<Integer>.Node caa = ca.addChild(defaultTree.newNode(476));
        Tree<Integer>.Node cba = cb.addChild(defaultTree.newNode(28));

        return r;
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
            Integer val = rand.nextInt(Integer.MAX_VALUE);
            assertEquals(t.newNode(val).getData(), val);
        }
    }

    //Passing
    /**
     * Test that depth restricted findNode returns null when there is no
     * match and returns the correct node when there is a match.
     * @throws Exception
     */
    public void testFindNode() throws Exception {
        //use the formattedDummyTree() to search by depth
        Tree<Integer>.Node r = formattedDummyTree();

        int[] tests = {50, 7, 8, 13, 37, 26, 100, 29,
                70, 6, 17, 2, 476, 28, 525, 78};

        int[] depths = {0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4};

        for (int i = 0; i < tests.length; i++) {
            for (int j = 0; j <= depths[i]; j++) {
                if (depths[i] == j) {
                    assertEquals(tests[i],
                            (int)defaultTree.findNode(
                                    defaultITree, tests[i], r, j).getData());
                } else {
                    assertEquals(null, defaultTree.findNode(
                            defaultITree, tests[i], r, j));
                }
            }
        }
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
            Tree<Integer>.Node ret =
                    defaultTree.findNode(defaultITree, r, defaultTree.rootNode);
            if (addedNodes.contains(r)) {
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

    //Passing
    /**
     * Expected behavior of passing a null value to be found will vary based
     * on the ITree provided by the caller. In IntegerTree it is expected to
     * throw a NullPointerException.
     * @throws Exception
     */
    public void testFindNode1NullHandling() throws Exception {
        try {
            defaultTree.findNode(defaultITree, null, defaultTree.rootNode);
            fail();
        } catch (NullPointerException e) {
            //pass
        }

    }

    //Passing
    /**
     * Test path to root against a dummy tree.
     * @throws Exception
     */
    public void testPathToRoot() throws Exception {
        Tree<Integer>.Node r = formattedDummyTree();

        int[] tests = {37, 26, 100, 7, 525, 78, 2, 29,
                8, 70, 476, 6, 13, 28, 17, 50};

        int[][] answers = {{50, 7, 37}, {50, 7, 26}, {50, 7, 100}, {50, 7},
                {50, 8, 29, 2, 525}, {50, 8, 29, 2, 78}, {50, 8, 29, 2},
                {50, 8, 29}, {50, 8}, {50, 8, 70}, {50, 13, 6, 476},
                {50, 13, 6}, {50, 13}, {50, 13, 17, 28}, {50, 13, 17}, {50}};

        for(int i = 0; i < tests.length; i++) {
            Stack<Tree<Integer>.Node> s =
                    defaultTree.pathToRoot(defaultTree.findNode(defaultITree, tests[i], r));
            for(int j = 0; j < answers[i].length; j++) {
                assertEquals(answers[i][j], (int)s.pop().getData());
            }
        }
    }

    //Passing
    /**
     * Test that getPath returns the correct path for a known tree.
     * @throws Exception
     */
    public void testGetPath() throws Exception {
        Tree<Integer>.Node r = formattedDummyTree();

        int[][] tests = {{37, 70}, {525, 476}, {100, 28}, {26, 78}};

        int[][] paths = {{37, 7, 50, 8, 70}, {525, 2, 29, 8, 50, 13, 6, 476},
                {100, 7, 50, 13, 17, 28}, {26, 7, 50, 8, 29, 2, 78}};

        for (int i = 0; i < tests.length; i++) {
            Stack<Tree<Integer>.Node> s = defaultTree.getPath(
                    defaultTree.findNode(defaultITree, tests[i][0], r),
                    defaultTree.findNode(defaultITree, tests[i][1], r));

            for (int val : paths[i]) {
                assertEquals(val, (int)s.pop().getData());
            }
        }
    }
}
