from collections import deque

col, row = map(int, input().split())
box = [list(map(int, input().split())) for _ in range(row)]
visited = [[False] * col for _ in range(row)]
# 위, 오, 아래, 왼
dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]

q = deque()

zeroCount = 0
for r in range(row):
    for c in range(col):
        if box[r][c] == 1:
            visited[r][c] = True
            q.append((r, c, 0))
        elif box[r][c] == 0:
            zeroCount += 1

def bfs():
    global zeroCount
    date = 0
    while q:
        x, y, date =  q.popleft() 
        for i in range(4):
            newX = x+dx[i]
            newY = y+dy[i]
            
            if 0<=newX<=row-1 and 0<=newY<=col-1 and not visited[newX][newY] and box[newX][newY]==0:
                visited[newX][newY] = True
                box[newX][newY] = 1
                zeroCount -= 1
                q.append((newX, newY, date+1))
                
    return date

answer = bfs()
if zeroCount > 0:
    answer = -1
    print(answer)
else:
    print(answer)