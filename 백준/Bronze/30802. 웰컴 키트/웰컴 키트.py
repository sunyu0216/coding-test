people = int(input())
size = list(map(int, input().split()))
shirt, pan = map(int, input().split())

countS = sum((s + shirt - 1) // shirt for s in size)

rest = people%pan
countP = people//pan

print(countS)
print(countP, rest)