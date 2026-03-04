import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 1. 입력받기
 * 	1-1. 마법의 두루마리 문자들 입력받기 char[] magicWords
 * 	1-2. 악마의 돌다리 입력받기 char[] devilBridge
 * 	1-3. 천사의 돌다리 입력받기 char[] angelBridge
 * 
 * 2. dp[다리 종류][돌 인덱스][마법의 두루마리 문자들 중 충족한 인덱스]
 * 	2-1. 첫 번째 마법 단어(magicWords[0])가 일치하는 돌들을 찾아 초기값 1 설정
 *	2-2. 점화식 전개 (두 번째 문자부터 끝까지)
 *		2-2-1. 마법 두루마리 두번째 단어부터 끝까지 반복,
 *			2-2-1-1. 현재 밟으려는 돌다리 인덱스 반복
 *				2-2-1-1-1. 현재 악마다리에서 밟은 돌과 문자가 일치하다면?
 *					2-2-1-1-1-1. 직전 단어, 천사다리의 0~현재-1까지의 인덱스를 조회해 모두 합산
 *				2-2-1-1-2. 현재 천사다리에서 밟은 돌과 문자가 일치하다면?
 *					2-2-1-1-2-1. 직전 단어, 악마다리의 0~현재-1까지의 인덱스를 조회해 모두 합산
 *
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		char[] magicWords = br.readLine().toCharArray();
		char[] devilBridge = br.readLine().toCharArray();
		char[] angelBridge = br.readLine().toCharArray();
		
		int[][][] dp = new int[2][devilBridge.length][magicWords.length];
		
		for(int i=0; i<devilBridge.length; i++) {
			if(devilBridge[i] == magicWords[0]) {
				dp[0][i][0] = 1;
			}
		}
		for(int i=0; i<angelBridge.length; i++) {
			if(angelBridge[i] == magicWords[0]) {
				dp[1][i][0] = 1;
			}
		}
		
		for(int m=1; m<magicWords.length; m++) {
			for(int j=0; j<devilBridge.length; j++) {
				if(devilBridge[j] == magicWords[m]) {
					for(int d=0; d<j; d++) {
						dp[0][j][m] += dp[1][d][m-1];
					}
				}
				if(angelBridge[j] == magicWords[m]) {
					for(int d=0; d<j; d++) {
						dp[1][j][m] += dp[0][d][m-1];
					}
				}
			}
		}
		
		// 모든 돌들을 순회하며 조건을 충족하면 다 더해줘야 함.
		int answer = 0;
		int lastWordIdx = magicWords.length-1;
		for(int j=0; j<devilBridge.length; j++) {
			answer += dp[0][j][lastWordIdx];
			answer += dp[1][j][lastWordIdx];	
		}
		
		System.out.println(answer);
		
	}
}
