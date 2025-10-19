# 최대공약수 공식
def gcd(a, b):
    while b:
        a, b = b, a % b
    return a

# 최소공배수 공식
def lcm(a, b):
    c = a * b // gcd(a, b)
    return c

a, b = map(int, input().split())

print(gcd(a, b))
print(lcm(a, b))

