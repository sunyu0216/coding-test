class Solution {
    static int[] number;
    static int targetNum;
    static int count;
    
    public int solution(int[] numbers, int target) {
        count = 0;
        number = numbers;
        targetNum = target;
        
        // 0번 인덱스부터 시작, 현재 합계는 0
        dfs(0, 0);
        
        int answer = count;
        return answer;
    }
    
    public void dfs(int idx, int currNum){
        if(idx == number.length){
            if(currNum == targetNum){
                count++;
            }
            return;
        }
        
        dfs(idx+1, currNum + number[idx]);
        dfs(idx+1, currNum - number[idx]);
    }
}