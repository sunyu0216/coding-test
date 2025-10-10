from itertools import groupby

def solution(numbers):
    arr = [2**i -1 for i in range(1,7)]
    binaryNum = []
    # 이진수 변환 및 길이검사
    for n in numbers:
        b = bin(n)[2:]
        #print(b)
        for a in range(len(arr)):
            # if a == 0 and len(b) <= arr[a]:
            #     # padding 처리
            #     length = '0'*(arr[a]-len(b))
            #     b = length+b
            #     binaryNum.append(b)
            if arr[a] < len(b):
                continue
            # elif arr[a-1] < len(b) <= arr[a]: # 길이를 맞춰줘야 함.
            #     length = '0'*(arr[a]-len(b))
            #     b = length+b
            #     binaryNum.append(b)
            #     break
            length = '0' * (arr[a]-len(b))
            b = length+b
            binaryNum.append(b)
            break
    
    # 중심찾기 함수
    def findCenter(binary):
        if len(binary) == 1:
            if binary == "1":
                return 1
            elif binary == "0":
                return 0
        else:
            mid = len(binary)//2 + 1
            #print(binary[mid-1:mid])
            
            # 자식들 중심 검사
            left = binary[:mid-1]
            leftRe = findCenter(left)
            # 자식이 0이라면 상관없음.
            # 자식이 1이라면 중심도 반드시 1일것.
            if leftRe == -1: # 이미 실패
                return -1
            if leftRe == 1:
                if binary[mid-1:mid] == "0":
                    #print("자식에 1이 있는데 중심이 0임")
                    return -1
            
            right = binary[mid:]
            rightRe = findCenter(right)
            #print("현재 오른쪽 자식값: ", rightRe, type(rightRe))
            # 자식이 0이라면 상관없음.
            # 자식이 1이라면 중심도 반드시 1일것.
            if rightRe == -1:
                return -1
            if rightRe == 1:
                #print("오른쪽값 1 걸림")
                if binary[mid-1:mid] == "0":
                    #print("자식에 1이 있는데 중심이 0임")
                    return -1

            # 모두 다 통과하면
            #print("이번 덩어리 통과~ 중심값: ", binary[mid-1:mid])
            return int(binary[mid-1:mid])
        

    answer = []
    for binary in binaryNum:
        if len(binary) == 1:
            answer.append(1)
            continue
        elif len(binary) == 3:
            mid = len(binary)//2 + 1
            if binary[mid-1:mid] == "0":
                answer.append(0)
                continue
            else:
                answer.append(1)
                continue
        # 최초 중심값 구해서 0인지 체크
        mid = len(binary)//2 + 1
        if binary[mid-1:mid] == "0":
            answer.append(0)
        else:
            m = binary[mid-1:mid]

            # 왼쪽덩어리
            result = findCenter(binary[:mid-1])
            if result == 1 and m == "0":
                answer.append(0)
            elif result == -1:
                answer.append(0)
            else:
                # 오른쪽 덩어리
                #print("오른쪽덩어리시작")
                r = findCenter(binary[mid:])
                if r == 1 and m == "0":
                    answer.append(0)
                elif r == -1:
                    answer.append(0)
                else:
                    answer.append(1)

    return answer