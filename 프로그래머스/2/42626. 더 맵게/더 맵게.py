import heapq

def solution(scoville, K):
    heapq.heapify(scoville)
    answer = 0
    
    while len(scoville) > 1:
        a = heapq.heappop(scoville)
        if a >= K:
            return answer
            break
        else:
            b = heapq.heappop(scoville)
            newScoville = a + b*2
            heapq.heappush(scoville, newScoville)
            answer += 1
    
    c = heapq.heappop(scoville)
    if c >= K:
        return answer
    else: 
        answer = -1
        return answer