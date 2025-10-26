num = int(input())
arr = []
for _ in range(num):
    a, b = map(int, input().split())
    arr.append((a, b))

arr.sort(key=lambda x: (x[1],x[0]))

start, end = arr[0]
count = 1

for a, b in arr[1:]:
    if end > a:
        continue
    else:
        start = a
        end = b
        count += 1

print(count)