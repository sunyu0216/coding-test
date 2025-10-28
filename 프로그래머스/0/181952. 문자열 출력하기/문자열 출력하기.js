const readline = require('readline'); // 모듈 가져오기
const rl = readline.createInterface({ // 입출력 인터페이스 만들기
    input: process.stdin,
    output: process.stdout
});

let input = []; //입력받을 배열

rl.on('line', function (line) {
    input = [line];
}).on('close',function(){
    str = input[0];
    console.log(str)
});
