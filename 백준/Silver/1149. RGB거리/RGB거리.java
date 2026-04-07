import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 1149. RGB 거리
 * 
 * 1. 입력받기
 * 	1-1. 집의 개수 homeNum
 * 	1-2. 각 집의 색칠 비용 homeColorCost[homeNum][3]
 * 		빨강: 0, 초록: 1, 파랑: 2
 * 
 * [흐름]
 * 양 옆의 집과 색이 같으면 안됨.
 * dp[n][color] = n번째 집의 색을 color로 칠할 때의 총 비용의 최솟값
 * 만약 두번째 집을 빨강으로 칠한다 하면 -> 첫번째 집은 무조건 초록 아님 파랑이어야 함.
 * dp[2][0] = min(dp[1][1], dp[1][2]) + homeColorCost[2][0]
 * ...
 * 만약 다섯번째 집을 빨강으로 칠한다 하면 -> 적어도 4번째 집은 무조건 초록 아님 파랑이어야 함.
 * dp[5][0] = min(dp[1][1], dp[1][2]) + homeColorCost[5][0]
 *
 */
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int homeNum = Integer.parseInt(br.readLine());
		int[][] homeColorCost = new int[homeNum+1][3];
		
		for(int i=1; i<homeNum+1; i++) {
			st = new StringTokenizer(br.readLine());
			homeColorCost[i][0] = Integer.parseInt(st.nextToken());
			homeColorCost[i][1] = Integer.parseInt(st.nextToken());
			homeColorCost[i][2] = Integer.parseInt(st.nextToken());
		} // 입력 끝
		
		int[][] dp = new int[homeNum+1][3];
		dp[1][0] = homeColorCost[1][0];
		dp[1][1] = homeColorCost[1][1];
		dp[1][2] = homeColorCost[1][2];
		
		for(int i=2; i<homeNum+1; i++) {
			// 현재 집을 빨강으로 칠한다면...
			dp[i][0] = Math.min(dp[i-1][1], dp[i-1][2]) + homeColorCost[i][0];
			
			// 현재 집을 초록으로 칠한다면...
			dp[i][1] = Math.min(dp[i-1][0], dp[i-1][2]) + homeColorCost[i][1];
			
			// 현재 집을 파랑으로 칠한다면...
			dp[i][2] = Math.min(dp[i-1][0], dp[i-1][1]) + homeColorCost[i][2];
		}
		
		int answer = Math.min(Math.min(dp[homeNum][0], dp[homeNum][1]), dp[homeNum][2]);
		
		System.out.println(answer);
		
	}

}
