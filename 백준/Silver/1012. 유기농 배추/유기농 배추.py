from collections import deque

testcaseNum = int(input())

# 위, 오, 아래, 왼
dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]

def dfs_stack(x, y):
    farm[x][y] = 2
    stack = [(x, y)]
    while stack:
        x, y = stack.pop()
        for i in range(4):
            newX = x + dx[i]
            newY = y + dy[i]
            if 0<= newX < len(farm) and 0<= newY < len(farm[0]) and farm[newX][newY] == 1:
                farm[newX][newY] = 2
                stack.append((newX, newY))
            else:
                continue
    

for _ in range(testcaseNum):
    col, row, cabbageNum = map(int, input().split())
    q = deque()
    count = 0
    farm = [[0 for _ in range(col)] for _ in range(row)]
    for _ in range(cabbageNum):
        c, r = map(int, input().split())
        farm[r][c] = 1
    for x in range(row):
        for y in range(col):
            if farm[x][y] == 1:
               dfs_stack(x, y)
               count += 1
    print(count)