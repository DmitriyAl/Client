package main.view;

import java.math.BigInteger;

/**
 * @author Dmitriy Albot
 */
public class BinomialCoefficientCalculator {
    private static BigInteger[][] coefs;

    static {
        init(200);
    }

    private static void init(int maxN) {
        int maxn = maxN;
        coefs = new BigInteger[maxn + 1][maxn + 1];
        for (int n = 0; n <= maxn; ++n) {
            coefs[n][0] = coefs[n][n] = new BigInteger("1");
            for (int k = 1; k < n; ++k)
                coefs[n][k] = coefs[n - 1][k - 1].add(coefs[n - 1][k]);
        }
    }

    public static BigInteger getCoef(int n, int k) {
        return coefs[n][k];
    }
}
