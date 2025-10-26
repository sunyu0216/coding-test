from collections import deque

def solution(n, edge):
    dist = [999 for _ in range(n+1)]
    dist[1] = 0
    graph = [[] for _ in range(n+1)]
    
    q = deque()
    edge.sort(key=lambda x: (x[0],x[1]))
    
    for a,b in edge:
        graph[a].append(b)
        graph[b].append(a)
        
    q.append(1)
    while q:
        now = q.popleft()
        for nxt in graph[now]:
            if dist[nxt]==999: # 아직 방문하지 않은 노드
                #print("0인거리", n)
                dist[nxt] = dist[now] +1
                q.append(nxt)
    
    answer = dist.count(max(dist[1:]))
    return answer