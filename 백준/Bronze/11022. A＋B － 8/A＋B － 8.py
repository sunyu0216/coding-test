n = int(input())

for i in range(n):
    a, b = map(int, input().split())  # 공백 기준으로 나누고 각각 int로 변환
    
    c = a + b
    print(f"Case #{i+1}: {a} + {b} = {c}")