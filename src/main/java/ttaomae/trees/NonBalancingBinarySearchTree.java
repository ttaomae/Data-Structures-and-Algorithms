package ttaomae.trees;

import ttaomae.lists.ArrayList;
import ttaomae.lists.List;

public class NonBalancingBinarySearchTree<E extends Comparable<E>>
        implements BinarySearchTree<E>
{
    private Node root;
    private int size;

    public NonBalancingBinarySearchTree()
    {
        this.root = null;
        this.size = 0;
    }

    @Override
    public List<E> inOrderTraversal()
    {
        List<E> result = new ArrayList<>();

        inOrderTraversal(result, this.root);

        return result;
    }

    private void inOrderTraversal(List<E> result, Node node)
    {
        if (node == null) {
            return;
        } else {
            inOrderTraversal(result, node.left);
            result.add(node.data);
            inOrderTraversal(result, node.right);
        }
    }

    @Override
    public List<E> preOrderTraversal()
    {
        List<E> result = new ArrayList<>();

        preOrderTraversal(result, this.root);

        return result;
    }

    private void preOrderTraversal(List<E> result, Node node)
    {
        if (node == null) {
            return;
        }
        else {
            result.add(node.data);
            preOrderTraversal(result, node.left);
            preOrderTraversal(result, node.right);
        }
    }

    @Override
    public List<E> postOrderTraversal()
    {
        List<E> result = new ArrayList<>();

        postOrderTraversal(result, this.root);

        return result;
    }

    private void postOrderTraversal(List<E> result, Node node)
    {
        if (node == null) {
            return;
        }
        else {
            postOrderTraversal(result, node.left);
            postOrderTraversal(result, node.right);
            result.add(node.data);
        }
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean add(E e)
    {
        if (e == null) {
            throw new IllegalArgumentException("Cannot add null element");
        }

        Node newNode = new Node(e);

        if (this.root == null) {
            this.root = newNode;
            this.size++;
            return true;
        }

        Node node = root;
        while(true) {
            // element already exists in tree
            if (e.compareTo(node.data) == 0) {
                return false;
            }

            // go left
            if (e.compareTo(node.data) < 0) {
                // add if at end of tree
                if (node.left == null) {
                    node.left = newNode;
                    newNode.parent = node;
                    break;
                }

                node = node.left;
            }

            // go right
            if (e.compareTo(node.data) > 0) {
                // add if at end of tree
                if (node.right == null) {
                    node.right = newNode;
                    newNode.parent = node;
                    break;
                }

                node = node.right;
            }
        }

        this.size++;
        return true;
    }

    @Override
    public boolean remove(E e)
    {
        Node removeNode = findNode(e);

        if (removeNode == null) {
            return false;
        }

        remove(removeNode);
        this.size--;
        return true;
    }

    private void remove(Node node)
    {
        if (node.isLeaf()) {
            node.replaceSelf(null);
        }
        // since we know it is not a leaf, it must have at least one child
        // so if either child is null, the other is the only child
        // replace node with its child
        else if (node.left == null) {
            node.replaceSelf(node.right);
        }
        else if (node.right == null) {
            node.replaceSelf(node.left);
        }
        else {
            Node swapNode = node.left;
            while (swapNode.right != null) {
                swapNode = swapNode.right;
            }
            // swap data
            node.data = swapNode.data;
            // remove the swapped node
            remove(swapNode);
        }
    }

    @Override
    public boolean contains(E e)
    {
        return findNode(e) != null;
    }

    private Node findNode(E e)
    {
        if (e == null) {
            return null;
        }

        Node node = this.root;

        while (node != null) {
            if (e.compareTo(node.data) < 0) {
                node = node.left;
            }
            else if (e.compareTo(node.data) > 0) {
                node = node.right;
            }
            // found element
            else {
                return node;
            }
        }

        return null;
    }

    private class Node
    {
        E data;
        Node left;
        Node right;
        Node parent;

        public Node(E data)
        {
            this.data = data;
        }

        public boolean isLeaf()
        {
            return left == null && right == null;
        }

        public void replaceSelf(Node replacement)
        {
            if (this == NonBalancingBinarySearchTree.this.root) {
                NonBalancingBinarySearchTree.this.root = replacement;
            }
            else {
                if (this.parent.left == this) {
                    this.parent.left = replacement;
                }
                else if (this.parent.right == this) {
                    this.parent.right = replacement;
                }
            }
            if (replacement != null) {
                replacement.parent = this.parent;
            }
        }
    }
}
