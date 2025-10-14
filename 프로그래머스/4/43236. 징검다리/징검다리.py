def solution(distance, rocks, n):
    rocks.append(distance)
    rocks.sort()
    
    start, end = 0, distance
    mid = distance//2
    
    while start <= end:
        mid = (start+end) // 2
        removed = 0
        startRock = 0
        
        for i in range(len(rocks)):
            if rocks[i]-startRock < mid:
                removed += 1
            else:
                startRock = rocks[i]
        
        if removed <= n:
            answer = mid
            start = mid+1
        else:
            end = mid-1

    return answer