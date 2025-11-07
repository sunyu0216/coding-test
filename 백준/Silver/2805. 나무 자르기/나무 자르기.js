const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

const [N, M] = input[0].split(" ").map(Number);
const arr = input[1].split(" ").map(Number);

let start = 0
let end = Math.max(...arr);
let mid = Math.floor((start+end)/2);

let count = 0
let answer = 0

while (start<=end) {
    count = 0
    mid = Math.floor((start+end)/2);
    for(const value of arr){
        if (value > mid) count += (value - mid);
    }
    if (count >= M) {
        // 너무 많이 잘림 -> 더 많이 잘렸다는 건 더 높여도 됨 → mid 올림
        answer = mid;
        start = mid + 1;
    } else {
        end = mid - 1;
    }
}

console.log(answer);