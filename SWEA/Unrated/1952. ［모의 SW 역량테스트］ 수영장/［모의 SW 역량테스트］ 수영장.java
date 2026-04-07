import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1952. 수영장
 * 
 * 1. 입력받기 및 변수
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 이용권 가격들 cost[]
 * 	1-3. 12개월 이용 계획 plan[]
 * 
 * 2. DP -> dp[i] = i번째 달까지의 최소 이용 금액
 * 	2-1. dp[i] = min(1일권 사용, 1달권 사용, 3달권 사용)
 * 	2-2. dp[1] = min(1일권, 1달권) -> min(더 작은 것, 3달권) *혹시 3달권이 1원 이럴수도 있으니까
 * 	2-3. dp[2] = min(1일권, 1달권) -> "
 * 	2-4. dp[3] = min(dp[2]+1일권, dp[2]+한달권) -> min(더 작은 것, dp[0]+3달권)
 * 	2-5. dp[12]가 되면 min(dp[12], 1년권을 비교하기)
 *
 */
public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		int[] cost;
		int[] plan;
		int[] dp;
		
		for(int t=1; t<=T; t++) {
			cost = new int[4];
			plan = new int[13];
			dp = new int[13]; 
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<4; i++) {
				cost[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<13; i++) {
				plan[i] = Integer.parseInt(st.nextToken());
			}
			
			dp[0] = 0; // 0번째 달 이용요금 0원
			
			for(int i=1; i<13; i++) {
				dp[i] = Math.min(dp[i-1]+(cost[0]*plan[i]), dp[i-1]+cost[1]); // 1일권과 한달권 중 더 작은 거 선택
				
				if(i>=3) {
					dp[i] = Math.min(dp[i], dp[i-3]+cost[2]); // 현재 이용값과 3달권 중 뭐가 더 싼지 선택
				}else {
					dp[i] = Math.min(dp[i], cost[2]); // 3달권이 너무 싸서 1월부터 적용하는 경우
				}
			}
			int answer = Math.min(dp[12], cost[3]);
			
			System.out.println("#"+t+" "+ answer);
		}
	}
}
