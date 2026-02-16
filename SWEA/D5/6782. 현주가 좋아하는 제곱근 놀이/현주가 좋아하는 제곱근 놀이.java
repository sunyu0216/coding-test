import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author seonyu
 * SWEA 6782. 현주가 좋아하는 제곱근 놀이
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 개수 T
 *  1-2. 2이상의 어떤 정수 Num
 *
 * 2. 2가 될때까지
 *  2-1. Num의 제곱근이 정수인지 확인,
 *      2-1-1. 맞다면 제곱근으로 Num을 교체, count +1
 *      2-1-2. 아니라면 Num 제곱근의 정수 부분 +1 한 값이 되도록
 *          count += (Num 제곱근의 정수 부분 +1) * (Num 제곱근의 정수 부분 +1) - Num + 1
 *          Num += Num 제곱근의 정수 부분 +1
 *  2-2. 반복
 *
 */
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){
            long Num = Long.parseLong(br.readLine());

            long count = 0;
            // 2. 2가 될때까지
            while(Num!=2){
                // 2-1. Num의 제곱근이 정수인지 확인,
                long result = (long)Math.sqrt(Num);
                if(result*result == Num){
                    // 2-1-1. 맞다면 제곱근으로 Num을 교체, count +1
                    //System.out.println(result+"제곱근이 정수입니다.");
                    Num = result;
                    count++;
                }else {
                    // 2-1-2. 아니라면 Num 제곱근의 정수 부분 +1 한 값이 되도록
                    //System.out.println(result+"제곱근은 바로 Num이 아니므로 계산로직");
                    count += ((result + 1) * (result + 1)) - Num + 1;
                    Num = result + 1;
                }
            }
            System.out.println("#"+t+" "+count);
        }
    }
}
