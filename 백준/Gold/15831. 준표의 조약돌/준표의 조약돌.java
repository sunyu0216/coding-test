import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int total_shingleNum = Integer.parseInt(st.nextToken());
        int blackShingle_max = Integer.parseInt(st.nextToken());
        int whiteShingle_min = Integer.parseInt(st.nextToken());

        char[] shingles = br.readLine().toCharArray();
        int startPointer=0;
        int endPointer=0;
        int blackCount=0;
        int whiteCount=0;
        int max_length = 0;

        while(endPointer<total_shingleNum){
            if(shingles[endPointer] == 'B'){
                blackCount++;
            }else{
                whiteCount++;
            }
            //System.out.print(shingles[endPointer]);

            // 1. 검은 조약돌 너무 많은 경우 -> startPointer 이동
            while(blackCount>blackShingle_max){
                if(shingles[startPointer] == 'B'){
                    blackCount--;
                }else{
                    whiteCount--;
                }
                startPointer++; // 어쨌든 검은 조약돌이 많은 상황이니까 무조건 증가
            }

            endPointer++; // 구간계산용 + 한칸 전진

            // 2. 흰색 조약돌 조건 맞는지 체크
            if(whiteCount >= whiteShingle_min){
                max_length = Math.max(max_length, endPointer-startPointer);
            }
        }
        System.out.println(max_length);    
    }
}
