package ttaomae.lists;

public class SinglyLinkedList<E> implements List<E>
{
    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty LinkedList.
     */
    public SinglyLinkedList()
    {
        this.head = null;
        this.size = 0;
    }

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
            this.tail = newNode;
        }

        this.size++;
    }

    @Override
    public void add(int index, E e)
    {
        // add to the end of the list
        if (index == this.size) {
            add(e);
            return;
        }

        Node newNode = new Node(e);

        if (index == 0) {
            newNode.next = this.head;
            this.head = newNode;
        }
        else {
            Node node = getNode(index - 1);
            newNode.next = node.next;
            node.next = newNode;
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
        checkIndex(index);

        // remove last remaining element
        if (this.size == 1) {
            E result = this.head.data;
            this.head = null;
            this.tail = null;
            this.size = 0;
            return result;
        }

        // at least 2 elements remaining; remove head
        if (index == 0) {
            E result = this.head.data;
            this.head = this.head.next;
            this.size--;
            return result;
        }

        // remove some other element
        Node node = getNode(index - 1);
        E result = node.next.data;

        // update tail if we are
        if (node.next == this.tail) {
            this.tail = node;
        }

        assert (node.next != null);
        node.next = node.next.next;

        this.size--;
        return result;
    }

    @Override
    public boolean remove(E e)
    {
        Node node = this.head;
        Node prev = null;
        for (int i = 0; i < this.size; i++) {
            if (e == null ? node.data == null : e.equals(node.data)) {
                // remove head
                if (i == 0) {
                    this.head = node.next;
                }
                // remove tail
                if (i == this.size - 1) {
                    this.tail = prev;
                }

                // remove node
                if (i != 0) {
                    prev.next = node.next;
                }

                this.size--;
                return true;
            }

            prev = node;
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

        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    private class Node
    {
        E data;
        Node next;

        public Node(E data)
        {
            this.data = data;
        }
    }
}
