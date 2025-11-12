function solution(money) {
    const n = money.length;
    const dp1 = new Array(n).fill(0);
    const dp2 = new Array(n).fill(0);
    // 1. 첫번째 집을 털 수 있는 경우
    // const dp1 = [];
    // dp1.push(...money.slice(0,money.length-1)); // 마지막 집 제외
    // dp1.push(0);
    
    // case 1: 첫 집 포함, 마지막 집 제외
    dp1[0] = money[0];
    dp1[1] = Math.max(money[0], money[1]);
    for (let i = 2; i < n - 1; i++) {
        dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + money[i]);
    }
    
    // const dp2 = []; // 첫번째 집 제외
//     dp2.push(0);
//     dp2.push(...money.slice(1, money.length));
    
//     dp1[1] = Math.max(dp1[0], dp1[1]);
//     console.log(dp1[1]);
    
    // case 2: 첫 집 제외, 마지막 집 포함
    dp2[0] = 0;
    dp2[1] = money[1];
    for (let i = 2; i < n; i++) {
        dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + money[i]);
    }
    
    // for(let i=2; i<money.length; i++){
    //     dp1[i] = Math.max(dp1[i-2]+dp1[i], dp1[i-1])
    //     dp2[i] = Math.max(dp2[i-2]+dp2[i], dp2[i-1])
    //     // console.log(dp);
    // }
    // for (let i = 2; i < dp1.length; i++) {
    //   dp1[i] = Math.max(dp1[i-2] + dp1[i], dp1[i-1]);
    // }
    // for (let i = 2; i < dp2.length; i++) {
    //   dp2[i] = Math.max(dp2[i-2] + dp2[i], dp2[i-1]);
    // }
    return Math.max(dp1[n - 2], dp2[n - 1]);
}