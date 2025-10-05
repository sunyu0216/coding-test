def solution(numbers):
    a = len(str(max(numbers)))
    sortedNumber = sorted(numbers, key = lambda x:str(x)*a, reverse=True)

    answer = ''
    for i, n in enumerate(sortedNumber):
        if i == 0 and n == 0:
            answer = str(n)
            break
        else: answer = answer+str(n)

    return answer