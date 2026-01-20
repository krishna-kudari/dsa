n = int(input())

result = [n]
while n != 1:
    n = n // 2 if n % 2 == 0 else 3 * n + 1
    result.append(n)

print(*result)

# Time: O(log n) empirically | Space: O(k) where k = sequence length
# Collatz conjecture unproven, but: n ≤ 10^6 → ~500 steps, n ≤ 10^18 → ~1000 steps
