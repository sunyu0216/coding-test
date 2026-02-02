"""
    def solution 
        n : 지점 갯수
        s : 출발지점
        a : A 의 도착지점
        b : B 의 도착지점
        fares : 지점 사이 요금[출발지점 , 끝지점 , 요금]
    
    def make_graph(n,fares)
        1. 주어진 2차원 배열을 그래프로 만드는 함수
        2. n으로 기본 배열로 초기화후 fares 의 값을 하나씩 넣는다
    def dijstra(s, e, graph)
        1. 다익스트라 진행
    
    def carwith (s,a,b,graph)
        1. s에서 환승을 시작하므로 s -> 어느지점까지 환승할지 정한다
        2. 그 지점에서 a , b 지점까지 최소값을 구하고 3개의 배열을 구한다.
"""
import heapq
def dijkstra(s,graph):
    # 초기화
    distances = [float('inf')] * (len(graph)+1)
    distances[s] = 0
    
    queue = []
    # (비용, 노드) 순서로 힙에 삽입
    heapq.heappush(queue, (0, s))
    
    while queue:
        # 최소값을 뽑는다
        c_dist , c_node = heapq.heappop(queue)
        # 만약 
        if distances[c_node] < c_dist:
            continue
        # 3. 인접 노드 탐색 및 비용 갱신
        for n_node, weight in graph[c_node]:
            cost = c_dist + weight
            
            # 더 저렴한 경로를 발견하면 테이블 갱신 & 큐에 추가
            if cost < distances[n_node]:
                distances[n_node] = cost
                heapq.heappush(queue, (cost, n_node))
    return distances

def carwith (n,s,a,b,graph):
    min_cost = float('inf')
    # 1. 시작지점에서 카풀 까지 가는 최소 비용
    s_dijkstra = dijkstra(s, graph)
    # 2. 카풀 지점에서 A의 집으로 가는 최소 비용
    a_dijkstra = dijkstra(a, graph)
    # 3. 카풀 지점에서 B 의 집으로 가는 최소 비용
    b_dijkstra = dijkstra(b, graph)
    for i in range(n):
        target = i+1
        total_cost = s_dijkstra[target]+a_dijkstra[target]+b_dijkstra[target]
        
        min_cost = min(min_cost,total_cost)
    return min_cost

def make_graph(n,fares):
    graph = {}
    for i in range(n):
        graph[i+1] = []
    for i in range(len(fares)):
        graph[fares[i][0]].append((fares[i][1],fares[i][2]))
        graph[fares[i][1]].append((fares[i][0],fares[i][2]))
    return graph

def solution(n, s, a, b, fares):
    answer = 0
    
    graph = make_graph(n,fares)
    answer = carwith(n,s, a, b,graph)
    return answer