import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5215. 햄버거 다이어트
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 재료의 수 ingreNum, 칼로리제한 maxCalorie
 * 	1-3. 재료에 대한 민기의 맛 점수와 칼로리 ingredient[][2]
 * 
 * [흐름]
 * 외판원순회..? 는 순서가 중요할때 쓰는데 이건 순서가 중요하지 않음. 칼로리에 따른 최대의 맛 점수를 찾는게 중요
 * dp[칼로리] = 현재 칼로리가 이럴때 최대의 맛 점수
 * 
 * dp[1000] = dp[200] + dp[300] + dp[500] 일 수도 있고 / 그냥 dp[1000]이 더 클수도 있음.
 * 재료 1에 대해 제한 칼로리부터 재료1까지 돌기?
 * 
 * 2. DP 알고리즘 - 각 재료를 순회하며
 * 	2-1. 최대 칼로리부터 현재재료의 칼로리까지 감소시키면서, currC
 * 		2-1-1. dp[currC] = Math.max(dp[currC], dp[currC - 현재재료 칼로리]+현재재료 맛 점수)
 * 
 * 3. dp[maxCalorie]를 출력하면 됨.
 *
 */
public class Solution {
	static int T;
	static int ingreNum;
	static int maxCalorie;
	static int[][] ingredient;
	static int[] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			ingreNum = Integer.parseInt(st.nextToken());
			maxCalorie = Integer.parseInt(st.nextToken());
			ingredient = new int[ingreNum][2];
			
			for(int i=0; i<ingreNum; i++) {
				st = new StringTokenizer(br.readLine());
				ingredient[i][0] = Integer.parseInt(st.nextToken());
				ingredient[i][1] = Integer.parseInt(st.nextToken());
			} // 입력 끝
			
			dp = new int[maxCalorie+1];
			
			for(int[] curr: ingredient) {
				int currScore = curr[0];
				int currCalorie = curr[1];
				
				for(int cc=maxCalorie; cc>=currCalorie; cc--) {
					dp[cc] = Math.max(dp[cc], dp[cc-currCalorie]+currScore);
				}
			}
			
			System.out.println("#"+t+" "+dp[maxCalorie]);
				
		}
	}

}
