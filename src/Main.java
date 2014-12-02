import java.util.Stack;

/**
 * Created by Frank Burnham on 12/2/14.
 * Testing the tree representation
 */

class IntegerTree implements ITree<Integer> {
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

public class Main {
    public static void main(String[] args) {
        //do main stuff
        Tree<Integer> tree = new Tree<Integer>();
        IntegerTree it = new IntegerTree();
        tree.rootNode.data = 1;
        tree.rootNode.addChild(tree.newNode(2));
        tree.rootNode.addChild(tree.newNode(3));
        tree.rootNode.getChild(0).addChild(tree.newNode(5));
        tree.rootNode.getChild(1).addChild(tree.newNode(4));
        tree.printTree(it, tree.rootNode);

        Stack<Tree<Integer>.Node> path = tree.pathToRoot(tree.findNode(it, 4, tree.rootNode));
        /*for (Tree<Integer>.Node node : path) {
            System.out.print(it.toString(node) + " ");
        }*/
        while(!path.empty()) {
            System.out.print(it.toString(path.pop()) + " ");
        }
        System.out.println();
        path = tree.getPath(
                tree.findNode(it, 3, tree.rootNode),
                tree.findNode(it, 5, tree.rootNode)
                );
        while(!path.empty()) {
            System.out.print(it.toString(path.pop()) + " ");
        }
    }
}
