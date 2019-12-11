package com.lorenzorigato.base.util;

/**
 * This class contains the Levensthein distance algorithm implementation.
 * This is done using Dynamic Programming.
 */
public class LevenstheinDistance {


    public int levenstheinDistance(String a, String b) {

        // Both strings are null
        if (a == null && b == null) {
            return 0;
        }

        // A is null and B is not null, returns the length of B
        if ((a == null || a.isEmpty())) {
            return (b == null || b.isEmpty()) ? 0 : b.length();
        }

        if ((b == null || b.isEmpty())) {
            return a.length();
        }

        // A and B are not null and not empty
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    int costOfSubstitution = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = min(
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1,
                            dp[i - 1][j - 1] + costOfSubstitution);
                }
            }
        }

        return dp[a.length()][b.length()];
    }

    private int min(int a, int b, int c) {
        int min = Math.min(a, b);
        return Math.min(min, c);
    }
}
