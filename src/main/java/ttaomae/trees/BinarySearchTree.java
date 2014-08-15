package ttaomae.trees;

/**
 * A binary tree which has the following properties:
 * <ul>
 * <li>all elements of a left subtree are less than the parent</li>
 * <li>all elements of a right subtree are greater than the parent</li>
 * <li>every subtree is a binary tree</li>
 * <li>there are no duplicate elements</li>
 * <li>does not allow null elements</li>
 * </ul>
 *
 * @param <E> the type of elements in the tree
 * 
 * @author Todd Taomae
 */
public interface BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>
{
    /**
     * Adds the specified element to the tree. Returns true if the tree was
     * modified.
     *
     * @param e the element to be added
     * @return true if the element was successfully added; false if the tree
     *         already contained the element
     */
    public boolean add(E e);

    /**
     * Removes the specified element from the tree.
     *
     * @param e the element to be removed
     * @return true if the element was in the tree
     */
    public boolean remove(E e);

    /**
     * Checks whether or not the tree contains the specified element.
     *
     * @param e the element being searched for
     * @return true if the tree contained the element
     */
    public boolean contains(E e);
}
