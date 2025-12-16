from collections import deque

N = int(input())
board = []
for i in range(N):
    row = list(map(int, input().split()))
    for j in range(N):
        if row[j] == 9:
            sx, sy = i, j
            row[j] = 0
    board.append(row)

dx = [-1, 0, 0, 1]
dy = [0, -1, 1, 0]

def bfs(x, y, size):
    visited = [[-1]*N for _ in range(N)]
    q = deque()
    q.append((x, y))
    visited[x][y] = 0
    fishes = []
    min_dist = None

    while q:
        cx, cy = q.popleft()
        for d in range(4):
            nx, ny = cx + dx[d], cy + dy[d]
            if 0 <= nx < N and 0 <= ny < N and visited[nx][ny] == -1:
                if board[nx][ny] <= size:
                    visited[nx][ny] = visited[cx][cy] + 1
                    if 0 < board[nx][ny] < size:
                        if min_dist is None or visited[nx][ny] <= min_dist:
                            min_dist = visited[nx][ny]
                            fishes.append((visited[nx][ny], nx, ny))
                    q.append((nx, ny))

    if not fishes:
        return None

    fishes.sort()
    return fishes[0]  # (거리, x, y)

time = 0
size = 2
eat = 0
x, y = sx, sy

while True:
    result = bfs(x, y, size)
    if result is None:
        break

    dist, nx, ny = result
    time += dist
    x, y = nx, ny
    board[x][y] = 0
    eat += 1

    if eat == size:
        size += 1
        eat = 0

print(time)