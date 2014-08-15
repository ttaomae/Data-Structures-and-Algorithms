package ttaomae.trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ttaomae.lists.List;

@RunWith(Parameterized.class)
public class BinarySearchTreeTest
{
    private static final int ITERATIONS = 10_000;
    private static final long RANDOM_SEED = 0L;

    @Parameters
    public static Collection<Object[]> data()
    {
        return Arrays
                .asList(new Object[][] { { NonBalancingBinarySearchTree.class } });
    }

    private Class<BinarySearchTree<Integer>> treeClass;
    private BinarySearchTree<Integer> tree;

    public BinarySearchTreeTest(Class<BinarySearchTree<Integer>> treeClass)
    {
        this.treeClass = treeClass;
    }

    @Before
    public void init() throws InstantiationException, IllegalAccessException
    {
        tree = treeClass.newInstance();
    }

    @Test
    public void testAdd_null()
    {
        try {
            tree.add(null);
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testAdd_singleItem()
    {
        assertEquals(0, tree.size());

        assertTrue(tree.add(5));
        assertTrue(tree.contains(new Integer(5)));
        assertFalse(tree.add(5));
    }

    @Test
    public void testAdd_ascendingOrder()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.add(i));
            assertEquals(i + 1, tree.size());
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.contains(new Integer(i)));
            assertFalse(tree.add(new Integer(i)));
            assertEquals(ITERATIONS, tree.size());
        }
    }

    @Test
    public void testAdd_descendingOrder()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.add(ITERATIONS - i));
            assertEquals(i + 1, tree.size());
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.contains(new Integer(ITERATIONS - i)));
            assertFalse(tree.add(new Integer(ITERATIONS - i)));
            assertEquals(ITERATIONS, tree.size());
        }
    }

    @Test
    public void testAdd_randomOrder()
    {
        Integer[] addOrder = getRandomArray(ITERATIONS, RANDOM_SEED);

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.add(addOrder[i]));
            assertEquals(i + 1, tree.size());
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.contains(new Integer(addOrder[i])));
            assertFalse(tree.add(addOrder[i]));
            assertEquals(ITERATIONS, tree.size());
        }
    }

    @Test
    public void testRemove_randomAdd_randomRemove()
    {
        Random rng = new Random(RANDOM_SEED);

        Integer[] addOrder = getRandomArray(ITERATIONS, RANDOM_SEED);

        for (int n = 0; n < ITERATIONS; n++) {
            // perform "inside-out" Fisher-Yates shuffle
            // pick random location
            int i = rng.nextInt(n + 1);
            // if necessary, move whatever is at that location to the end of the
            // sublist
            if (i != n) {
                addOrder[n] = addOrder[i];
            }
            // place next number at random location
            addOrder[i] = n;
        }

        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(addOrder[i]);
        }

        Integer[] removeOrder = getRandomArray(ITERATIONS, RANDOM_SEED + 1);

        // check that remove method actually removes items
        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.remove(new Integer(removeOrder[i])));
            assertEquals(ITERATIONS - 1 - i, tree.size());

            assertFalse(tree.contains(new Integer(removeOrder[i])));
        }

        // check that remove returns false if the number doesn't exist
        for (int i = 0; i < ITERATIONS; i++) {
            assertFalse(tree.remove(new Integer(i)));
            assertEquals(0, tree.size());
        }
    }

    @Test
    public void testRemove_ascendingAdd_ascendingRemove()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(i);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.remove(new Integer(i)));
            assertEquals(ITERATIONS - 1 - i, tree.size());

            assertFalse(tree.contains(new Integer(i)));
        }
    }


    @Test
    public void testRemove_ascendingAdd_descendingRemove()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(i);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.remove(new Integer(ITERATIONS - 1 - i)));
            assertEquals(ITERATIONS - 1 - i, tree.size());

            assertFalse(tree.contains(new Integer(ITERATIONS - i)));
        }
    }

    @Test
    public void testRemove_decendingAdd_descendingRemove()
    {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(ITERATIONS - i);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.remove(new Integer(ITERATIONS - i)));
            assertEquals(ITERATIONS - 1 - i, tree.size());

            assertFalse(tree.contains(new Integer(ITERATIONS - i)));
        }
    }

    @Test
    public void testRemove_decendingAdd_ascendingRemove()
    {

        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(ITERATIONS - i);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(tree.remove(new Integer(i + 1)));
            assertEquals(ITERATIONS - 1 - i, tree.size());

            assertFalse(tree.contains(new Integer(i)));
        }
    }

    @Test
    public void testInOrderTraversal()
    {
        Integer[] addOrder = getRandomArray(ITERATIONS, RANDOM_SEED);
        for (int i = 0; i < ITERATIONS; i++) {
            tree.add(addOrder[i]);
        }

        List<Integer> sortedList = tree.inOrderTraversal();

        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i).compareTo(sortedList.get(i + 1)) < 0);
        }
    }

    /**
     * Returns an array containing the numbers 0 (inclusive) up to n (exclusive)
     * in a random order, using the specified random seed.
     */
    private Integer[] getRandomArray(int n, long seed)
    {
        assert (n > 0);

        Integer[] result = new Integer[n];
        Random rng = new Random(seed);

        for (int i = 0; i < n; i++) {
            // perform "inside-out" Fisher-Yates shuffle
            // pick random location
            int j = rng.nextInt(i + 1);
            // if necessary, move whatever is at that location to the end of the
            // sublist
            if (j != i) {
                result[i] = result[j];
            }
            // place next number at random location
            result[j] = i;
        }

        return result;
    }
}
