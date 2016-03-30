package numeric;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class BigIntTest {
    static Random rand = new Random();

    private String generateRandomNumberString(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }

    @Test
    public void testingBigIntConstructor() {
        String num = "5671948593898598347649861409856";
        BigInt a = new BigInt(num);
        assertEquals("String constructor test: ", num, a.toString());
    }

    @Test
    public void testingBigIntSimpleAddition() {
        String numA = "51367892345673";
        String numB = "12895758928750765";
        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);
        BigInt res = BigInt.add(a, b);

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        BigInteger e = c.add(d);
        assertEquals("Test BigInt simple addition: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntSimpleMinus() {
        String numA = "151367892345673635986958";
        String numB = "92895758928750765985376";
        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);
        BigInt res = BigInt.minus(a, b);

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        BigInteger e = c.subtract(d);
        assertEquals("Test BigInt simple addition: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntSpecialMinus() {
        String numA = "0";
        String numB = "0";
        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);
        BigInt res = BigInt.minus(a, b);
        assertEquals("Test BigInt special minus: ", "0", res.toString());
    }

    @Test
    public void testingBigIntSimpleMultipy() {
        String numA = "582978297682957019475894763297";
        String numB = "59285711509431051601910578167";
        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);
        BigInt res = BigInt.mul(a, b);

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        BigInteger e = c.multiply(d);
        assertEquals("Test BigInt simple multiply: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntSimpleDivision() {
        String numA = "582978297682957019475894763297597580911585021705";
        String numB = "59285711509";
        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);
        BigInt res = BigInt.div(a, b);

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        BigInteger e = c.divide(d);
        assertEquals("Test BigInt simple multiply: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntLargeNumberAddition() {
        String numA = generateRandomNumberString(1000000);
        String numB = generateRandomNumberString(1000000);

        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);

        long time1 = System.currentTimeMillis();
        BigInt res = BigInt.add(a, b);
        long time2 = System.currentTimeMillis();

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        long time3 = System.currentTimeMillis();
        BigInteger e = c.add(d);
        long time4 = System.currentTimeMillis();
        System.out.println("Large addition time diff: " + (time2 - time1) + " " + (time4 - time3));
        assertEquals("Test BigInt large addition: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntLargeNumberMultipy() {
        String numA = generateRandomNumberString(10000);
        String numB = generateRandomNumberString(10000);

        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);

        long time1 = System.currentTimeMillis();
        BigInt res = BigInt.mul(a, b);
        long time2 = System.currentTimeMillis();

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        long time3 = System.currentTimeMillis();
        BigInteger e = c.multiply(d);
        long time4 = System.currentTimeMillis();

        System.out.println("Large multiplication time diff: " + (time2 - time1) + " " + (time4 - time3));
        assertEquals("Test BigInt large multiply: ", e.toString(), res.toString());
    }

    @Test
    public void testingBigIntLargeNumberDivision() {
        String numA = generateRandomNumberString(1000000);
        String numB = generateRandomNumberString(1000);

        BigInt a = new BigInt(numA);
        BigInt b = new BigInt(numB);

        long time1 = System.currentTimeMillis();
        BigInt res = BigInt.div(a, b);
        long time2 = System.currentTimeMillis();

        BigInteger c = new BigInteger(numA);
        BigInteger d = new BigInteger(numB);
        long time3 = System.currentTimeMillis();
        BigInteger e = c.divide(d);
        long time4 = System.currentTimeMillis();

        System.out.println("Large division time diff: " + (time2 - time1) + " " + (time4 - time3));
        assertEquals("Test BigInt large multiply: ", e.toString(), res.toString());
    }
}