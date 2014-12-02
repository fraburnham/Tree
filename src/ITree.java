/**
 * Created by Frank Burnham on 12/2/14.
 * Interface for making generic comparisons on Tree.data
 */
public interface ITree<T> {
    /**
     * Method to check equality of two objects. The order of the objects
     * should be considered arbitrary.
     * @param a the first object to compare
     * @param b the second object to compare
     * @return true when equal, false otherwise
     */
    public boolean nodeDataEquals(T a, T b);

    /**
     * Method to check which object is "greater". If the object is a string
     * this can be used to sort alphabetically.
     * @param a the first object to compare
     * @param b the second object to compare
     * @return true if a > b, false otherwise
     */
    public boolean nodeDataGreater(T a, T b);

    /**
     * Method to give a meaningful string representation to the data in the
     * given node. If node is null the string should be "Root" or whatever is
     * appropriate.
     * @param node node to print data from
     * @return the string representation of the node's data
     */
    public String toString(Tree.Node node);
}
