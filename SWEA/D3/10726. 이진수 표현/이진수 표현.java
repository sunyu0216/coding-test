import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * SWEA 10726. 이진수 표현
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 입력받기
 *  1-2. N과 M을 int로 입력받기 -> M의 최대값이 10의 8승이므로 1억, int는 21억까지 가능
 *
 * 2. 비트마스킹용 mask를 N만큼 왼쪽으로 시프트 시킨 다음, -1
 * 3. & 연산으로 mask와 M을 연산 후 mask와 동일한지 체크
 * 
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            // 검사용 수 만들기 (마지막 N개의 비트들이 모두 1인)
            int mask = (1<<N)-1;

            // & 연산으로 비트 밝히기
            if((M & mask) == mask){
                System.out.println("#"+t+" ON");
            }else{
                System.out.println("#"+t+" OFF");
            }
        }
    }
}
