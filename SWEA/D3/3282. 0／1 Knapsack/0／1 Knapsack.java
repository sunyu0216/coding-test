import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 3282. 0/1 Knapsack
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 T
 * 	1-2. 물건의 부피와 가치 items[][2]
 * 
 * 2. 물건의 전체 개수를 순회하며
 * 	2-1. 현재 물건의 부피 currV, 물건의 가치 currC
 * 	2-2. 최대 부피부터 현재 물건의 부피까지 반복
 * 		2-2-1. dp[현재 부피] = Math.max(dp[현재부피], dp[현재부피-현재 물건의 부피]+현재 물건의 가치)
 * 
 * 3. dp[최대 부피]를 출력
 *
 */
public class Solution {
	static int T;
	static int itemNum;
	static int maxV;
	static int[][] items;
	static int[] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			itemNum = Integer.parseInt(st.nextToken());
			maxV = Integer.parseInt(st.nextToken());
			items = new int[itemNum][2];
			
			for(int i=0; i<itemNum; i++) {
				st = new StringTokenizer(br.readLine());
				items[i][0] = Integer.parseInt(st.nextToken());
				items[i][1] = Integer.parseInt(st.nextToken());
			} // 입력 끝
			
			dp = new int[maxV+1];
			
			for(int[] curr: items) {
				int currV = curr[0];
				int currC = curr[1];
				
				for(int cc=maxV; cc>=currV; cc--) {
					dp[cc] = Math.max(dp[cc], dp[cc-currV]+currC);
				}
			}
			
			System.out.println("#"+t+" "+dp[maxV]);
				
		}
		
	}

}
