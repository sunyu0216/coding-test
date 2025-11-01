function solution(begin, target, words) {
    if (!(words.includes(target))) return 0;
    
    function diffCount(a, b){
        let count = 0;
        for(let i=0; i<a.length; i++){
            if(a[i] !== b[i]) count++;
        }
        return count;
    };
    
    let q = [[begin, 0]];
    let visited = Array(words.length).fill(false);
    
    while (q.length > 0){
        let [start, count] = q.shift();
        console.log(start)
        if (start === target){
            return count;
        }
        for(let i=0; i<words.length; i++){
            if (!(visited[i]) && diffCount(start, words[i])===1){
                q.push([words[i], count +1]);
                visited[i] = true;
            }
        }
    }
    return 0;
}