import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 10942. 팰린드롬?
 * 
 * 1. 입력받기
 * 	1-1. 수열의 크기 numSize
 * 	1-2. 홍준이가 칠판에 적은 수 number[]
 * 	1-3. 질문의 개수 questionNum
 * 	1-4. 홍준이가 한 질문들 startIdx, endIdx
 * 
 * (2. 홍준이가 한 질문들을 순회하며,
 * 	2-1. 시작 포인터와 끝 포인터대로 칠판에 적은 수들을 순회하며 대칭인지 판단, 투포인터
 * => 시간초과;)
 * 
 * 2. dp[i][j] = i번째 인덱스부터 j번째 인덱스까지의 수열을 봤을때 펠린드롬인지 아닌지를 나타냄
 * 	2-1. dp[i][i] = 길이가 1이면 당연히 펠린드롬
 * 	2-2. dp[i][i+1] = 길이가 2면 dp[i][i] = dp[i][i+1]이 성립해야 펠린드롬
 * 	2-3. dp[i][i+2] = 길이가 3이면 dp[i][i]와 dp[i][i+2]가 같고, 중간값 dp[i][i+1]이 펠린드롬이라면 펠린드롬
 *
 */
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int numSize = Integer.parseInt(br.readLine());
		int[] number = new int[numSize+1];
		boolean[][] isPalin = new boolean[numSize+1][numSize+1];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=1; i<=numSize; i++) {
			number[i] = Integer.parseInt(st.nextToken());
		}
		
		
		// 길이 1 채우기
		for (int i = 1; i <= numSize; i++) isPalin[i][i] = true;

		// 길이 2 채우기
		for (int i = 1; i < numSize; i++) {
		    if (number[i] == number[i + 1]) isPalin[i][i + 1] = true;
		}
		
		// 나머지 길이일 때 채우기 (길이가 1, 2일때 제외하고 열 기준으로)
		for(int j=3; j<=numSize; j++) {
			for(int i=1; i<=j-2; i++) {
				if(number[i] == number[j]) {
					if(isPalin[i+1][j-1]) {
						isPalin[i][j] = true;
					}else {
						isPalin[i][j] = false;
					}
				}
			}
		}
		
//		for(int i=1; i<=numSize; i++) {
//			for(int j=1; j<=numSize; j++) {
//				System.out.print(isPalin[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		int questionNum = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		
		for(int q=0; q<questionNum; q++) {
			st = new StringTokenizer(br.readLine());
			
			int startIdx = Integer.parseInt(st.nextToken());
			int endIdx = Integer.parseInt(st.nextToken());
			
			if(isPalin[startIdx][endIdx]) {
				sb.append("1\n");
			}else {
				sb.append("0\n");
			}
		}
		
		System.out.println(sb);
	}

}
