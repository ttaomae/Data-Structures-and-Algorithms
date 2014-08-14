package ttaomae.lists;

/**
 * An ordered collection of elements.
 *
 * @param <E> type of elements in the list
 *
 * @author Todd Taomae
 */
public interface List<E>
{
    /**
     * Adds the specified element to the end of this list.
     *
     * @param e the element being added to the list
     */
    public void add(E e);

    /**
     * Adds the specified element at the specified index of this list. Shifts
     * the existing elements at and above the specified index to the right.
     * 
     * @param index the index at which to add the new element
     * @param e the element being added to the list
     * @throws IndexOutOfBoundsException if the index is not in the range 0 <=
     *             index <= size()
     */
    public void add(int index, E e);

    /**
     * Returns the element at the specified index.
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is not in the range 0 <= index < size()
     */
    public E get(int index);

    /**
     * Removes and returns the element at the specified index.
     * @param index the index of the element to remove
     * @return the element being removed
     * @throws IndexOutOfBoundsException if the index is not in the range 0 <= index < size()
     */
    public E remove(int index);

    /**
     * Removes the first element in the list that is equal to the specified
     * element. Returns true if such an element existed in the list.
     *
     * @param e the element to be removed
     * @return true if the element was in the list
     */
    public boolean remove(E e);

    /**
     * Sets the element at the specified index.
     *
     * @param index the index of the element being set
     * @param e the new value of the element at the specified index
     * @throws IndexOutOfBoundsException if the index is not in the range 0 <= index < size()
     */
    public void set(int index, E e);

    /**
     * Returns the size of this list.
     *
     * @return the size of this list
     */
    public int size();
}
