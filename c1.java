import java.util.*;

public class c1 {
    
    public static int findMinimumOperations(List<Integer> arr) {
        int n = arr.size();
        
        // Check if already sorted
        if (isSorted(arr)) return 0;
        
        // Find position of 1 and n
        int pos1 = indexOf(arr, 1);
        int posN = indexOf(arr, n);
        
        // Check if it's a rotation of ascending order [1,2,3,...,n]
        if (isAscendingRotation(arr, pos1)) {
            // Option 1: rotate pos1 times (direct)
            int opt1 = pos1;
            
            // Option 2: reverse -> rotate -> reverse
            // After reverse: descending rotation
            // Position of n after reverse = n - 1 - posN
            // Need to rotate (n - 1 - posN) times to get [n,n-1,...,1], then reverse
            // Total: 1 + (n - 1 - posN) + 1 = n + 1 - posN
            int opt2 = n + 1 - posN;
            
            return Math.min(opt1, opt2);
        }
        
        // Check if it's a rotation of descending order [n,n-1,...,1]
        if (isDescendingRotation(arr, posN)) {
            // In descending rotation, 1 is at position (posN + n - 1) % n
            int pos1_desc = (posN + n - 1) % n;
            
            // Option 1: reverse first (1 op), then we get ascending rotation
            //           with 1 at position (n-1-pos1_desc), need that many rotations
            int pos1_after_reverse = n - 1 - pos1_desc;
            int opt1 = 1 + pos1_after_reverse;
            
            // Option 2: rotate posN times to get [n,n-1,...,1], then reverse (1 op)
            int opt2 = posN + 1;
            
            return Math.min(opt1, opt2);
        }
        
        return -1; // Should not reach here per problem guarantee
    }
    
    private static boolean isSorted(List<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != i + 1) return false;
        }
        return true;
    }
    
    private static int indexOf(List<Integer> arr, int val) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == val) return i;
        }
        return -1;
    }
    
    private static boolean isAscendingRotation(List<Integer> arr, int pos1) {
        int n = arr.size();
        for (int i = 0; i < n; i++) {
            int expected = (i - pos1 + n) % n + 1;
            if (arr.get(i) != expected) return false;
        }
        return true;
    }
    
    private static boolean isDescendingRotation(List<Integer> arr, int posN) {
        int n = arr.size();
        for (int i = 0; i < n; i++) {
            int expected = n - (i - posN + n) % n;
            if (arr.get(i) != expected) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        // Test case from problem: [2,3,4,5,6,7,8,9,10,1] -> 3
        System.out.println("Test 1: " + findMinimumOperations(Arrays.asList(2,3,4,5,6,7,8,9,10,1)) + " (expected 3)");
        
        // Sample case 0: [1,3,2] -> 2
        System.out.println("Test 2: " + findMinimumOperations(Arrays.asList(1,3,2)) + " (expected 2)");
        
        // Sample case 1: [5,4,3,2,1] -> 1
        System.out.println("Test 3: " + findMinimumOperations(Arrays.asList(5,4,3,2,1)) + " (expected 1)");
        
        // Already sorted: [1,2,3,4,5] -> 0
        System.out.println("Test 4: " + findMinimumOperations(Arrays.asList(1,2,3,4,5)) + " (expected 0)");
        
        // [3,4,5,1,2] - ascending rotation
        System.out.println("Test 5: " + findMinimumOperations(Arrays.asList(3,4,5,1,2)) + " (expected 3)");
        
        // [2,1,5,4,3] - descending rotation
        System.out.println("Test 6: " + findMinimumOperations(Arrays.asList(2,1,5,4,3)) + " (expected 3)");
        
        // [2,1] - descending rotation
        System.out.println("Test 7: " + findMinimumOperations(Arrays.asList(2,1)) + " (expected 1)");
        
        // [4,3,2,1,5] - descending rotation, reverse->rotate->sorted = 2 ops
        System.out.println("Test 8: " + findMinimumOperations(Arrays.asList(4,3,2,1,5)) + " (expected 2)");
        
        // [1,5,4,3,2] - descending rotation
        System.out.println("Test 9: " + findMinimumOperations(Arrays.asList(1,5,4,3,2)) + " (expected 2)");
    }
}
