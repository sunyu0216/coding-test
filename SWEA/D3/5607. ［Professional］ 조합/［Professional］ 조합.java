import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5607. [Professional] 조합
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 자연수 N, R
 * 
 * 2. 전처리(Preprocessing)
 * 	2-1. 1!부터 1000000! 까지 미리 계산하여 fact[]에 저장
 * 
 * 3. 페르마의 소정리 활용
 * 	3-1. 조합공식
 * 	3-2. 분모인 r!(n-r)!의 역원을 구하기 위해 r!(n-r)!의 p-2승을 계산
 * 	3-3. 거듭제곱은 분할정복을 이용
 * 
 * 4. 결과 출력
 * 	4-1. 분자 x 분모의 역원을 계산하여 출력
 *
 */
public class Solution {
    static final int MOD = 1234567891;
    static long[] fact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        // 1. 팩토리얼 전처리 (N이 1,000,000까지이므로 미리 한 번만 계산)
        fact = new long[1000001];
        fact[0] = 1;
        for (int i = 1; i <= 1000000; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 2. 조합 계산
            // 분자: n!
            long top = fact[n];
            // 분모: (r! * (n-r)!)
            long bottom = (fact[r] * fact[n - r]) % MOD;

            // 3. 페르마의 소정리를 이용해 분모의 역원 계산: bottom^(MOD-2)
            long bottomInv = power(bottom, MOD - 2);

            // 4. 최종 결과: (top * bottomInv) % MOD
            long result = (top * bottomInv) % MOD;

            System.out.println("#" + t + " " + result);
        }
    }

    // 분할 정복을 이용한 거듭제곱 함수
    static long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            // 지수가 홀수이면 base를 한 번 곱함
            if (exp % 2 == 1) res = (res * base) % MOD;
            // 지수를 반으로 줄이고 base를 제곱함
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }
}