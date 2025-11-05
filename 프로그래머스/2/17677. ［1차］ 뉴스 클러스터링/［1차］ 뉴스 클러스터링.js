function solution(str1, str2) {
    var answer = 0;
    if (str1.trim()===" " && str2.trim()===" "){
        answer = 65536;
        return answer;
    }
    const lower1 = str1.toLowerCase();
    const lower2 = str2.toLowerCase();
    //console.log(lower1, lower2);
    
    const A = [];
    const B = [];
    for(let i=0; i<str1.length-1; i++){
        let word = lower1.slice(i,i+2);
        if (/^[A-Za-z]+$/.test(word)){
            A.push(word);
        };
    };
    for(let i=0; i<str2.length-1; i++){
        let word = lower2.slice(i,i+2);
        if (/^[A-Za-z]+$/.test(word)){
            B.push(word);
        };
    };
    
    // 교집합
    const bb = [...B];
    const intersection = [];
    for(const word of A){
        const idx = bb.indexOf(word);
        if (idx !== -1) {
            bb.splice(idx, 1);
            intersection.push(word);
        }
    }
    console.log(intersection);
    
    // 합집합
    for(const word of A){
        const idx = B.indexOf(word);
        if (idx !== -1) B.splice(idx, 1);
        
    }
    const union = [...A, ...B];
    console.log(union);
    
    if (union.length===0){
        answer = 65536;
    }
    else{
        answer = Math.floor((intersection.length / union.length) * 65536);
    }
    
    return answer;
}