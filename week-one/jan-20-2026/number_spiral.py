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
    (x, y) = inpl()

    x -=1
    y -=1
    if x > y:
        # row even x -> start, odd x -> end
        if x%2 == 0:
            ans = (x*x+1)+y
        else:
            ans = ((x+1)*(x+1))-y
    else:
        # col even y -> end, odd y -> start
        if y%2 == 1:
            ans = (y*y+1)+x
        else:
            ans = ((y+1)*(y+1))-x

    print(ans)


def main():
    t = 1
    t = inp()  # Uncomment for multiple test cases
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()

