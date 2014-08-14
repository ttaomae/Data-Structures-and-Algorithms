package ttaomae.lists;

import java.util.Arrays;

public class ArrayList<E> implements List<E>
{
    private E[] data;
    private int size;

    /**
     * Constructs an empty ArrayList.
     */
    @SuppressWarnings("unchecked")
    public ArrayList()
    {
        this.data = (E[]) (new Object[16]);
        this.size = 0;
    }

    @Override
    public void add(E e)
    {
        if (checkCapacity()) {
            increaseCapacity();
        }

        this.data[this.size] = e;
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

        checkIndex(index);

        if (checkCapacity()) {
            increaseCapacity();
        }

        assert (index < this.size);

        int shiftLength = this.size - index;
        if (shiftLength > 0) {
            System.arraycopy(this.data, index, this.data, index+1, shiftLength);
        }

        this.data[index] = e;
        this.size++;
    }

    @Override
    public E get(int index)
    {
        checkIndex(index);

        return this.data[index];
    }

    @Override
    public E remove(int index)
    {
        checkIndex(index);

        E result = this.data[index];

        assert (index < this.size);

        // only shift data if necessary
        int shiftLength = this.size - index - 1;
        if (shiftLength > 0) {
            System.arraycopy(this.data, index + 1, this.data, index, shiftLength);
        }

        this.size--;
        return result;
    }

    @Override
    public boolean remove(E e)
    {
        for (int i = 0; i < this.size; i++) {
            if (e == null ? this.data[i] == null : e.equals(this.data[i])) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public void set(int index, E e)
    {
        checkIndex(index);

        this.data[index] = e;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    /**
     * Checks if the underlying array is full.
     * 
     * @return true if the size of this list is equal to the size of the
     *         underlying array
     */
    private boolean checkCapacity()
    {
        return this.size == this.data.length;
    }

    /**
     * Doubles the capacity of the underlying array.
     */
    private void increaseCapacity()
    {
        this.data = Arrays.copyOf(this.data, this.data.length * 2);
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
}
