from collections import deque

row, col = map(int, input().split())
Map = []
TwoRow, TwoCol = 0, 0

for r in range(row):
    OneRow = list(map(int, input().split()))
    if 2 in OneRow:
        TwoCol = OneRow.index(2)
        TwoRow = r
    Map.append(OneRow)

dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]
q = deque()
q.append((TwoRow, TwoCol, 0))
Map[TwoRow][TwoCol] = 0

canGo = []

while q:
    x, y, c = q.popleft()
    for i in range(4):
        newX = x + dx[i]
        newY = y + dy[i]
        
        if 0<=newX<row and 0<=newY<col and Map[newX][newY]==1:
            if c == 0:
                canGo.append((newX, newY))
                # 1인 거리 한정으로 2로 만들기..
                Map[newX][newY] = c+2
            else:
                Map[newX][newY] = c+1
            q.append((newX, newY, c+1))

for r in range(row):
    for c in range(col):
        if (r, c) in canGo:
            # 다시 1로 만들기
            Map[r][c] = 1
            
        else:
            if Map[r][c] == 1:
                Map[r][c] = -1
        print(Map[r][c], end=' ')
    print()