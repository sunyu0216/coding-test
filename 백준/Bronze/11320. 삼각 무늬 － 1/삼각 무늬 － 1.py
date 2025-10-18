n = int(input())

for i in range(n):
    a, b = map(int, input().split())
    if a%b == 0:
        c = a//b
        print(c**2)
    else:
        c = a//b + 1
        print(c**2)