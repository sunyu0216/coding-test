from collections import deque

m, n = map(int, input().split())

board = [0] *101
visited = [False] *101

for _ in range(m):
    start, end = map(int, input().split())
    board[start] = end

for _ in range(n):
    start, end = map(int, input().split())
    board[start] = end


q = deque()
q.append((1, 0))  # (현재 위치, 주사위 던진 횟수)
visited[1] = True
answer = 100

while q:
    end, count = q.popleft()
    
    if end == 100:
        answer = min(answer, count)
        continue
    else: 
        for i in range(1,7): # 일단 주사위 굴리기
            # 주사위 굴렸을 때 이동될 칸을 기준으로 
            next_pos = end+i
            
            if next_pos > 100:
                continue
            
            # 사다리/뱀 있으면 연속으로 이동될 칸 찾기
            while board[next_pos] > 0:
                next_pos = board[next_pos]
            
            if not visited[next_pos]:
                visited[next_pos] = True
                q.append((next_pos, count+1))
print(answer)
            