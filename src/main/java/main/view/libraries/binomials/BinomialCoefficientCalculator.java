package main.view.libraries.binomials;

import main.view.libraries.binomials.exceptions.IncorrectCoefficientException;

import java.math.BigDecimal;

/**
 * @author Dmitriy Albot
 */
public class BinomialCoefficientCalculator {
    private static BigDecimal[][] bigIntCoefficients;
    private static long[][] longCoefficients;
    private static final int MAX_LONG_COEFFICIENT = 66;
    private static int maxBigDecimalCoefficient;

    static {
        maxBigDecimalCoefficient = 200;
        initBigInt(maxBigDecimalCoefficient);
        initFloat(MAX_LONG_COEFFICIENT);
    }

    public static int getMaxLongCoefficient() {
        return MAX_LONG_COEFFICIENT;
    }

    private static void initBigInt(int maxN) {
        bigIntCoefficients = new BigDecimal[maxN + 1][maxN + 1];
        for (int n = 0; n <= maxN; ++n) {
            bigIntCoefficients[n][0] = bigIntCoefficients[n][n] = new BigDecimal("1");
            for (int k = 1; k < n; ++k)
                bigIntCoefficients[n][k] = bigIntCoefficients[n - 1][k - 1].add(bigIntCoefficients[n - 1][k]);
        }
    }

    private static void initFloat(int maxN) {
        longCoefficients = new long[maxN + 1][maxN + 1];
        for (int n = 0; n <= maxN; ++n) {
            longCoefficients[n][0] = longCoefficients[n][n] = 1;
            for (int k = 1; k < n; ++k)
                longCoefficients[n][k] = longCoefficients[n - 1][k - 1] + longCoefficients[n - 1][k];
        }
    }

    public static long getLongCoefficient(int n, int k) {
        check(n, k);
        return longCoefficients[n][k];
    }

    public static BigDecimal getBigDecimalCoefficient(int n, int k) {
        check(n, k);
        try {
            return bigIntCoefficients[n][k];
        } catch (ArrayIndexOutOfBoundsException e) {
            calculateNewCoefficients(n);
            return bigIntCoefficients[n][k];
        }
    }

    private static void calculateNewCoefficients(int n) {
        maxBigDecimalCoefficient = n + 100;
        bigIntCoefficients = new BigDecimal[maxBigDecimalCoefficient][maxBigDecimalCoefficient];
        initBigInt(maxBigDecimalCoefficient);
    }

    private static void check(int n, int k) throws IncorrectCoefficientException {
        if (k > n) {
            throw new IncorrectCoefficientException();
        }
    }
}
