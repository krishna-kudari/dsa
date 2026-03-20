import java.io.*;
import java.util.*;

public class Solution {

    static int findMinimumOperations(List<Integer> arrList) {
        int n = arrList.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = arrList.get(i);

        // Build sorted targets
        int[] sortedAsc  = new int[n];
        int[] sortedDesc = new int[n];
        for (int i = 0; i < n; i++) {
            sortedAsc[i]  = i + 1;
            sortedDesc[i] = n - i;
        }

        // 0 ops: already sorted ascending
        if (Arrays.equals(arr, sortedAsc)) return 0;

        // 1 op: just reverse
        if (Arrays.equals(arr, sortedDesc)) return 1;

        // Build reversed array
        int[] rev = new int[n];
        for (int i = 0; i < n; i++) rev[i] = arr[n - 1 - i];

        int best = Integer.MAX_VALUE;

        /*
         * Every solution path is one of 4 strategies:
         *  s=0: transfers only          → rotate arr to sortedAsc,  cost = pos
         *  s=1: reverse + transfers     → rotate rev to sortedAsc,  cost = 1 + pos
         *  s=2: transfers + reverse     → rotate arr to sortedDesc, cost = pos + 1
         *  s=3: reverse+transfers+rev   → rotate rev to sortedDesc, cost = 1 + pos + 1
         */
        int[]   prefix  = {0, 1, 0, 1};
        int[]   suffix  = {0, 0, 1, 1};
        int[][] targets = {sortedAsc, sortedAsc, sortedDesc, sortedDesc};
        int[][] bases   = {arr,       rev,       arr,        rev       };

        for (int s = 0; s < 4; s++) {
            int[] target = targets[s];
            int[] base   = bases[s];
            int pivot    = target[0]; // must be at front after rotation

            // Find pivot in base
            int pos = -1;
            for (int i = 0; i < n; i++) {
                if (base[i] == pivot) { pos = i; break; }
            }
            if (pos < 0) continue;

            // Verify: base rotated left by pos == target
            boolean match = true;
            for (int i = 0; i < n; i++) {
                if (base[(pos + i) % n] != target[i]) { match = false; break; }
            }
            if (match) {
                best = Math.min(best, prefix[s] + pos + suffix[s]);
            }
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arr.add(Integer.parseInt(br.readLine().trim()));
        }

        System.out.println(findMinimumOperations(arr));
    }
}
