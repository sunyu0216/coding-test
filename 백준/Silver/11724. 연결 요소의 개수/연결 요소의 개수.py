import sys
input = sys.stdin.readline
n, m = map(int, input().split())
edges = [tuple(map(int, input().split())) for _ in range(m)]

# 연결리스트 생성
graph = [[] for _ in range(n + 1)]
for u, v in edges:
    graph[u].append(v)
    graph[v].append(u)

visited = [False] * (n + 1)
stack = []

# 함수 정의
def dfs(s):
    stack = [s]
    while stack:
      start = stack.pop()
      if visited[start]:
         continue
      visited[start] = True
      for edge in graph[start]:
          if not visited[edge]:
            stack.append(edge)

count = 0
for i in range(1, n+1):
   if not visited[i]: # 방문하지 않은 노드라면
      dfs(i)
      count += 1

print(count)
