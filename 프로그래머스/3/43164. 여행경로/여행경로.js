function solution(tickets) {
    var answer = [];
    
    // 0. 티켓 정렬
    tickets.sort((a,b)=>{
        if(a[0]===b[0]){
            return a[1].localeCompare(b[1]);
        }
        return a[0].localeCompare(b[0]);
    });
    //console.log(tickets)
    
    // 1. 연결리스트 만들기
    let graph = [];
    for(const [a,b] of tickets){
        if (!(a in graph)){
            graph[a] = []
        }
        graph[a].push(b)
    }
    console.log(graph)
    
    // 3. dfs 함수
    let route = [];
    const dfs = (airport) => {
        if (!(airport in graph)||graph[airport].length === 0){
            route.push(airport);
            return
        }
        while (graph[airport].length > 0){
            next_airport = graph[airport].shift();
            dfs(next_airport)
        }
        route.push(airport);
    }
    dfs("ICN");
    answer = route.slice().reverse();
    return answer;
}