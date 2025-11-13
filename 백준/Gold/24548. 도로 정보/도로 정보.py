from collections import defaultdict

N = int(input())
S = input().strip()

state_count = defaultdict(int)
state_count[(0,0,0,0)] = 1 # 초기 상태

count = {'T':0, 'G':0, 'F':0, 'P':0}
result = 0

for char in S:
    count[char] += 1

    #현재 상태
    state = (
        count['T'] % 3,
        count['G'] % 3,
        count['F'] % 3,
        count['P'] % 3
    )
    # 동일한 상태가 이전에 있었다면, 그 차이는 흥미로운 구간
    result += state_count[state]
    # 현재 상태를 해시 맵에 추가
    state_count[state] += 1

print(result)