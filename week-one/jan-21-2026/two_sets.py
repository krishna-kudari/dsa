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


def solve():
    n = inp()
    total_sum = n * (n + 1) // 2

    # If total sum is odd, cannot divide into two equal sets
    if total_sum % 2 != 0:
        print("NO")
        return

    # Target sum for each set
    target_sum = total_sum // 2
    setA = []
    setB = []

    # Greedily assign numbers from largest to smallest
    for i in range(n, 0, -1):
        if i <= target_sum:
            setA.append(i)
            target_sum -= i
        else:
            setB.append(i)

    print("YES")
    print(len(setA))
    print(*setA)
    print(len(setB))
    print(*setB)


def main():
    t = 1
    # t = inp()  # Uncomment for multiple test cases
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
