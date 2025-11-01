function solution(priorities, location) {
    var answer = 0;
    let now = 0;
    let loc = location;
    let q = [];
    
    while (loc !== -1){
        let currentTarget = Math.max(...priorities);
        now = priorities.shift();
        if(currentTarget !== now){ // 우선순위에 해당안하면
            priorities.push(now) // 다시 넣어주기
            if(loc === 0){
                loc = priorities.length -1;
            }
            else{
                loc -= 1;
            }
        }
        else{ //우선순위에 해당하면
            q.push(now);
            loc -= 1;
        }
    }
    answer += q.length;
    return answer;
}