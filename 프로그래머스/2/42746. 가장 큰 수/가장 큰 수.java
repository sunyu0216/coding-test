import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        // 1. 스트링으로 변환하기
        String[] strs = new String[numbers.length];
        for(int i=0; i<numbers.length; i++){
            strs[i] = String.valueOf(numbers[i]);
        }
        
        // 2. 정렬시키기 -> 무조건 맨 앞자리가 큰 순서대로, 만약 맨 앞자리가 같다면 그 다음 자리를 비교하는 방식
        Arrays.sort(strs, (a, b)->{
            return (b+a).compareTo(a+b);
        });
        
        String answer = "";
        
        if(strs[0].equals("0")){
            answer = "0";
            return answer;
        }else{
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<strs.length; i++){
                sb.append(strs[i]);
            }
            return sb.toString();
        }
    }
}