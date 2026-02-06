import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5215. 햄버거 다이어트
 * 
 * 
 * 1. 입력받기
 * 	1-1. 재료 개수, 최대 칼로리
 * 	1-2. 각 재료마다 점수와 칼로리를 각각의 배열로 입력받기
 * 
 * 2. 모든 조합의 점수 중 최고 점수를 찾는 함수
 * 	2-1. 재료를 다 순회하며,
 * 	2-2. 현재 만들어진 햄버거의 점수가 최고 점수를 넘는다면 갱신
 * 	2-3. 기저조건: 현재 모든 재료를 순회했다면 종료
 * 	2-4. 새로 넣을 재료의 칼로리가 최대 칼로리를 넘지 않을 경우에 이 재료를 넣어준다.
 * 
 * 3. 최고의 점수 출력
 *
 */
public class Solution {
	
	static int IngredientNum;
	static int maxCalorie;
	static int[] IngredientScore;
	static int[] IngredientCalorie;
	static int bestScore;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			IngredientNum = Integer.parseInt(st.nextToken());
			maxCalorie = Integer.parseInt(st.nextToken());
			
			IngredientScore = new int[IngredientNum];
			IngredientCalorie = new int[IngredientNum];
			for(int i=0; i<IngredientNum; i++) {
				st = new StringTokenizer(br.readLine());
				IngredientScore[i] = Integer.parseInt(st.nextToken());
				IngredientCalorie[i] = Integer.parseInt(st.nextToken());
			}
			
			bestScore = 0;
			combination(0, 0, 0);
			
			// 결과 출력
            System.out.println("#" + t + " " + bestScore);
			
		}
	}
	static void combination(int currentCalorie, int currentScore, int startIdx) {
		
		if (currentScore > bestScore) {
            bestScore = currentScore;
        }

        // 기저 조건: 모든 재료를 다 훑었다면 종료
        if (startIdx == IngredientNum) {
            return;
        }
		
		for(int idx=startIdx; idx<IngredientNum; idx++) {
			if(IngredientCalorie[idx] + currentCalorie <= maxCalorie) {
				combination(IngredientCalorie[idx] + currentCalorie, IngredientScore[idx] + currentScore, idx+1);
				
			}
		}
	}

}
