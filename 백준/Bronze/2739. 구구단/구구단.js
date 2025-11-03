const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

const n = input[0];

for(let i=1; i<10; i++){
    console.log(n, "*", i, "=", n*i);
}