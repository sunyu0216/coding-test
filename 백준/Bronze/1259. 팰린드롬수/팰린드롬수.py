while True:
    num = list(input().strip())
    if num[0] == "0":
            break
    else:
        answer = "yes"
        for i in range(len(num)//2):
            if num[i] == num[len(num)-1 -i]:
                answer = "yes"
                continue
            else:
                answer = "no"
                break    
        print(answer)