import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * SWEA 1288. 새로운 불면증 치료법
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 개수를 입력받고 순회하며,
 *  1-2. N을 입력받는다.
 *
 * 2. 필요한 변수 생성
 *  2-1. 0-9가 다 방문되었다는 걸 검증하기 위한 fullmask
 *  2-2. 현재 숫자의 비트 수를 표현할 visited
 *  2-3. 현재 몇번째 양을 세는 건지 체크할 count
 *
 * 3. 반복하며
 *  3-1. 입력받은 N에 현재 인덱스를 곱한 값 N*i 를 문자열로 변환해 숫자 하나씩 읽어들인다.
 *  3-2. 문자 하나를 다시 숫자로 변환해 (1<<숫자) 를 visited에 or 연산해준다.
 *  3-3. 매번 visited와 fullmask를 비교하며 모든 숫자가 다 등장했는지 확인한다.
 */

public class Solution {

    static BufferedReader br;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){

            int N = Integer.parseInt(br.readLine());

            int fullmask = (1<<10)-1;
            int visited = 0;
            int count = 0;

            while(visited != fullmask){
                count++;

                int temp = N*count;
                String value = String.valueOf(temp);

                for(int v=0; v< value.length(); v++){
                    int digit = value.charAt(v) - '0';

                    visited = visited | (1<<digit);
                }
            }

            System.out.println("#"+t+" "+count*N);
        }
    }
}
