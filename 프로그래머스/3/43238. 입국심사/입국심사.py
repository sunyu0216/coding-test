def solution(n, times):
    # 초기값
    start = min(times)
    end = min(times)*n
    mid = start+((end-start)//2)
    answer = 0
    
    def calPeople(times, mid):
        currentPeople = 0
        for t in times:
            currentPeople += mid//t

        return currentPeople
    
    while start<=end:
        currentPeople = calPeople(times, mid)
            
        print("mid 값: ", mid)
        print("현재 가능한 사람 수: ", currentPeople)
        if currentPeople >= n:
            answer = mid
            end = mid - 1
        elif currentPeople < n:
            start = mid + 1
        mid = start+((end-start)//2)

    return answer