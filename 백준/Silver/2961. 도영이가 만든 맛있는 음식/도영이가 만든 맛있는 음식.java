import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 2961. 도영이가 만든 맛있는 음 식
 * 
 * 1. 입력받기
 * 	1-1. 재료의 개수 ingredientNum
 * 	1-2. 재료의 개수만큼, sourScore[] bitterScore[] 입력받기
 * 
 * 2. 신맛과 쓴맛의 차이가 가장 작은 요리 구하기
 * 	2-1. 모든 재료를 순회하며, 이 재료를 넣을지 말지를 재귀로 호출
 * 	2-2. 기저조건: 모든 재료를 순회했다면
 * 		2-2-1. 재료를 하나 이상 골랐다면
 * 		2-2-2. 최소 차이를 갱신
 * 
 * 3. 최소 차이 출력
 * 
 */

public class Main {
	
	static int ingredientNum;
	static int[] sourScore;
	static int[] bitterScore;
	static int totalScore;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		ingredientNum = Integer.parseInt(br.readLine().trim());
		
		
		sourScore = new int[ingredientNum];
		bitterScore = new int[ingredientNum];
		for(int i=0; i<ingredientNum; i++) {
			st = new StringTokenizer(br.readLine());
			
			sourScore[i] = Integer.parseInt(st.nextToken());
			bitterScore[i] = Integer.parseInt(st.nextToken());
		}
		
		totalScore = Integer.MAX_VALUE;
		subset(0, 1, 0, 0);
		
		System.out.println(totalScore);
	}
	
	static void subset(int idx, int currentSour, int currentBitter, int count){
		
		if(idx == ingredientNum) {
			if(count >=1) {
				totalScore = Math.min(totalScore, Math.abs(currentSour-currentBitter));
			}
			return;
		}
		
		subset(idx+1, currentSour*sourScore[idx], currentBitter+bitterScore[idx], count+1);
		subset(idx+1, currentSour, currentBitter, count);
		
	}
}
