def solution(clothes):
    dict = {}
    for c in clothes:
        if c[1] not in dict:
            dict[c[1]] = [c[0]]
        else:
            dict[c[1]].append(c[0])
    
    combi = 1
    for i in dict:
        combi  = combi * (len(dict[i])+1)
    combi -= 1
    
    return combi