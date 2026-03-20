import java.util.*;

public class c2 {
    static final int MOD = 998244353;
    static final int MAX_VAL = 200;
    
    public static int calcInventoryRestorations(List<Integer> warehouseStockLevels) {
        int n = warehouseStockLevels.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = warehouseStockLevels.get(i);
        }
        
        // dp[prev][curr] = ways where arr[i-1]=prev, arr[i]=curr, constraints for 0..i-1 satisfied
        long[][] dp = new long[MAX_VAL + 1][MAX_VAL + 1];
        
        // Initialize for i=1 (positions 0 and 1)
        // Constraint for position 0: arr[0] <= arr[1]
        if (arr[0] == -1 && arr[1] == -1) {
            // v0 <= v1: for each v1, there are v1 choices for v0
            for (int v1 = 1; v1 <= MAX_VAL; v1++) {
                for (int v0 = 1; v0 <= v1; v0++) {
                    dp[v0][v1] = 1;
                }
            }
        } else if (arr[0] == -1) {
            int v1 = arr[1];
            for (int v0 = 1; v0 <= v1; v0++) {
                dp[v0][v1] = 1;
            }
        } else if (arr[1] == -1) {
            int v0 = arr[0];
            for (int v1 = v0; v1 <= MAX_VAL; v1++) {
                dp[v0][v1] = 1;
            }
        } else {
            if (arr[0] <= arr[1]) {
                dp[arr[0]][arr[1]] = 1;
            }
        }
        
        // Process positions 2 to n-1
        for (int i = 2; i < n; i++) {
            long[][] newDp = new long[MAX_VAL + 1][MAX_VAL + 1];
            
            // Precompute suffix sums for optimization
            // suffixSum[prev][curr] = sum of dp[prev][curr] + dp[prev][curr+1] + ... + dp[prev][MAX_VAL]
            // This helps when next >= curr (constraint: curr <= max(prev, next))
            
            // For constraint curr <= max(prev, next):
            // Case 1: curr <= prev -> any next works
            // Case 2: curr > prev -> need next >= curr
            
            if (arr[i] == -1) {
                // next can be any value 1..MAX_VAL
                // For each (prev, curr) with dp[prev][curr] > 0:
                //   If curr <= prev: add dp[prev][curr] to newDp[curr][next] for all next
                //   If curr > prev: add dp[prev][curr] to newDp[curr][next] for next >= curr
                
                // Precompute sums for optimization
                // sumGeq[curr] = sum of dp[prev][curr] for prev >= curr (i.e., curr <= prev)
                // sumLt[curr] = sum of dp[prev][curr] for prev < curr (i.e., curr > prev)
                long[] sumGeq = new long[MAX_VAL + 2];
                long[] sumLt = new long[MAX_VAL + 2];
                
                for (int curr = 1; curr <= MAX_VAL; curr++) {
                    for (int prev = 1; prev <= MAX_VAL; prev++) {
                        if (prev >= curr) {
                            sumGeq[curr] = (sumGeq[curr] + dp[prev][curr]) % MOD;
                        } else {
                            sumLt[curr] = (sumLt[curr] + dp[prev][curr]) % MOD;
                        }
                    }
                }
                
                // Case 1: curr <= prev (sumGeq), any next works
                // newDp[curr][next] += sumGeq[curr] for all next
                for (int curr = 1; curr <= MAX_VAL; curr++) {
                    if (sumGeq[curr] > 0) {
                        for (int next = 1; next <= MAX_VAL; next++) {
                            newDp[curr][next] = (newDp[curr][next] + sumGeq[curr]) % MOD;
                        }
                    }
                }
                
                // Case 2: curr > prev (sumLt), need next >= curr
                // newDp[curr][next] += sumLt[curr] for next >= curr
                // Use suffix sum approach: accumulate and then distribute
                long[] toAdd = new long[MAX_VAL + 2];
                for (int curr = 1; curr <= MAX_VAL; curr++) {
                    if (sumLt[curr] > 0) {
                        // Add sumLt[curr] to newDp[curr][next] for next = curr..MAX_VAL
                        // We'll use a difference array approach
                        toAdd[curr] = (toAdd[curr] + sumLt[curr]) % MOD;
                    }
                }
                // Now for each curr, add toAdd[curr] to newDp[curr][next] for next >= curr
                for (int curr = 1; curr <= MAX_VAL; curr++) {
                    if (toAdd[curr] > 0) {
                        for (int next = curr; next <= MAX_VAL; next++) {
                            newDp[curr][next] = (newDp[curr][next] + toAdd[curr]) % MOD;
                        }
                    }
                }
            } else {
                int next = arr[i];
                for (int prev = 1; prev <= MAX_VAL; prev++) {
                    for (int curr = 1; curr <= MAX_VAL; curr++) {
                        if (dp[prev][curr] == 0) continue;
                        if (curr <= Math.max(prev, next)) {
                            newDp[curr][next] = (newDp[curr][next] + dp[prev][curr]) % MOD;
                        }
                    }
                }
            }
            dp = newDp;
        }
        
