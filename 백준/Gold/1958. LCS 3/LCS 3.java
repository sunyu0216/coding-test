import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author seonyu
 * BOJ 1958. LCS 3
 * 
 * 1. 입력받기
 * 	1-1. 각 문자열 배열로 입력받기 char1[],,,
 * 	abcdefghijklmn
 * 	bdefg
 * 	efg
 * 	순서가 중요! 
 * 
 * 2. dp[i][j][k] = 첫번째 문자열의 i번째 까지의 문자열과, 두번째 문자열의 j번째 까지의 문자열과, 세번째 문자열의 k번째 문자열의 LCS 개수
 * 	2-1. 현재 비교하는 문자가 같다면, <00a / 000a / 0000a>
 * 		2-1-1. 순서까지 같아야 LCS 이므로 결국 < 00 / 000 / 0000> 이었던 경우의 LCS 개수에 +1을 한 값이 이 경우의 LCS 개수가 될 것이다
 * 		2-1-2. 수식: dp[i][j][k] = dp[i-1][j-1][k-1] +1
 * 
 *	2-2. 현재 비교하는 문자가 다르다면, < 00a / 000b / 0000a>
 *		2-2-1. < 00a / 000b / 0000>, < 00a / 000 / 0000a>, < 00 / 000b / 0000a>의 경우 중 최대값이 이 경우의 LCS 개수가 된다.
 *		2-2-2. 수식: dp[i][j][k] = Max(dp[i-1][j][k], dp[i][j-1][k], dp[i][j][k-1])
 *		
 * 3. dp[i][j][k]의 값을 출력
 * 
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		char[] char1 = br.readLine().toCharArray();
		char[] char2 = br.readLine().toCharArray();
		char[] char3 = br.readLine().toCharArray();
		
		int[][][] dp = new int[char1.length+1][char2.length+1][char3.length+1];
		
		for(int i=1; i<char1.length+1; i++) {
			for(int j=1; j<char2.length+1; j++) {
				for(int k=1; k<char3.length+1; k++) {
					// 2-1. 현재 비교하는 문자가 같다면, 
					if(char1[i-1] == char2[j-1] && char2[j-1] == char3[k-1]) {
						dp[i][j][k] = dp[i-1][j-1][k-1] + 1;
					}
					// 2-2. 현재 비교하는 문자가 다르다면, 
					else {
						dp[i][j][k] = Math.max(dp[i-1][j][k], Math.max(dp[i][j-1][k], dp[i][j][k-1]));
					}
				}
			}
		}
		
		System.out.println(dp[char1.length][char2.length][char3.length]);
	}

}
