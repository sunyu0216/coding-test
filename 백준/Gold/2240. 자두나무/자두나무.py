T, W = map(int, input().split())

DP = [[0 for _ in range(W+1)] for _ in range(T+1)]

for t in range(1, T+1):
    tree = int(input())

    for w in range(W+1):
        if w%2 == 0: # 1번 나무만 가능
            if w == 0:
                DP[t][w] = DP[t-1][w] + (1 if tree == 1 else 0)
            else:
                DP[t][w] = max(DP[t-1][w], DP[t-1][w-1]) + (1 if tree == 1 else 0)
        elif w%2 != 0: # 2번 나무만 가능
            DP[t][w] = max(DP[t-1][w], DP[t-1][w-1]) + (1 if tree == 2 else 0)

print(max(DP[T]))