        // Final check: constraint for position n-1: arr[n-1] <= arr[n-2]
        long result = 0;
        for (int prev = 1; prev <= MAX_VAL; prev++) {
            for (int curr = 1; curr <= prev; curr++) {
                result = (result + dp[prev][curr]) % MOD;
            }
        }
        
        return (int) result;
    }
    
    public static void main(String[] args) {
        // Test case: [-1, -1] -> 200
        System.out.println("Test 1: " + calcInventoryRestorations(Arrays.asList(-1, -1)) + " (expected 200)");
        
        // Test case: [1, -1, 2] -> 1
        System.out.println("Test 2: " + calcInventoryRestorations(Arrays.asList(1, -1, 2)) + " (expected 1)");
        
        // Test case: [1, 3, 4, 1, 1] -> 0
        System.out.println("Test 3: " + calcInventoryRestorations(Arrays.asList(1, 3, 4, 1, 1)) + " (expected 0)");
        
        // Test case: [1, 2] -> 0 (invalid: need arr[0]<=arr[1] AND arr[1]<=arr[0], so must be equal)
        System.out.println("Test 4: " + calcInventoryRestorations(Arrays.asList(1, 2)) + " (expected 0)");
        
        // Test case: [2, 1] -> 0 (invalid: 2 > 1 violates arr[0] <= arr[1])
        System.out.println("Test 5: " + calcInventoryRestorations(Arrays.asList(2, 1)) + " (expected 0)");
        
        // Test case: [1, 1] -> 1
        System.out.println("Test 6: " + calcInventoryRestorations(Arrays.asList(1, 1)) + " (expected 1)");
        
        // Test case: [1, 2, 1] -> 1 (valid: 1<=2, 2<=max(1,1)=1? NO, 2>1, invalid)
        System.out.println("Test 7: " + calcInventoryRestorations(Arrays.asList(1, 2, 1)) + " (expected 0)");
        
        // Test case: [1, 1, 1] -> 1 (valid)
        System.out.println("Test 8: " + calcInventoryRestorations(Arrays.asList(1, 1, 1)) + " (expected 1)");
        
        // Test case: [-1, -1, -1] - need to count manually
        // For [a, b, c]: a<=b, b<=max(a,c), c<=b
        // This means b >= a AND b >= c (since c <= b)
        // But also b <= max(a, c). Since a <= b and c <= b, max(a,c) <= b
        // So b <= max(a,c) <= b, meaning b = max(a,c)
        // So either a = b or c = b (or both)
        // Case 1: a = b, c can be 1..b -> b choices for c, 1 choice for a = b*1 = b
        // Case 2: c = b, a can be 1..b -> b choices for a, 1 choice for c = b*1 = b  
        // Case 3: a = b AND c = b -> counted twice, subtract b (actually 1 for each b)
        // Total for each b: b + b - 1 = 2b - 1
        // Sum for b=1..200: sum(2b-1) = 2*200*201/2 - 200 = 40200 - 200 = 40000
        System.out.println("Test 9: " + calcInventoryRestorations(Arrays.asList(-1, -1, -1)) + " (expected 40000)");
        
        // Performance test
        List<Integer> large = new ArrayList<>();
        for (int i = 0; i < 1000; i++) large.add(-1);
        long start = System.currentTimeMillis();
        int result = calcInventoryRestorations(large);
        long end = System.currentTimeMillis();
        System.out.println("Test 10 (n=1000, all -1): " + result + " in " + (end-start) + "ms");
        
        // Performance test with n=10000
        List<Integer> larger = new ArrayList<>();
        for (int i = 0; i < 10000; i++) larger.add(-1);
        start = System.currentTimeMillis();
        result = calcInventoryRestorations(larger);
        end = System.currentTimeMillis();
        System.out.println("Test 11 (n=10000, all -1): " + result + " in " + (end-start) + "ms");
    }
}
