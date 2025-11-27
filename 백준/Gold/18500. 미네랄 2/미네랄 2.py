from collections import defaultdict

R, C = map(int, input().split())
cave = [[] for _ in range(R)]

for i in range(R):
    info = input()
    for j in range(C):
        cave[i].append(info[j])

N = int(input())
H = list(map(int, input().split()))

# 클러스터 찾기 함수
def find_cluster(x, y, visited):
    dx = [-1, 0, 1, 0]
    dy = [0, 1, 0, -1]
    cluster = [(x,y)]
    stack = [(x,y)]

    while stack:
        cx, cy = stack.pop()
        for r in range(4):
            newX = dx[r] + cx
            newY = dy[r] + cy

            if 0<=newX<R and 0<=newY<C and not visited[newX][newY] and cave[newX][newY] == 'x':
                stack.append((newX, newY))
                cluster.append((newX, newY))
                visited[newX][newY] = True
    #print("이번클러스터 좌표들", cluster)
    return cluster

# 중력으로 떨어지기 함수
def apply_gravity(arr):
    # grouped = defaultdict(list)
    # flag = False
    # count = 0

    for ax, ay in arr: # 일단 이 클러스터 잠깐 없애기
        cave[ax][ay] = '.'

    max_fall = int(1e9)
    for ax, ay in arr:
        fall_count = 0
        nx = ax+1

        while 0<=nx<R and cave[nx][ay] == '.':
            fall_count += 1
            nx += 1
        max_fall = min(fall_count, max_fall)
    # print("************************************")
    # print("이 클러스터는 총", max_fall, "번 내릴 수 있음")
    # print("************************************")

    # 다시 클러스터 떨어진만큼 원상복구
    # print("*******실제 중력으로 내리기*******")
    for ax, ay in arr:
        cave[ax+max_fall][ay] = 'x'


currentH = 0
for idx, h in enumerate(H):
    #print("현재높이", h)
    currentH = R-h
    flag = False

    for j in range(C): #한줄 순회하면서 미네랄 찾기
        if idx%2 == 0:
            if cave[currentH][j] == 'x': #미네랄 찾았으면 없애기
                #print("왼쪽으로 던져서 미네랄 하나 없앰")
                cave[currentH][j] = '.'
                flag = True
                break
        else:
            if cave[currentH][C-1-j] == 'x': #미네랄 찾았으면 없애기
                #print("오른쪽으로 던져서 미네랄 하나 없앰")
                cave[currentH][C-1-j] = '.'
                flag = True
                break

    if flag:
        count = 0
        visited = [[False for _ in range(C)] for _ in range(R)]
        save_cluster = []
        #print(visited)
        for i in range(R):
            for j in range(C):
                if cave[i][j] == 'x' and not visited[i][j]:
                    visited[i][j] = True
                    c = find_cluster(i,j, visited)
                    save_cluster.append(c)
                    #print(save_cluster)
                    count += 1
                else:
                    visited[i][j] = True

        for c in save_cluster:
            c.sort(reverse = True)
            #print(c)
            a, b = c[0]
            if not(a==R-1): # 땅에서 떨어져있으면 중력가동 시작
                apply_gravity(c)

for i in range(R):
    for j in range(C):
        print(cave[i][j], end='')
    print()