import sys
import heapq

input = sys.stdin.readline

n = int(input())
arr = []

for _ in range(n):
    num = int(input())
    
    if num == 0:
       if arr:
            nn = heapq.heappop(arr)
            print(nn)
       else:
            print(0)
    else:
        heapq.heappush(arr, num)
        