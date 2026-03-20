package uber;

import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 998244353;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            String[] grid = new String[n];
            for (int i = 0; i < n; i++) grid[i] = br.readLine().trim();

            sb.append(solve(n, m, d, grid)).append('\n');
        }
        System.out.print(sb);
    }

    static long solve(int n, int m, int d, String[] grid) {
        // dp[j][f]: current row's DP values
        // sdp[j][f]: prefix sums of dp for current and previous rows
        // We only need two rows at a time → space O(m)
        long[][] dpCur  = new long[m][2];   // row i
        long[][] sdpCur = new long[m][2];   // prefix sums of dpCur
        long[][] dpPrev = new long[m][2];   // row i+1 (already computed)
        long[][] sdpPrev= new long[m][2];   // prefix sums of dpPrev

        // R1: max horizontal distance when transitioning across rows
        // dist² = 1 + colDiff² ≤ d² → colDiff ≤ floor(sqrt(d²-1))
        // For integer d ≥ 1: floor(sqrt(d²-1)) = d-1
        int R1 = d - 1;   // cross-row horizontal range
        int R0 = d;       // same-row horizontal range

        // Helper: range sum on sdp array
        // sum of sdp[L..R][f]
        // We inline this for speed

        // Process rows bottom-up: i = n-1 down to 0
        for (int i = n - 1; i >= 0; i--) {

            // Clear current row
            for (int j = 0; j < m; j++) {
                dpCur[j][0] = dpCur[j][1] = 0;
            }

            // ── PASS 1: compute dpCur[j][1] (first hold on row i) ──
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) != 'X') continue;

                long val = 0;

                // Base case: bottom row → every X is a valid route start
                if (i == n - 1) val = 1;

                // From row below: sum sdpPrev[j-R1 .. j+R1][0]
                if (i < n - 1) {
                    int L = Math.max(0, j - R1);
                    int R = Math.min(m - 1, j + R1);
                    if (L <= R) {
                        val += sdpPrev[R][0];
                        if (L > 0) val -= sdpPrev[L-1][0];
                    }
                }

                dpCur[j][1] = ((val % MOD) + MOD) % MOD;
            }

            // Build prefix sum for f=1
            sdpCur[0][1] = dpCur[0][1];
            for (int j = 1; j < m; j++) {
                sdpCur[j][1] = sdpCur[j-1][1] + dpCur[j][1];
            }

            // ── PASS 2: compute dpCur[j][0] (final hold on row i) ──
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) != 'X') continue;

                // Start with the solo-hold contribution (same sources as f=1)
                long val = dpCur[j][1];

                // Add: j is the SECOND hold, after some j' with |j-j'| <= R0, j' != j
                // = sum sdpCur[j-R0..j+R0][1]  minus  dpCur[j][1]  (exclude j itself)
                int L = Math.max(0, j - R0);
                int R = Math.min(m - 1, j + R0);
                if (L <= R) {
                    long rangeSum = sdpCur[R][1];
                    if (L > 0) rangeSum -= sdpCur[L-1][1];
                    val += rangeSum;
                    val -= dpCur[j][1];   // remove j pairing with itself
                }

                dpCur[j][0] = ((val % MOD) + MOD) % MOD;
            }

            // Build prefix sum for f=0
            sdpCur[0][0] = dpCur[0][0];
            for (int j = 1; j < m; j++) {
                sdpCur[j][0] = sdpCur[j-1][0] + dpCur[j][0];
            }

            // dpCur becomes dpPrev for next iteration
            long[][] tmpDp = dpPrev; dpPrev = dpCur; dpCur = tmpDp;
            long[][] tmpSdp = sdpPrev; sdpPrev = sdpCur; sdpCur = tmpSdp;
        }

        // Answer: sum of dpPrev[j][0] for all j in row 0 (top)
        // (dpPrev now holds the last computed row = row 0)
        long ans = 0;
        for (int j = 0; j < m; j++) {
            ans = (ans + dpPrev[j][0]) % MOD;
        }
        return ans;
    }
}
