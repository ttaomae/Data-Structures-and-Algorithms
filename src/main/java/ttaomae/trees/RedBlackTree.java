package ttaomae.trees;

import ttaomae.lists.ArrayList;
import ttaomae.lists.List;


public class RedBlackTree<E extends Comparable<E>>
        implements BinarySearchTree<E>
{
    private final Node nil;

    private Node root;
    private int size;

    public RedBlackTree()
    {
        this.nil = new Node(null);
        // when the Node constructor is called, nil is null so we must
        // manually set the instance variables after it has been initialized
        this.nil.parent = this.nil;
        this.nil.left = this.nil;
        this.nil.right = this.nil;
        this.nil.color = Color.BLACK;

        this.root = this.nil;
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
        if (node == this.nil) {
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
        if (node == this.nil) {
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
        if (node == this.nil) {
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

        Node parent = this.nil;
        Node node = root;
        // find the location to add the new item
        while (node != this.nil) {
            parent = node;
            // element already exist in tree
            if (e.compareTo(node.data) == 0) {
                return false;
            }

            if (e.compareTo(node.data) < 0) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }

        newNode.parent = parent;
        if (parent == this.nil) {
            this.root = newNode;
        }
        else if (newNode.data.compareTo(parent.data) < 0) {
            parent.left = newNode;
        }
        else {
            parent.right = newNode;
        }

        addFix(newNode);
        this.size++;
        return true;
    }

    /**
     * Fixes this tree to maintain the red-black properties after an item was
     * added.
     *
     * @param node the node to begin the fix
     */
    private void addFix(Node node)
    {
        while (node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                }
                else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            }
            else if (node.parent == node.parent.parent.right) {
                Node uncle = node.parent.parent.left;
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                }
                else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }
        }

        this.root.color = Color.BLACK;
    }

    /**
     * Performs a left rotate on this tree using the specified node as the
     * parent of the pivot point.
     */
    private void leftRotate(Node pivotX)
    {
        assert (pivotX.right != this.nil);

        Node pivotY = pivotX.right;
        pivotX.right = pivotY.left;
        if (pivotY.left != this.nil) {
            pivotY.left.parent = pivotX;
        }
        pivotY.parent = pivotX.parent;
        if (pivotX.parent == this.nil) {
            this.root = pivotY;
        }
        else if (pivotX == pivotX.parent.left) {
            pivotX.parent.left = pivotY;
        }
        else {
            pivotX.parent.right = pivotY;
        }

        pivotY.left = pivotX;
        pivotX.parent = pivotY;
    }

    /**
     * Performs a right rotate on this tree using the specified node as the
     * parent of the pivot point.
     */
    private void rightRotate(Node pivotX)
    {
        assert (pivotX.left != this.nil);

        Node pivotY = pivotX.left;
        pivotX.left = pivotY.right;
        if (pivotY.right != this.nil) {
            pivotY.right.parent = pivotX;
        }

        pivotY.parent = pivotX.parent;

        if (pivotX.parent == this.nil) {
            this.root = pivotY;
        }
        else if (pivotX == pivotX.parent.left) {
            pivotX.parent.left = pivotY;
        }
        else {
            pivotX.parent.right = pivotY;
        }

        pivotY.right = pivotX;
        pivotX.parent = pivotY;
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

    /**
     * Removes the specified node from this tree.
     *
     * @param node the node being removed
     */
    private void remove(Node node)
    {
        Node y = node;
        Node x;
        Color yOrignalColor = y.color;

        if (node.left == this.nil) {
            x = node.right;
            node.replaceSelf(node.right);
        }
        else if (node.right == this.nil) {
            x = node.left;
            node.replaceSelf(node.left);
        }
        else {
            // find minimum of subtree starting at 'node'
            y = node.right;
            while (y.left != this.nil) {
                y = y.left;
            }

            yOrignalColor = y.color;
            x = y.right;
            if (y.parent == node) {
                x.parent = y;
            }
            else {
                y.replaceSelf(y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            node.replaceSelf(y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }

        if (yOrignalColor == Color.BLACK) {
            removeFix(x);
        }
    }

    /**
     * Fixes this tree to maintain the red-black properties after a node was
     * removed.
     * 
     * @param node the node to begin the fix
     */
    private void removeFix(Node node)
    {
        while (node != this.root && node.color == Color.BLACK) {
            if (node == node.parent.left) {
                Node w = node.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    leftRotate(node.parent);
                    w = node.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    node = node.parent;
                }
                else {
                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = node.parent.right;
                    }
                    w.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(node.parent);
                    node = this.root;
                }
            }
            else if (node == node.parent.right) {
                Node w = node.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    rightRotate(node.parent);
                    w = node.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    node = node.parent;
                }
                else {
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        leftRotate(w);
                        w = node.parent.left;
                    }
                    w.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(node.parent);
                    node = this.root;
                }
            }
        }

        node.color = Color.BLACK;
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

        while (node != this.nil) {
            if (e.compareTo(node.data) < 0) {
                node = node.left;
            }
            else if (e.compareTo(node.data) > 0) {
                node = node.right;
            }
            else {
                return node;
            }
        }

        return null;
    }

    private class Node
    {
        E data;
        Node parent;
        Node left;
        Node right;
        Color color;

        public Node(E data)
        {
            this.data = data;
            this.parent = RedBlackTree.this.nil;
            this.left = RedBlackTree.this.nil;
            this.right = RedBlackTree.this.nil;
            this.color = Color.RED;
        }

        public void replaceSelf(Node replacement)
        {
            if (this.parent == RedBlackTree.this.nil) {
                RedBlackTree.this.root = replacement;
            }
            else if (this.parent.left == this) {
                this.parent.left = replacement;
            }
            else if (this.parent.right == this) {
                this.parent.right = replacement;
            }

            replacement.parent = this.parent;
        }
    }

    private static enum Color
    {
        RED, BLACK;
    }
}
