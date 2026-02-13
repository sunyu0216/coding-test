import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 7206. 숫자 게임
 * 
 * 1. 입력받기 & 변수들
 * 	1-1. 테스트케이스 수  T 입력받기
 * 	1-2. 시작수 startNum 입력받기
 * 	1-3. static -> maxTurn, memo[], startNum
 * 
 * 
 * 2. 최대 턴수를 반환하는 함수
 * 	2-1. 기저조건: 현재 수가 10 미만이라면, 0을 반환
 * 
 * 	2-2. 가지치기: memo[현재 수] = -1 이 아니라면 이미 계산된 수이므로 memo[현재 수]를 반환
 * 
 * 	2-3. 숫자 쪼개기 (조합 생성)
 * 		2-3-1. 현재 수의 자리수 L 구하기
 * 
 * 		2-3-2. (1 << L) -1 만큼 비트마스킹을 생성하며 반복,
 * 			a. 1을 기준으로 현재 수를 쪼개기
 * 			b. 쪼갠 수를 모두 곱한 후 재귀 호출
 * 			c. 반환값을 받아 maxTurn과 비교 후 갱신
 * 
 * 		2-3-3. memo[반환값]을 maxTurn으로 갱신
 * 	
 */
public class Solution {
	
	static int startNum;
	static int[] memo = new int[100000];
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		Arrays.fill(memo, -1);
		
		for(int t=1; t<=T; t++) {
			startNum = Integer.parseInt(br.readLine());
			
			int answer = combination(startNum);
			
			System.out.println("#"+t+" "+answer);
			
		}
	}
	
	static int combination(int num) {
		if(num < 10) {
			return 0;
		}
		
		if(memo[num] != -1) {
			return memo[num];
		}
		
		int maxTurn = 0; // 현재 숫자에 대한 최대 턴수를 구하는 변수
		
		// 2-3-1. 현재 수의 자리수 L 구하기
		int L = String.valueOf(num).length();
		
		// 2-3-2. (1 << L) -1 만큼 비트마스킹을 생성하며 반복,
		for(int mask = 1; mask < (1 << (L - 1)); mask++) {
			// mask는 수를 어떻게 쪼갤지를 나타내는 비트마스킹이므로 1부터 시작(00~01)
			int productNum = 1;
			int completeNum = 0;
			int currentNum = num;
		    int divisor = 10;
			
		    // a. 1을 기준으로 현재 수를 쪼개기
			for(int check = 0; check < L - 1; check++) {
				// check는 어떻게 쪼갤지를 확인해서 쪼개는 역할을 하는 비트마스킹이므로 1이 하나씩만 들어가게 됨
				
				if((mask & (1 << check)) != 0) {
					// mask와 check를 &연산 했을때 0이 아니라면 현재 check에서 쪼개주는게 맞다는 뜻이므로
					
					completeNum = currentNum % divisor; // 쪼개진 부분 중 오른쪽 부분은 확정이므로 곱해주기
					productNum  = productNum * completeNum;
					currentNum = currentNum / divisor; // 왼쪽 부분은 다시 쪼개질 수도 있으므로 
					divisor = 10; // 쪼개졌으므로 제일 작은 단위는 그래도임
				}else {
					// mask와 check를 &연산 했을때 0이라면 현재 check에서는 쪼개면 안됨 = 다음 칸막이를 확인해봐야함
					divisor = divisor*10;
				}
			}
			// b. 쪼갠 수를 모두 곱한 후 재귀 호출
			productNum *= currentNum; // 마지막 남은 조각 곱하기
			
			// c. 반환값을 받아 maxTurn과 비교 후 갱신
			int tempResult = combination(productNum);
			maxTurn = Math.max(maxTurn, tempResult+1);
		}
		// 2-3-3. memo[반환값]을 maxTurn으로 갱신
		memo[num] = maxTurn;
		
		return memo[num];
	}
}
