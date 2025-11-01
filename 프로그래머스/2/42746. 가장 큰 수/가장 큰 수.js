function solution(numbers) {
    var answer = '';
    let a = String(Math.max(numbers)).length
    const sortedNumbers = numbers.sort((x, y) => {
        const sx = String(x).repeat(a);
        const sy = String(y).repeat(a);
        return sy.localeCompare(sx); // 내림차순
    });
    //console.log(sortedNumbers)
    for(const [idx, val] of sortedNumbers.entries()){
        if (val === 0 && idx === 0){
            answer += "0"
            break
        }
        else{
            answer += String(val)
        }
    }
    return answer;
}