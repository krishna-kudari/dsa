seq = input()

ans = 1
lp = rp = 0
while rp != len(seq):
    if seq[rp] != seq[lp]:
        lp = rp
    rp += 1
    cl = rp - lp
    ans = max(cl, ans)

print(ans)
