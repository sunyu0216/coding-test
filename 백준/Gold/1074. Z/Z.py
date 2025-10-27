N, r, c = map(int, input().split())

answer = 0
# 순서대로 1, 2, 3, 4사분면
graph = [1, 0, 2, 3]

for n in range(N, 0, -1):
    currentNum = 2**(2*(n-1))
    standard = 2**(n-1) -1
    standardNum = 0
    if r<= standard: # 1,2사분면
        if c<= standard:
            #print("2사분면")
            standardNum = currentNum * 0
        else:
            #print("1사분면")
            standardNum = currentNum * 1
            c = c-(standard+1)
    else:
        if c<= standard:
            #print("3사분면")
            standardNum = currentNum * 2
            r = r-(standard+1)
        else:
            #print("4사분면")
            standardNum = currentNum * 3
            r = r-(standard+1)
            c = c-(standard+1)
    answer += standardNum
print(answer) 
        