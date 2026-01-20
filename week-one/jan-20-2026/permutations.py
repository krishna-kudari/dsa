n = int(input())

if n == 1:
    print(1)
elif n == 2 or n == 3:
    print("NO SOLUTION")
else:
    # Strategy: separate evens and odds, then combine
    # Put evens first, then odds (or vice versa)
    # This ensures no adjacent elements differ by 1

    evens = [i for i in range(2, n + 1, 2)]  # 2, 4, 6, ...
    odds = [i for i in range(1, n + 1, 2)]  # 1, 3, 5, ...

    # Check if last even and first odd differ by 1
    # If so, reverse the order (odds first, then evens)
    if evens and odds and abs(evens[-1] - odds[0]) == 1:
        perm = odds + evens
    else:
        perm = evens + odds

    print(*perm)
