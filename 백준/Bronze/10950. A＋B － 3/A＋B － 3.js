const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

const num = input[0]

for(let i=1; i<=Number(num); i++){
    const [a, b] = input[i].split(" ").map(Number);
    console.log(a+b);
}