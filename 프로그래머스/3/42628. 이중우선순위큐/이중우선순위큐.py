import heapq

def solution(operations):
    heap = []
    temp = []
    for o in operations:
        operation, number = o.split(" ")
        if operation == "I":
            heapq.heappush(heap, int(number))
            heapq.heappush(temp, -int(number))
        elif heap:
            if number == "-1": # 최솟값 삭제
                n = heapq.heappop(heap)
                temp.remove(-n)
                heapq.heapify(temp)
            else: # 최댓값 삭제
                n = heapq.heappop(temp)
                heap.remove(-n)
                heapq.heapify(heap)
    
    if heap:
        mmin = heapq.heappop(heap)
        mmax = heapq.heappop(temp)
        return [-mmax,mmin]
    else:
        return [0,0]