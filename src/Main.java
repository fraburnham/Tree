/**
 * Created by Frank Burnham on 12/2/14.
 * Testing the tree representation
 */
public class Main {
    public static void main(String[] args) {
        //do main stuff
        Tree<Integer> tree = new Tree<Integer>();
        tree.rootNode.data = 1;
        tree.rootNode.addChild(tree.newNode(2));
        System.out.println(tree.rootNode.getChild(0).getRoot().data);
    }
}
