import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 9456. 스티커
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 스티커 열의 개수 colNum
 * 	1-3. 열의 개수 만큼 순회하며,
 * 		1-3-1. 이차원 배열 stickers[][]에 입력받기
 * 	                                                                   
 * 2. stickers를 디피 배열로 사용
 * 		dp[r][c] = c번째 스티커 위, 아래 중 r(0이면 위, 1이면 아래) 스티커를 선택했을 때의 최대 점수
 * 
 * 3. 이때 스티커의 열 길이가 1이라면 바로 6번으로 
 * 
 * 4. dp[0][0] ~ dp[1][1]까지 초기화
 * 
 * 5. 2 ~ colNum까지 반복하며
 *  5-1. dp[0][현재 열] = Max(dp[1][현재 열 -1], dp[1][현재 열-2])
 * 	5-2. dp[1][현재 열] = Max(dp[0][현재 열 -1], dp[0][현재 열-2])
 * 
 * 6. dp[0][colNum-1]와 dp[1][colNum-1] 비교 후 더 큰 것을 출력
 * 
 *
 */
public class Main {
	static int colNum;
	static int[][] stickers;
	static int[][] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			colNum = Integer.parseInt(br.readLine());
			stickers = new int[2][colNum];
			dp = new int[2][colNum];
			
			for(int r=0; r<2; r++) {
				st = new StringTokenizer(br.readLine());
				for(int c=0; c<colNum; c++) {
					stickers[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 3. 이때 스티커의 열 길이가 1이라면 바로 6번으로 
			if(colNum > 1) {

				// 4. dp[0][0] ~ dp[1][1]까지 초기화
				stickers[0][1] += stickers[1][0];
				stickers[1][1] += stickers[0][0];
				
				// 5. 2 ~ colNum까지 반복하며
				for(int c=2; c<colNum; c++) {
					// 5-1. dp[0][현재 열] = Max(dp[1][현재 열 -1], dp[1][현재 열-2])
					stickers[0][c] += Math.max(stickers[1][c-1], stickers[1][c-2]);
					
					// 5-2. dp[1][현재 열] = Max(dp[0][현재 열 -1], dp[0][현재 열-2])
					stickers[1][c] += Math.max(stickers[0][c-1], stickers[0][c-2]);
				}
			}
			
			// 6. dp[0][colNum-1]와 dp[1][colNum-1] 비교 후 더 큰 것을 출력
			int answer = Math.max(stickers[0][colNum-1], stickers[1][colNum-1]);
			
			
			System.out.println(answer);
		}
	}

}
