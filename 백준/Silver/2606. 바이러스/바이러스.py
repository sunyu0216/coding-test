computer = int(input())
network = int(input())

networks = [[] for _ in range(computer + 1)]
visited = [False] * (computer + 1)

for _ in range(network):
    a, b = map(int, input().split())
    networks[a].append(b)
    networks[b].append(a)

def dfs(start, answer):
    visited[start] = True
    for net in networks[start]:
        if not visited[net]:
            answer += 1
            answer = dfs(net, answer)
    return answer

answer = dfs(1, 0)

print(answer)