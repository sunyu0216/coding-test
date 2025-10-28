from collections import deque

N = int(input())
Grid = []

dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]

for _ in range(N):
    color = input()
    Grid.append(list(color))

def bfs(x, y, color, num):
    q = deque([(x, y)])
    Grid[x][y] = num
    while q:
        x, y = q.popleft()
        for i in range(4):
            newX = x+dx[i]
            newY = y+dy[i]
            
            if 0<=newX<N and 0<=newY<N and Grid[newX][newY] == color:
                Grid[newX][newY] = num
                q.append((newX,newY))
                
def bfs_2(x, y, num):
    q = deque([(x, y)])
    Grid[x][y] = num
    while q:
        x, y = q.popleft()
        for i in range(4):
            newX = x+dx[i]
            newY = y+dy[i]
            
            if 0<=newX<N and 0<=newY<N and Grid[newX][newY] == num:
                Grid[newX][newY] = "2"
                q.append((newX,newY))
        

no = 0
yes = 0
for r in range(N):
    for c in range(N):
        if Grid[r][c] == "R" or Grid[r][c] == "G": #빨간색 혹은 초록색인 경우(적록색약 고려해야 함)
            bfs(r,c,Grid[r][c],"0")
            no+=1
        elif Grid[r][c] == "B":
            bfs(r,c,Grid[r][c],"1")
            no+=1
                
for r in range(N):
    for c in range(N):
        if Grid[r][c] == "0":
            bfs_2(r,c,"0")
            yes+=1
        elif Grid[r][c] == "1":
            bfs_2(r,c,"1")
            yes+=1
            
print(no, yes) 