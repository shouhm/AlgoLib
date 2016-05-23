package numeric;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shouhm on 16/5/22.
 */
public class FractionTest {
    @Test
    public void testFractionAdd() {
        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(2, 4);
        Fraction c = Fraction.add(a, b);
        assertEquals(c.den, 1);
        assertEquals(c.num, 1);
    }
}