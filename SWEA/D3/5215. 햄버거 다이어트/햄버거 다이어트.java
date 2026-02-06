import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5215. 햄버거 다이어트 (부분집합)
 * 
 * 
 * 1. 입력받기
 * 	1-1. 재료 개수, 최대 칼로리
 * 	1-2. 각 재료마다 점수와 칼로리를 각각의 배열로 입력받기
 * 
 * 2. 재료를 순회하면서, 이 재료를 넣을지 말지 결정
 * 	2-1. 기저조건1: 현재 칼로리가 최대 칼로리를 넘는다면, 종료
 * 	2-2. 기저조건2: 모든 재료를 순회했다면, 점수가 최대 점수보다 낮다면 갱신
 * 	2-3. 이 재료를 넣는 경우의 재귀 호출
 * 	2-4. 이 재료를 넣지 않는 경우의 재귀 호출
 * 
 * 3. 최고의 점수 출력
 *
 */
public class Solution {
	
	static int IngredientNum;
	static int maxCalorie;
	static int[] IngredientScore;
	static int[] IngredientCalorie;
	static boolean[] isSelected;
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
			
			isSelected = new boolean[IngredientNum];
			bestScore = 0;
			subset(0, 0, 0);
			
			// 결과 출력
            System.out.println("#" + t + " " + bestScore);
			
		}
	}
	static void subset(int idx, int currentCalorie, int currentScore) {

		// 기저조건 1: 최대 칼로리를 넘는다면 종료
		if(currentCalorie > maxCalorie) {
			return;
		}
        // 기저 조건2: 모든 재료를 다 훑었다면 종료
        if (idx == IngredientNum) {
        	if(currentScore>bestScore) {
        		bestScore = currentScore;
        	}
            return;
        }
		
		subset(idx+1, currentCalorie+IngredientCalorie[idx], currentScore+IngredientScore[idx]);
            		
        subset(idx+1, currentCalorie, currentScore);
	}

}
