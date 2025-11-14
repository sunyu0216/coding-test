testNumber = int(input())

for _ in range(testNumber):
    curNum = int(input())
    dp = [0] * (max(4, curNum + 1))

    dp[1] = 1
    dp[2] = 2
    dp[3] = 4

    if curNum <= 3:
        print(dp[curNum])
    else:
        for i in range(4, curNum + 1):
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3]
        print(dp[curNum])