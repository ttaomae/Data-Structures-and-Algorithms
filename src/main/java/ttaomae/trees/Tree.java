package ttaomae.trees;

import ttaomae.lists.List;

/**
 * A non-linear collection of elements, such that there is a single element at
 * the top of the tree, known as the root. This element may not exist,
 * representing an empty tree. Each element can have 0 or more children, which
 * are each trees.
 * 
 * @param <E> the type of elements in the tree
 * @author Todd Taomae
 */
public interface Tree<E>
{
    /**
     * Returns a list containing all elements of this tree, in pre-order
     * traversal. In pre-order traversal, the root node is visited first,
     * followed by recursively performing a pre-order traversal on each of its
     * children, starting with the leftmost child and working right.
     *
     * @return a list containing all elements of this tree, in pre-order
     *         traversal
     */
    public List<E> preOrderTraversal();

    /**
     * Returns a list containing all elements of this tree, in post-order
     * traversal. In post-order traversal, the children of the root are
     * recursively traversed in post-order fashion, starting with the leftmost
     * child and working right, followed by visiting the root or starting node.
     *
     * @return a list containing all elements of this tree, in post-order
     *         traversal
     */
    public List<E> postOrderTraversal();

    /**
     * Returns the number of nodes in this tree.
     *
     * @return the number of nodes in this tree
     */
    public int size();
}
