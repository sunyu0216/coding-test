const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split('\n');

let lineIdx = 0;
// 한 줄 읽기 함수
const readLine = () => input[lineIdx++];

// 여러 숫자 공백 구분 읽기 함수
const readNums = () => readLine().split(' ').map(Number);

// --- 코드 시작 ---
const [r, c] = input[0].split(' ').map(Number);

// 주사위 면 정의
let top = 1, bottom = 6, left = 4, right = 3, front = 5, back = 2;
let answer = 1;

const roll_right = (count, rest) => {
  answer += 14 * count;
  for (let i = 0; i < rest; i++) {
    //console.log("오른쪽으로 한번이동")
    const new_top = left;
    left = 7 - top;
    right = top;
    top = new_top;
    answer += top;
    //console.log("탑", top);
    //console.log("오른쪽, 왼쪽", right, left);
  }
};

const roll_left = (count, rest) => {
  answer += 14 * count;
  for (let i = 0; i < rest; i++) {
    //console.log("왼쪽으로 한번이동")
    const new_top = right;
    right = 7 - top;
    left = top;
    top = new_top;
    answer += top;
    //console.log("탑", top);
    //console.log("오른쪽, 왼쪽", right, left);
  }
};

const roll_down = () => {
    //console.log("아래로 한번이동")
    const new_top = front;
    front = 7 - top;
    back = top;
    top = new_top;
    answer += top;
    //console.log("탑", top);
    //console.log("위, 아래", front, back);
};

for (let i = 0; i < r; i++) {
  let share = Math.floor((c-1) / 4);
  let rest = (c - 1) % 4;

  if (i === 0) { // 첫 줄
    //console.log("몫", share, "나머지", rest);
    roll_right(share, rest);
    roll_down();
  } else {
    if (i % 2 === 0) {
      roll_right(share, rest);
    } else {
      roll_left(share, rest);
    }
    if (i !== r - 1) roll_down();
    //console.log("아래로 굴린 뒤 답", answer);
  }
}

console.log(answer);