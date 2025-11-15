import sys
sys.setrecursionlimit(10**7)

K = int(sys.stdin.readline())
arr = []

def hanoi(num, start, mid, end):
    if num == 1:
        arr.append((start, end))
        return
    # 1) n-1개를 start → mid
    hanoi(num-1, start, end, mid)
    # 2) 가장 큰 원판을 end에 놓기
    arr.append((start, end))
    # 3) n-1개를 mid → end
    hanoi(num-1, mid, start, end)


# 이동 횟수 출력
print((1 << K) - 1)

if K<=20:
    hanoi(K, 1, 2, 3)
    for a in arr:
        sys.stdout.write(f"{a[0]} {a[1]}\n")
