# pylint: disable=unused-import,redefined-builtin, missing-docstring, unused-variable
import sys
from collections import defaultdict, deque, Counter
from itertools import permutations, combinations, accumulate
from functools import lru_cache
from heapq import heappush, heappop, heapify
from bisect import bisect_left, bisect_right
from math import gcd, lcm, sqrt, ceil, floor, log2

input = sys.stdin.readline


def inp():
    return int(input())


def inps():
    return input().strip()


def inpl():
    return list(map(int, input().split()))


def inpm():
    return map(int, input().split())


MOD = 10**9 + 7
INF = float("inf")


# Math utility functions
def sum_natural(n):
    """Sum of first n natural numbers: n*(n+1)//2"""
    return n * (n + 1) // 2


def comb2(n):
    """Combinations C(n, 2) = n*(n-1)//2"""
    return n * (n - 1) // 2


def nCr(n, r):
    """Combinations C(n, r)"""
    if r > n or r < 0:
        return 0
    if r == 0 or r == n:
        return 1
    r = min(r, n - r)
    result = 1
    for i in range(r):
        result = result * (n - i) // (i + 1)
    return result


def nPr(n, r):
    """Permutations P(n, r)"""
    if r > n or r < 0:
        return 0
    result = 1
    for i in range(r):
        result *= n - i
    return result


def factorial(n):
    """Factorial of n"""
    if n < 0:
        return 0
    result = 1
    for i in range(2, n + 1):
        result *= i
    return result


def gcd_extended(a, b):
    """Extended Euclidean Algorithm: returns (gcd, x, y) such that ax + by = gcd"""
    if a == 0:
        return b, 0, 1
    gcd_val, x1, y1 = gcd_extended(b % a, a)
    x = y1 - (b // a) * x1
    y = x1
    return gcd_val, x, y


def mod_inverse(a, m=MOD):
    """Modular inverse of a modulo m using Extended Euclidean Algorithm"""
    gcd_val, x, _ = gcd_extended(a, m)
    if gcd_val != 1:
        return None  # Inverse doesn't exist
    return (x % m + m) % m


def power_mod(base, exp, mod=MOD):
    """Fast exponentiation: (base^exp) % mod"""
    result = 1
    base = base % mod
    while exp > 0:
        if exp % 2 == 1:
            result = (result * base) % mod
        exp = exp >> 1
        base = (base * base) % mod
    return result


def is_prime(n):
    """Check if n is prime"""
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    i = 3
    while i * i <= n:
        if n % i == 0:
            return False
        i += 2
    return True

def countGoUp(i, j, n, m, d, grid, memo):
    if i == 0:
        return 1

    total = 0
    # iterate over next row
    for j2 in range(m):
        if grid[i-1][j2] != 'X':
            continue
        rowDif = 1
        colDif = j2 - j
        dist2 = rowDif*rowDif + colDif*colDif
        if dist2 <= d*d:
            total = (total + count(i-1, j2, 1, n, m, d, grid, memo)) % MOD
    return total % MOD

def count(i, j, f, n, m, d, grid, memo):
    if memo[i][j][f] != -1:
        return memo[i][j][f]
    if i == 0 and f == 0:
        return 1

    total = 0
    if f == 1:
        # Option A first hold go up as solo hold
        cntA = countGoUp(i, j, n, m, d, grid, memo)
        # print(f"{cntA} - {i}, {j}")
        total = (total + cntA) % MOD

        # Option B grabSecond and from their go up
        for j2 in range(m):
            if j2 == j:
                continue
            if grid[i][j2] != 'X':
                continue

            dist2 = (j2 - j)*(j2 - j)

            if dist2 <= d*d:
                cntB = countGoUp(i, j2, n, m, d, grid, memo)
                # print(f"{cntA} - {i}, {j}")
                total = (total + cntB) % MOD

    memo[i][j][f] = total % MOD
    return memo[i][j][f]

def dp(n,m,d,grid):

    dp = [ [ [ 0 for _ in range(2)] for _ in range(m)] for _ in range(n)]

    psum = [ [ 0 for _ in range(m)] for _ in range(n)]

    for row in range(n-1, -1, -1):
        # fill the dp[row][col][1]

        for col in range(m):
            if grid[row][col] != 'X':
                continue

            val = 0
            if row == n-1:
                val+=1
            if row < n-1:
                # for j2 in range(max(0,col - (d - 1)), min(m-1,col + (d - 1)) + 1):
                #     val = (val + dp[row+1][j2][0]) % MOD
                left = max(0,col - (d - 1))
                if left == 0:
                    leftsum = 0
                else:
                    leftsum = psum[row+1][left-1]
                right = min(m-1,col + (d - 1)) + 1
                val = psum[row+1][right] - leftsum

            dp[row][col][1] = val
        # fill the dp[row][col][0]
        for col in range(m):
            if grid[row][col] != 'X':
                continue

            val = dp[row][col][1]
            left  = max(0,col-d)
            right = min(m-1, col+d) + 1
            if left == 0:
                leftsum = 0
            else:
                leftsum = psum
            for j2 in range(max(0,col-d), min(m-1, col+d) + 1):
                if j2 == col:
                    continue
                val = (val + dp[row][j2][1]) % MOD

            dp[row][col][0] = val

        for col in range(m):
            psum[row][col] = (psum[row][col-1] + dp[row][col][0]) % MOD

    ans = 0
    for j in range(m):
        ans = (ans + dp[0][j][0]) % MOD
    # print(dp)
    print(ans)
    return

def solve():
    n, m, d = inpl()
    grid = [ inps() for _ in range(n)]

    dp(n, m, d, grid)
    # total = 0
    # memo = [[[-1 for _ in range(2)] for _ in range(m)] for _ in range(n)]
    # # iterate columns of bottom row n-1
    # for col in range(m):
    #     if grid[n-1][col] == 'X':
    #         cnt = count(n-1, col, 1, n, m, d, grid, memo)
    #         # print(f"{cnt} - {col}")
    #         total = (total + cnt) % MOD
    # print(total)
    pass


def main():
    # t = 1
    t = inp()  # Uncomment for multiple test cases
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
