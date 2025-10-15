import heapq
import sys
input = sys.stdin.readline

n = int(input())
min_heap = []
max_heap = []

answer = []

for i in range(n):
  num = int(input())
  if i == 0:
    heapq.heappush(min_heap, -num)
    answer.append(-min_heap[0])
  else:
    if num > -min_heap[0]: 
      heapq.heappush(max_heap, num)
    else:
      heapq.heappush(min_heap, -num)

    if len(min_heap) - len(max_heap) > 1:
      val = -heapq.heappop(min_heap)
      heapq.heappush(max_heap, val)
    elif len(max_heap) > len(min_heap):
      val = heapq.heappop(max_heap)
      heapq.heappush(min_heap, -val)
    
    answer.append(-min_heap[0])

    #print("현재 min: ", min_heap)
    #print("현재 max: ", max_heap)

#print("-----------------------")
for i in answer:
  print(i)