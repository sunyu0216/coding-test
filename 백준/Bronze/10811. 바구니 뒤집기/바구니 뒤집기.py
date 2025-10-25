N, M = map(int, input().split())

buckets = [i for i in range(1, N+1)]
# buckets = list(range(1, N+1))

for m in range(M):
    start, end = map(int, input().split())
    change = buckets[start-1:end]
    buckets = buckets[:start-1]+change[::-1]+buckets[end:len(buckets)]
for b in buckets:
    print(b, end=' ')