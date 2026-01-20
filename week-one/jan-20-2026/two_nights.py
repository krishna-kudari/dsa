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

def comb2(n):
    return int((n-1)*(n)/2)

def solve():
    n = inp()
    for k in range(1,n+1):
        ans = comb2(k*k) - ((k-1)*(k-2)*4)
        print(ans)
    pass


def main():
    t = 1
    # t = inp()  # Uncomment for multiple test cases
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
