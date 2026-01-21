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


def solve():
    n = inp()
    


    pass


def main():
    t = 1
    # t = inp()  # Uncomment for multiple test cases
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
