const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split(/\s+/).map(Number);

const n = input[0];
const arr = input.slice(1, 1 + n);

// console.log(n);   // 5
// console.log(arr); // [2, 4, -10, 4, -9]

const unique = [... new Set(arr)].sort((a,b)=> a-b);
const rank = {};
unique.forEach((val, idx) => {
    rank[val] = idx
})

const result = arr.map(x => rank[x]);
console.log(result.join(' '));