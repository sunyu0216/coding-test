import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 17070. 파이프 옮기기 1
 * 
 * 1. 입력받기
 * 	1-1. 집의 크기 homeSize
 * 	1-2. 집의 상태 home[][]
 * 
 * 2. dp[i][j][state] = i, j 좌표에 파이프의 한쪽 끝 state 방향으로 위치할 경우의 수
 * 	state = 0, 1, 2 (가로, 세로, 대각선)
 * 	dp[1][2][0] = 1 -> state가 0일때는 0, 2로밖에 못감. dp[1][3][0], dp[2][3][2]
 * 	dp[2][3][0] -> state가 2일때는 0, 1, 2로 다 갈 수 있음. dp[2][4][0], dp[3][3][1], dp[3][4][2]
 * 	dp[3][3][1] -> state가 1일때는 1, 2로 밖에 못감.
 * 
 * 	전체 좌표 반복문을 돌며,
 * 	2-1. 현재 좌표에 가로로 도착하려면 -> 직전 상태가 가로였거나, 대각선이어야함.
 * 		2-1-1. dp[i][j][0] = dp[i][j-1][0] + dp[i][j-1][2]
 * 	2-2. 현재 좌표에 세로로 도착하려면 -> 직전 상태가 세로였거나, 대각선이어야함.
 * 		2-2-1. dp[i][j][1] = dp[i-1][j][1] + dp[i-1][j][2]
 * 	2-3. 현재 좌표에 대각선으로 도착하려면 -> 직전 상태가 가로, 세로, 대각선일때 다 가능. (이때 대각선에 도착하려면 현재 좌표 위, 왼이 비어있어야 함)
 * 		2-3-1. dp[i][j][2] = dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2]
 * 
 * 
 * 3. 전체 좌표를 다 돌았으면 dp[homeSize-1][homeSize-1][0] + dp[homeSize-1][homeSize-1][1] + dp[homeSize-1][homeSize-1][2] 한 값이 정답임
 *
 */
public class Main {
	static int homeSize;
	static int[][] home;
	static int[][][] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		homeSize = Integer.parseInt(br.readLine());
		home = new int[homeSize][homeSize];
		
		for(int i=0; i<homeSize; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<homeSize; j++) {
				int value = Integer.parseInt(st.nextToken());
				home[i][j] = value;
			}
		}// 입력 끝
		
		dp = new int[homeSize][homeSize][3];
		dp[0][1][0] = 1;
		
		for(int i=0; i<homeSize; i++) {
			for(int j=0; j<homeSize; j++) {
				// 시작부터 파이프가 놓여있는 위치(0, 1)는 이미 1로 세팅했으니 계산하지 않고 패스!
		        if (i == 0 && j == 1) continue;
		        
				int value = home[i][j];
				
				// 0) 벽이라면 아예 건너뛰기 (어떤 파이프도 못 옴)
		        if (value == 1) continue;
				
				// 1) 현재 좌표의 파이프가 가로라면
				if(j-1 >= 0) {
					dp[i][j][0] = dp[i][j-1][0] + dp[i][j-1][2];
				}
				
				// 2) 현재 좌표의 파이프가 세로라면
				if(i-1 >= 0) {
					dp[i][j][1] = dp[i-1][j][1] + dp[i-1][j][2];
				}
				
				// 3) 현재 좌표의 파이프가 대각선이 가능한지 체크
				if(i-1 >= 0 && j-1 >= 0) {
					if(home[i-1][j] == 0 && home[i][j-1] == 0) {
						dp[i][j][2] = dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2];
					}
				}
				
			}
		}
		
		int answer = dp[homeSize-1][homeSize-1][0] + dp[homeSize-1][homeSize-1][1] + dp[homeSize-1][homeSize-1][2];
		
		System.out.println(answer);
	}

}
