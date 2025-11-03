const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

const [n, m] = input[0].split(" ").map(Number);

const nameToNum = new Map();
const numToName = Array(n+1);

for (let i = 1; i < n+1; i++) {
    let pocketmonName = input[i]
    nameToNum.set(pocketmonName, i);
    numToName[i] = pocketmonName;
};

for (let j = 0; j < m; j++){
    let value = input[n+1+j]
    if (isNaN(value)){// 문자열이면 숫자 출력
        console.log(nameToNum.get(value));
    }
    else{// 숫자이면 이름 출력
        console.log(numToName[Number(value)]);
    }
};