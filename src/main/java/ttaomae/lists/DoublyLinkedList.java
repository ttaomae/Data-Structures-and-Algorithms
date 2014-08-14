package ttaomae.lists;



public class DoublyLinkedList<E> implements List<E>
{
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(E e)
    {
        Node newNode = new Node(e);

        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }

        this.size++;
    }

    @Override
    public void add(int index, E e)
    {
        // add to empty list or to the end of the list
        if (this.size == 0 || index == this.size) {
            add(e);
            return;
        }

        Node newNode = new Node(e);

        if (index == 0) {
            this.head.prev = newNode;
            newNode.next = this.head;
            this.head = newNode;
        }
        else {
            newNode.next = getNode(index);
            newNode.prev = newNode.next.prev;
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
        }
        size++;
    }

    @Override
    public E get(int index)
    {
        return getNode(index).data;
    }

    @Override
    public E remove(int index)
    {
        Node node = getNode(index);

        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size = 0;
            return node.data;
        }

        // at least two elements
        if (index == 0) {
            this.head = node.next;
        }
        else {
            node.prev.next = node.next;
        }

        if (index == this.size - 1) {
            this.tail = this.tail.prev;
        }
        else {
            node.next.prev = node.prev;
        }

        this.size--;
        return node.data;
    }

    @Override
    public boolean remove(E e)
    {
        Node node = this.head;
        for (int i = 0; i < this.size; i++) {
            if (e == null ? node.data == null : e.equals(node.data)) {
                // remove head;
                if (i == 0) {
                    this.head = node.next;
                }
                else {
                    node.prev.next = node.next;
                }
                // remove tail
                if (i == this.size - 1) {
                    this.tail = this.tail.prev;
                }
                else {
                    node.next.prev = node.prev;
                }

                this.size--;
                return true;
            }

            node = node.next;
        }

        return false;
    }

    @Override
    public void set(int index, E e)
    {
        getNode(index).data = e;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    /**
     * Checks that the specified index is valid
     *
     * @param index the index being checked
     */
    private void checkIndex(int index)
    {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    /**
     * Returns the node at the specified index.
     *
     * @param index the index of the node to be returned
     * @return the node at the specified index
     */
    private Node getNode(int index)
    {
        checkIndex(index);

        assert (index < this.size);

        Node node;
        // start from front
        if (index < this.size / 2) {
            node = this.head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        // start from end
        else {
            node = this.tail;
            for (int i = this.size - 1; i > index; i--) {
                node = node.prev;
            }
        }

        return node;
    }

    private class Node
    {
        Node next;
        Node prev;
        E data;

        public Node(E data)
        {
            this.data = data;
        }
    }
}
