import sys
input = sys.stdin.readline

a, b = map(int, input().split())
arr = set()
see = []

for i in range(a):
    s = input().strip()
    arr.add(s)
    
for j in range(b):
    ss = input().strip()
    if ss in arr:
        see.append(ss)

see.sort()
print(len(see))
for i in range(len(see)):
    print(see[i])