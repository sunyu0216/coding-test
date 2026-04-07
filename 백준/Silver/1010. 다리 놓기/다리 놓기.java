import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 1010. 다리 놓기
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 서쪽 사이트 개수 westNum, 동쪽 사이트 개수 eastNum
 * 
 * 결국 동쪽에 있는 사이트 중 서쪽 사이트 개수만큼의 사이트를 뽑는 느낌에 가깝다..!!
 * 7*6*5*4 / 4*3*2*1 -> 그냥 하면 오버플로 나니까 파스칼의 삼각형으로 풀기!!
 * 
 * 
 * 2. dp[r][c] = r중에 c를 뽑는 경우의 수  = r-1중에 c-1을 뽑는 경우의 수 + r-1중에 c를 뽑는 경우의 수
 * 	2-1. dp[1][1] = 1
 * 
 *
 */
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int westNum = Integer.parseInt(st.nextToken());
			int eastNum = Integer.parseInt(st.nextToken());
			
			int[][] dp = new int[eastNum+1][eastNum+1];
			
	        for (int i = 0; i <= eastNum; i++) {
	            for (int j = 0; j <= i; j++) {
	                if (j == 0 || j == i) {
	                    dp[i][j] = 1; // nC0 과 nCn은 항상 1
	                } else {
	                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
	                }
	            }
	        }
	        
	        int answer = dp[eastNum][westNum];
	        System.out.println(answer);

		}
	}

}
