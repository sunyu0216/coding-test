function solution(n, results) {
    var answer = 0;
    const ranking = Array.from({length: n+1}, ()=> Array(n+1).fill(999));
    
    for(const r of results){
        const [a, b] = r;
        ranking[a][b] = 1;
        ranking[b][a] = -1;
    }
    for(let i=1; i<n+1; i++){
        ranking[i][i] = 0;
    }
    for(let k=1; k<n+1; k++){
        for(let i=1; i<n+1; i++){
            for(let j=1; j<n+1; j++){
                if(ranking[i][k]===-1 && ranking[k][j]===-1){
                    ranking[i][j] = -1;
                }
                else if(ranking[i][k]===1 && ranking[k][j]===1){
                    ranking[i][j] = 1;
                }
            }
        }
    }
    for(let i=1; i<n+1; i++){
        const runner = ranking[i].slice(1,n+1);
        //console.log(runner)
        if(runner.includes(999)){ // 문법주의 -> js 에서는 in을 쓰면 인덱스가 맞냐는 의미
            //console.log("999가 있음");
            continue;
        }
        else{
            answer += 1
        }
    }
    return answer;
}