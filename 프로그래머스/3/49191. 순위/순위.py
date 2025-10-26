def solution(n, results):
    fw = [[999 for _ in range(n+1)]for _ in range(n+1)]
    for a, b in results:
        fw[a][b] = 1
        fw[b][a] = -1
    for n in range(1, n+1):
        fw[n][n] = 0
    
    for k in range(1, n+1):
        for i in range(1, n+1):
            for j in range(1, n+1):
                if fw[i][k] == -1 and fw[k][j] == -1:
                    fw[i][j] = -1
                elif fw[i][k] == 1 and fw[k][j] == 1:
                    fw[i][j] = 1
                    
    answer = 0               
    for n in range(1, n+1):
        if 999 not in fw[n][1:]:
            answer +=1
    return answer