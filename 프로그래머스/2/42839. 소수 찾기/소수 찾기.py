from itertools import permutations

def is_prime(n):
        if n < 2:
            return False
        for i in range(2, n):
            if n % i == 0:
                return False
        return True
    
def solution(numbers):
    
    num_list = list(numbers)
    s = []
    for i in range(1, len(num_list)+1):
        for p in permutations(num_list, i):
            s.append(int("".join(p)))
    all_number = list(set(s))
    
    answer = 0
    for a in all_number:
        if is_prime(a):
            answer += 1

    return answer