/**
 * Created by Frank Burnham on 12/2/14.
 * Interface for making generic comparisons on Tree.data
 */
public interface Compare<T> {
    /**
     * Method to check equality of two objects. The order of the objects
     * should be considered arbitrary.
     * @param a the first object to compare
     * @param b the second object to compare
     * @return true when equal, false otherwise
     */
    public boolean equals(T a, T b);

    /**
     * Method to check which object is "greater". If the object is a string
     * this can be used to sort alphabetically.
     * @param a the first object to compare
     * @param b the second object to compare
     * @return true if a > b, false otherwise
     */
    public boolean greater(T a, T b);
}
