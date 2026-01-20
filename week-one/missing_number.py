# pylint: disable=unused-import,redefined-builtin, missing-docstring, unused-variable
import sys

input = sys.stdin.readline


def inp():
    return int(input())


def inpl():
    return list(map(int, input().split()))


def solve():
    n = inp()
    numbers = inpl()

    # Mark all present numbers by negating their corresponding index
    for i in range(n - 1):
        num = abs(numbers[i])
        if num <= n - 1:
            numbers[num - 1] *= -1

    # Find the first positive number (missing number)
    ans = n
    for i in range(n - 1):
        if numbers[i] > 0:
            ans = i + 1
            break

    print(ans)


def main():
    t = 1
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
