package ttaomae.trees;

import ttaomae.lists.List;

/**
 * A tree where each element has two children.
 *
 * @param <E> the type of elements in the tree
 *
 * @author Todd Taomae
 */
public interface BinaryTree<E> extends Tree<E>
{
    /**
     * Returns a list containing all elements of this tree, in in-order
     * traversal. In in-order traversal, the left subtree is recursively
     * traversed, followed by visiting the root, followed by recursively
     * traversing the right subtree.
     *
     * @return a list containing all elements of this tree, in in-order
     *         traversal
     */
    public List<E> inOrderTraversal();
}
