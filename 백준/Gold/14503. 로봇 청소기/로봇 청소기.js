const fs = require("fs");
const input = fs.readFileSync(0, "utf8").trim().split("\n");

const [N, M] =  input[0].split(" ").map(Number);
let [robotX, robotY, direction] = input[1].split(" ").map(Number);
const Map  = [];
for(let i=0; i<N; i++){
    Map.push(input[i+2].split(" ").map(Number))
}
// 주변칸 탐색에 사용
const dx = [-1, 0, 1, 0]; // 위 오 아 왼
const dy = [0, 1, 0, -1];
// 후진에 사용
const backMoveX = [1, 0, -1, 0];
const backMoveY = [0, -1, 0, 1];
// 값체크에 사용
let flag = 0, newX = 0, newY = 0, answer = 0;

while(true){
    // 1. 현재 로봇 청소기 위치 청소
    if(Map[robotX][robotY] === 0){
        Map[robotX][robotY] = -1;
        answer += 1;
    }
    flag = 0
    for(let i=0; i<4; i++){
        newX = dx[i] + robotX;
        newY = dy[i] + robotY;
        if(newX >= 0 && newX < N &&
           newY >= 0 && newY < M && 
           Map[newX][newY] === 0){// 청소가 안되어 있는 칸이 있음을 표시
            flag = 1;
            break;
        }
    }
    // 2. 청소가 안되어 있는 칸이 주변에 있다면 회전 후 전진
    if(flag === 1){
        while(true){
            // 1) 반시계 90도 회전
            if(direction === 0){
                direction = 3;
            }
            else{
                direction -= 1;
            }
            
            // 2) 회전한 칸이 청소가 안되어 있다면 전진, 청소가 되어있으면 다시 회전
            newX = dx[direction] + robotX;
            newY = dy[direction] + robotY;
            if(Map[newX][newY] === 0){
                robotX = newX;
                robotY = newY;
                break;
            }
            else{
                continue;
            }
        }
    }
    // 3. 청소가 안되어 있는 칸이 주변에 없다면 한칸씩 후진
    else{
        newX = backMoveX[direction] + robotX;
        newY = backMoveY[direction] + robotY;
        if(Map[newX][newY] !== 1){// 1) 벽에 안부딪히면 후진
            robotX = newX;
            robotY = newY;
        }
        else{// 2) 벽에 부딪히면 작동 종료
            break;   
        }
    }
}
console.log(answer);