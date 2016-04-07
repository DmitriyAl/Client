package main.view;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Dmitriy Albot
 */
public class BinomialCoefficientCalculator {
    private static BigDecimal[][] bigIntCoefs;
    private static long[][] longCoefs;
    private static int maxLongCoef = 66;
    private static int maxBigIntCoef = 200;

    static {
        initBigInt(maxBigIntCoef);
        initFloat(maxLongCoef);
    }

    public static int getMaxLongCoef() {
        return maxLongCoef;
    }

    public static int getMaxBigIntCoef() {
        return maxBigIntCoef;
    }

    private static void initBigInt(int maxN) {
        bigIntCoefs = new BigDecimal[maxN + 1][maxN + 1];
        for (int n = 0; n <= maxN; ++n) {
            bigIntCoefs[n][0] = bigIntCoefs[n][n] = new BigDecimal("1");
            for (int k = 1; k < n; ++k)
                bigIntCoefs[n][k] = bigIntCoefs[n - 1][k - 1].add(bigIntCoefs[n - 1][k]);
        }
    }

    private static void initFloat(int maxN) {
        longCoefs = new long[maxN + 1][maxN + 1];
        for (int n = 0; n <= maxN; ++n) {
            longCoefs[n][0] = longCoefs[n][n] = 1;
            for (int k = 1; k < n; ++k)
                longCoefs[n][k] = longCoefs[n - 1][k - 1] + longCoefs[n - 1][k];
        }
    }

    public static BigDecimal getBigIntCoef(int n, int k) {
        return bigIntCoefs[n][k];
    }

    public static long getLongCoef(int n, int k) {
        return longCoefs[n][k];
    }
}
