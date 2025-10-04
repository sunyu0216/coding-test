from collections import deque

def solution(rectangle, characterX, characterY, itemX, itemY):
    
    # 좌표를 2배로 늘리기 위해 0~100 범위로 배열 생성
    graph = [[0 for _ in range(101)] for _ in range(101)]

    # 직사각형 처리
    for startX, startY, endX, endY in rectangle:
        # 좌표 2배 처리
        startX *= 2
        startY *= 2
        endX *= 2
        endY *= 2
        
        for x in range(startX, endX + 1):
            for y in range(startY, endY + 1):
                # 테두리
                if x in (startX, endX) or y in (startY, endY):
                    if graph[x][y] != -1:  # 내부가 아닌 경우만
                        graph[x][y] = 1
                else:
                    graph[x][y] = -1  # 내부

    # 캐릭터와 아이템 위치도 2배
    characterX *= 2
    characterY *= 2
    itemX *= 2
    itemY *= 2

    # 아이템 위치 표시
    graph[itemX][itemY] = -100

    # BFS 시작
    q = deque()
    q.append((characterX, characterY)) 
    
    # 위, 우, 하, 좌
    dx = [-1, 0, 1, 0]
    dy = [0, 1, 0, -1]
    
    # 방문 체크
    #visited = [[False for _ in range(101)] for _ in range(101)]
    #visited[characterX][characterY] = True
    
    while q:
        x, y = q.popleft()
        for d in range(4):
            newX = x + dx[d]
            newY = y + dy[d]
            
            if not (0 <= newX <= 100 and 0 <= newY <= 100):
                continue
            
            # 아이템 도착
            if graph[newX][newY] == -100:
                return graph[x][y] // 2  # 최종 거리 / 2로 원래 좌표 스케일 복원
            
            # 테두리 이동(아직 안밟은 테두리만)
            if graph[newX][newY] == 1:
                graph[newX][newY] = graph[x][y] + 1
                q.append((newX, newY))