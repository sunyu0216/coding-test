from collections import deque

N, K = map(int, input().split())

def bfs():
  q = deque()
  q.append((N, 0))
  visited = [False] * 100001
  visited[N] = True
  answer = 1000000

  while q:
    cur, time = q.popleft()
    if cur == K:
      answer = min(answer, time)
    else:
      for next_step in (cur-1, cur+1, cur*2):
        if 0<= next_step <= 100000 and not visited[next_step]:
          visited[next_step] = True
          q.append((next_step, time+1))
  return answer

print(bfs())