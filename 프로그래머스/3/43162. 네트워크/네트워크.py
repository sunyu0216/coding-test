def dfs(computers, v, n, visited):
    visited[v] = True
    print("방문한 노드", v+1)
    
    for i in range(n):
        if computers[v][i] == 1 and not visited[i]:
            print("방문한 노드와 연결된 노드", i+1)
            dfs(computers, i, n, visited)
            
            
def solution(n, computers):            
    visited = [False] * (n)
    count = 0
    
    for j in range(n):
        if not visited[j]:
            dfs(computers, j, n, visited)
            print("네트워크 끝", count+1)
            count += 1
        
    return count