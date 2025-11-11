const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

let N = input[0];
const paper = input.slice(1).map(line => line.split(" ").map(Number));

let white = 0;
let blue = 0;

function isSameColor(x, y, size){
    const color = paper[x][y];
    for (let i = x; i < x + size; i++) {
      for (let j = y; j < y + size; j++) {
        if (paper[i][j] !== color) return false;
      }
    }
    return true;
}

function devide(x,y,size){
    if(isSameColor(x,y,size)){
        if (paper[x][y] === 0){
            white += 1;
        }
        else{
            blue += 1;
        }
        return;
    }
    else{
        const half = size/2;
        devide(x,y, half);
        devide(x,y+half, half);
        devide(x+half,y, half);
        devide(x+half,y+half, half);
    }
}
devide(0,0,N);
console.log(white);
console.log(blue);