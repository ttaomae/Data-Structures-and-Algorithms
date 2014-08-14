package ttaomae.lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ArrayListTest
{
    private static final int ITERATIONS = 10_000;
    private static final long RANDOM_SEED = 0L;
    List<Integer> list;

    @Before
    public void init()
    {
        list = new ArrayList<>();
    }

    @Test
    public void testAdd_singleItem()
    {
        assertEquals(0, list.size());

        list.add(5);
        assertEquals(new Integer(5), list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    public void testAdd_manyItems()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(rng.nextInt());
            assertEquals(i + 1, list.size());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            assertEquals(new Integer(rng.nextInt()), list.get(i));
        }
    }

    @Test
    public void testAddIndex_frontOfList()
    {
        Random rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(0, rng.nextInt());
            assertEquals(i + 1, list.size());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = ITERATIONS - 1; i >= 0; i--) {
            assertEquals(new Integer(rng.nextInt()), list.get(i));
            assertEquals(ITERATIONS, list.size());
        }
    }

    @Test
    public void testAddIndex_endOfList()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(i, rng.nextInt());
            assertEquals(i + 1, list.size());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            assertEquals(new Integer(rng.nextInt()), list.get(i));
        }
    }

    @Test
    public void testAddIndex_randomLocation()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            int index = rng.nextInt(i + 1);
            Integer value = rng.nextInt();
            list.add(index, value);
            assertEquals(value, list.get(index));
            assertEquals(i + 1, list.size());
        }
    }

    @Test
    public void testRemoveIndex_singleItem()
    {
        assertEquals(0, list.size());

        list.add(5);
        assertEquals(1, list.size());

        assertEquals(new Integer(5), list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveObject_singleItem()
    {
        assertEquals(0, list.size());
        assertFalse(list.remove(new Integer(5)));

        list.add(5);
        assertEquals(1, list.size());

        assertTrue(list.remove(new Integer(5)));
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveIndex_frontOfList()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(rng.nextInt());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            assertEquals(new Integer(rng.nextInt()), list.remove(0));
            assertEquals(ITERATIONS - 1 - i, list.size());
        }
    }

    @Test
    public void testRemoveObject_frontOfList()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(rng.nextInt());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(list.remove(new Integer(rng.nextInt())));
            assertEquals(ITERATIONS - 1 - i, list.size());
        }

        // everything should be removed so remove(E) should always return false
        rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            assertFalse(list.remove(new Integer(rng.nextInt())));
            assertEquals(0, list.size());
        }
    }

    @Test
    public void testRemoveIndex_endOfList()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(0, rng.nextInt());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = ITERATIONS - 1; i >= 0; i--) {
            assertEquals(new Integer(rng.nextInt()), list.remove(i));
            assertEquals(i, list.size());
        }
    }

    @Test
    public void testRemoveObject_endOfList()
    {
        Random rng = new Random(RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(0, rng.nextInt());
        }

        rng = new Random(RANDOM_SEED);
        for (int i = ITERATIONS - 1; i >= 0; i--) {
            assertTrue(list.remove(new Integer(rng.nextInt())));
            assertEquals(i, list.size());
        }

        // everything should be removed so remove(E) should always return false
        rng = new Random(RANDOM_SEED);
        for (int i = ITERATIONS - 1; i >= 0; i--) {
            assertFalse(list.remove(new Integer(rng.nextInt())));
            assertEquals(0, list.size());
        }
    }

    @Test
    public void testRemoveIndex_randomLocation()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(new Integer(i));
        }

        Random rng = new Random(RANDOM_SEED);
        for (int i = ITERATIONS - 1; i >= 0; i--) {
            int index = rng.nextInt(i + 1);
            Integer num = list.remove(index);

            // i starts at (size() - 1) but should now be equal to the size
            // we are checking if the index is invalid (i.e. >= size())
            // otherwise we check that there is a different element at the index
            assertTrue(index >= i || !list.get(index).equals(num));
            assertEquals(i, list.size());
        }
    }

    @Test
    public void testRemoveObject_randomLocation()
    {
        Integer[] removeOrder = new Integer[ITERATIONS];
        Random rng = new Random();

        for (int n = 0; n < ITERATIONS; n++) {
            list.add(new Integer(n));

            // perform "inside-out" Fisher-Yates shuffle
            // pick random location
            int i = rng.nextInt(n + 1);
            // if necessary, move whatever is at that location to the end of the sublist
            if (i != n) {
                removeOrder[n] = removeOrder[i];
            }
            // place next number at random location
            removeOrder[i] = n;
        }

        for (int i = 0; i < ITERATIONS; i++) {
            Integer num = removeOrder[i];

            assertTrue(list.remove(num));
            assertEquals(ITERATIONS - 1 - i, list.size());

            assertFalse(list.remove(num));
            assertEquals(ITERATIONS - 1 - i, list.size());
        }
    }

    @Test
    public void testSet()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(0);
        }

        Random rng = new Random(RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            int num = rng.nextInt();
            list.set(i, new Integer(num));

            assertEquals(new Integer(num), list.get(i));
            assertEquals(ITERATIONS, list.size());
        }
    }

    @Test
    public void testGet_invalidIndex()
    {
        try {
            list.get(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {}

        try {
            list.get(0);
            fail();
        } catch (IndexOutOfBoundsException e) {}

    }

    @Test
    public void testRemove_invalidIndex()
    {
        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {}

        try {
            list.remove(0);
            fail();
        } catch (IndexOutOfBoundsException e) {}

        list.add(new Integer(0));
        list.add(new Integer(0));

        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {}

        try {
            list.remove(2);
            fail();
        } catch (IndexOutOfBoundsException e) {}

    }

    @Test
    public void testSet_invalidIndex()
    {
        try {
            list.set(-1, new Integer(0));
            fail();
        } catch (IndexOutOfBoundsException e) {}

        try {
            list.set(0, new Integer(0));
            fail();
        } catch (IndexOutOfBoundsException e) {}

        list.add(new Integer(0));
        list.add(new Integer(0));

        try {
            list.set(-1, new Integer(0));
            fail();
        } catch (IndexOutOfBoundsException e) {}

        try {
            list.set(2, new Integer(0));
            fail();
        } catch (IndexOutOfBoundsException e) {}

    }
}
