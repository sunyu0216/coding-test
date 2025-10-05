def solution(tickets):
    # 1. 그래프 만들기
    graph = {}
    for a,b in tickets:
        if a not in graph:
            graph[a] = []
        graph[a].append(b)
    
    # 2. 사전 순으로 정렬
    sorted_graph = dict(sorted(graph.items(), key=lambda x: (x[0])))
    for key in graph:
        graph[key].sort()
        
    """for key, value in sorted_graph.items():
        print(key, value)"""
        
    route = []  # 최종 경로

    def dfs(airport):
        # 현재 공항에서 가능한 목적지가 없을 때
        if airport not in sorted_graph or not sorted_graph[airport]:
            route.append(airport)
            return
        while sorted_graph[airport]:
            next_airport = sorted_graph[airport].pop(0)  # 작은 알파벳 우선
            dfs(next_airport)
        route.append(airport)
    
    dfs("ICN")
    return route[::-1]  # 역순 반환 (DFS 종료 시점 순서이기 때문)