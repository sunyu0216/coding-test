T = int(input())

for _ in range(T):
    k = int(input())
    n = int(input())
    
    # dp[f][r] : f층 r호에 사는 사람 수
    dp = [[0] * (n+1) for _ in range(k+1)]
    
    # 0층 초기화: 1호~n호
    for r in range(1, n+1):
        dp[0][r] = r
    
    # DP 계산
    for f in range(1, k+1):
        for r in range(1, n+1):
            dp[f][r] = dp[f][r-1] + dp[f-1][r]
    
    print(dp[k][n])