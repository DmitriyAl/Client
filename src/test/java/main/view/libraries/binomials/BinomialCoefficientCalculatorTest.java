package main.view.libraries.binomials;

import main.view.libraries.binomials.exceptions.IncorrectCoefficientException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dmitriy Albot
 */
public class BinomialCoefficientCalculatorTest {
    @Test
    public void getBigDecimalCoefficientTest() {
        Assert.assertEquals("1", BinomialCoefficientCalculator.getBigDecimalCoefficient(0, 0).toBigInteger().toString());
        Assert.assertEquals("1", BinomialCoefficientCalculator.getBigDecimalCoefficient(1, 1).toBigInteger().toString());
        Assert.assertEquals("10", BinomialCoefficientCalculator.getBigDecimalCoefficient(5, 3).toBigInteger().toString());
    }

    @Test(expected = IncorrectCoefficientException.class)
    public void getIncorrectBigDecimalCoefficientTest() {
        BinomialCoefficientCalculator.getBigDecimalCoefficient(1,2);
    }

    @Test
    public void getLongCoefficientTest() {
        Assert.assertEquals(1, BinomialCoefficientCalculator.getLongCoefficient(0, 0));
        Assert.assertEquals(1, BinomialCoefficientCalculator.getLongCoefficient(1, 1));
        Assert.assertEquals(10, BinomialCoefficientCalculator.getLongCoefficient(5, 3));
    }

    @Test(expected = IncorrectCoefficientException.class)
    public void getIncorrectLongCoefficientTest() {
        BinomialCoefficientCalculator.getLongCoefficient(1,2);
    }
}
