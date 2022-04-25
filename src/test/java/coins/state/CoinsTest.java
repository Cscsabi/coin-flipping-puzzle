package coins.state;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private Coins state1 = new Coins(7, 3); // the original initial state

    private final Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }


    @Test
    void isGoalTest() {
        assertFalse(state1.isGoal());
        assertTrue(state2.isGoal());
    }

    @Test
    void canFlipTest() {
        BitSet bs = new BitSet(6);
        assertFalse(state1.canFlip(bs));
        bs.set(0, 3);
        assertTrue(state1.canFlip(bs));
        assertTrue(state2.canFlip(bs));
        bs = new BitSet(8);
        assertFalse(state1.canFlip(bs));
        assertFalse(state1.canFlip(bs));
        bs = new BitSet(4);
        bs.set(0, 3);
        assertTrue(state1.canFlip(bs));
        assertTrue(state2.canFlip(bs));
        bs = new BitSet(5);
        bs.set(1, 2);
        assertFalse(state1.canFlip(bs));
        assertFalse(state2.canFlip(bs));
    }

    @Test
    void canFlipShouldBeFalseIfLengthIsLowerThanNOrCardinalityIsLowerThanMTest() {
        BitSet bs = new BitSet(10);
        assertFalse(state1.canFlip(bs));
        bs = new BitSet(7);
        bs.set(2);
        assertFalse(state1.canFlip(bs));
    }

    @Test
    void flip() {
        BitSet bs = new BitSet(4);
        bs.set(0, 3);
        state1 = new Coins(7, 3);
        state1.flip(bs);
        assertEquals(bs, state1.getCoins());
        BitSet bs2 = new BitSet(4);
        bs2.set(0, 2);
        state1.flip(bs2);
        BitSet bs3 = new BitSet(4);
        bs3.set(2);
        assertEquals(bs3, state1.getCoins());
    }

    @Test
    void generateFlipsTest() {
        long pieces = CombinatoricsUtils.binomialCoefficient(15, 8);
        List<BitSet> flips = Coins.generateFlips(15, 8);
        assertEquals((int) pieces, flips.size());
        pieces = CombinatoricsUtils.binomialCoefficient(8, 3);
        flips = Coins.generateFlips(8, 3);
        assertEquals((int) pieces, flips.size());
    }

    @Test
    void generateFlipsThrowingExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0, 2));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(2, 0));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(5, 6));
    }

    @Test
    void getFlips() {
        List<BitSet> flips = Coins.generateFlips(7, 3);
        assertEquals(flips.size(), state1.getFlips().size());
    }
}