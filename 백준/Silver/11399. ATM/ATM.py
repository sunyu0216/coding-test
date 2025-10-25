num = int(input())
time = list(map(int, input().split()))
time.sort()

for t in range(len(time)):
    if t != 0:
        time[t] += time[t-1]

answer = 0
for t in time:
    answer += t
print(answer)