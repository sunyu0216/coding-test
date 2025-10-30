N = input()

# 9 이하일 경우 스티커 세트 1개
if int(N) <= 9:
    print(1)
else:
    # N과 같은 자릿수의 1로만 이루어진 수
    one_num = int("1" * len(N))
    if int(N) < one_num:
        print(len(N) - 1)
    else:
        print(len(N))