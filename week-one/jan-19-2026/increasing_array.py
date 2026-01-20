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

    ans = 0
    for i in range(1,len(numbers)):
        # print(numbers[i])
        dif = numbers[i-1] - numbers[i]
        if dif > 0:
            ans += dif
            numbers[i] = numbers[i-1]
    print(ans)


def main():
    t = 1
    for _ in range(t):
        solve()


if __name__ == "__main__":
    main()
