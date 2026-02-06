import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 3421. 수제 버거 장인
 * 
 * 1. 테스트 케이스 입력받고 반복하면서,
 * 	1-1. 재료의 개수 ingredientNum, 궁합이 맞지 않은 정보 constraintNum
 * 	1-2. 궁합이 맞지 않은 정보 입력받기 badPairs[][]
 * 		1-2-1. 같은 쌍이 여러번 주어질 수도 있음
 * 		1-2-2. boolean 배열로 만들어 true이면 그 재료들은 궁합이 맞지 않은 것
 * 
 * 
 * @see #makeHamburger(int)
 * 2. 모든 조합으로 햄버거를 만들어보기
 * 	2-1. 현재 재료를 넣기 전에 궁합이 맞는지 체크 -> isGoodPairs(int)
 * 		2-1-1. 궁합이 좋다면 isSelected로 사용한 재료를 표시
 * 		2-1-2. 궁합이 좋다면 현재 재료 넣는 재귀 호출
 * 	2-2. 현재 재료를 넣지 않는 재귀 호출
 * 	2-3. 기저조건: 재료를 모두 순회했다면 전체 햄버거 개수 +1
 * 
 * 
 * @see #isGoodPairs(int)
 * 3. 만들어진 햄버거가 궁합이 맞는지 체크하기
 * 	3-1. 전체 재료의 수 만큼 반복하면서,
 * 	3-2. isSelected에서 true가 나오는 인덱스를 badPairs에서 현재 재료와의 true가 나오면 궁합이 맞지 않은 햄버거 -> false
 * 	3-3. 끝까지 안걸리면 true 반환
 * 
 * 4. 궁합을 통과한 햄버거 개수 출력하기
 * 
 */

public class Solution {
	
	static int ingredientNum;
	static int constraintNum;
	static boolean[] isSelected;
	static boolean[][] badPairs;
	static int totalHamburgerNum;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			// 1. 입력받기
			st = new StringTokenizer(br.readLine());
			
			ingredientNum = Integer.parseInt(st.nextToken());
			constraintNum = Integer.parseInt(st.nextToken());
			
			badPairs = new boolean[ingredientNum][ingredientNum];
			for(int c=0; c<constraintNum; c++) {
				st = new StringTokenizer(br.readLine());
				
				int i1 = Integer.parseInt(st.nextToken())-1;
				int i2 = Integer.parseInt(st.nextToken())-1;
				
				badPairs[i1][i2] = true;
				badPairs[i2][i1] = true;
				
			}
			
			// 2. 모든 조합
			isSelected = new boolean[ingredientNum];
			totalHamburgerNum = 0;
			makeHamburger(0);
			
			// 3. 출력
			System.out.println("#"+t+" "+totalHamburgerNum);
			
		}
		
	}
	
	static void makeHamburger(int idx) {
		
		if(idx == ingredientNum) {
			totalHamburgerNum++;
			return;
		}
		
		if(isGoodPairs(idx)) {
			isSelected[idx] = true;
			makeHamburger(idx+1);
			isSelected[idx] = false;
		}
		isSelected[idx] = false;
		makeHamburger(idx+1);
		
	}
	
	static boolean isGoodPairs(int idx) {
		
		for(int i=0; i<ingredientNum; i++) {
			if(isSelected[i] && badPairs[i][idx]) {
				return false;
			}
		}
		return true;
	}

}
