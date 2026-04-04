class Solution {
    public int solution(String s) {
        if (s.length() == 1) return 1;
        
        int answer = s.length();
        
        for(int cut=1; cut<=s.length()/2; cut++){
            StringBuilder compress = new StringBuilder();
            String target = s.substring(0, cut);
            int count = 1;
            
            // 2. 컷 단위만큼 건너뛰며 비교 시작
            for(int i=cut; i<s.length(); i+=cut){
                int endIdx = Math.min(i+cut, s.length());
                String next = s.substring(i, endIdx);
                
                if(target.equals(next)){
                    count++;
                }else{
                    if (count > 1) {
                        compress.append(count);
                    }
                    compress.append(target);
                    
                    target = next;
                    count = 1;
                }
            }
            // 3. 남은 문자열 처리 (target에 남아있던 마지막 조각 추가)
            if(count > 1){
                compress.append(count);
            }
            compress.append(target);
            
            answer = Math.min(answer, compress.length());
            
            //System.out.println(compress);
        }
        return answer;
    }
}