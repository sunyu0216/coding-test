function solution(bridge_length, weight, truck_weights) {
    var answer = 0;
    let bridge = Array(bridge_length).fill(0);
    let time = 0;
    let weight_sum = bridge.reduce((arr, cur) => arr+cur, 0);
    
    while(bridge.length > 0){
        //console.log("---------------");
        time += 1;
        //console.log(time, "초");
        bridge.shift();
        if (truck_weights.length > 0){
            weight_sum = bridge.reduce((arr, cur) => arr+cur, 0);
            if(weight_sum + truck_weights[0] <= weight){
                //console.log("추가되는 트럭 무게", truck_weights[0]);
                bridge.push(truck_weights.shift());
            }
            else{
                //console.log("무게 도달")
                bridge.push(0);
            }
        }
    }
    return time;
}