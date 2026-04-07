import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author seonyu
 * BOJ 1463. 1로 만들기
 * 
 * 1. 정수 Number 입력받기
 * 
 * [흐름]
 * 2에서 1을 만드는 연산 횟수는 1 => 2로 나누기
 * 3에서 1을 만드는 연산 횟수는 1 => 3으로 나누기
 * 4에서 1을 만드는 연산 횟수는 2 => 2로 나누기 (2) => 어 값이 있네 1
 * 5에서 1을 만드는 연산 횟수는 3 => 1을 빼기(4) => 어 값이 있네 2
 * 
 * dp[n] = n에서 1을 만드는 최소 연산 횟수를 저장
 *
 */
public class Main {
	static int Number;
	static int[] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Number = Integer.parseInt(br.readLine());
		dp = new int[Number+1];
		
        
        // 2부터 Number까지 차례대로 채워나가기. 이때 가능한 모든 경우의 수를 다 탐색할 것.(1뺴기, 2로 나누기, 3으로 나누기)
		
        for (int i = 2; i <= Number; i++) {
            // 1) 먼저 1을 빼는 경우를 기본값
            dp[i] = dp[i - 1] + 1;

            // 2) 2로 나누어 떨어지는 경우, 기존값과 비교해서 더 작은 것을 선택
            if (i % 2 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 2] + 1);
            }

            // 3) 3으로 나누어 떨어지는 경우, 다시 한번 비교해서 최솟값을 갱신
            if (i % 3 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 3] + 1);
            }
        }
        
		
		System.out.println(dp[Number]);

	}
}
