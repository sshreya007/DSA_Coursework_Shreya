public class CriticalTemperature {
    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {{1, 2}, {2, 6}, {3, 14}};

        for (int[] testCase : testCases) {
            int k = testCase[0];
            int n = testCase[1];
            System.out.println("k = " + k + ", n = " + n + " -> Min measurements: " + minMeasurements(k, n));
        }
    }

    public static int minMeasurements(int k, int n) {
        if (k == 1) return n;  // If only 1 sample, test sequentially
        if (n == 0) return 0;  // No temperatures to test

        int[][] dp = new int[k + 1][n + 1];
        int m = 0;  // Number of measurements

        while (dp[k][m] < n) {
            m++;
            for (int i = 1; i <= k; i++) {
                dp[i][m] = dp[i - 1][m - 1] + dp[i][m - 1] + 1;
            }
        }
        return m;
    }
}


