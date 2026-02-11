import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1486. 장훈이의 높은 선반
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 점원의 수 employNum , 선반 높이 shelfH
 * 	1-3. 점원들의 키 employH[]
 * 
 * 2. 부분집합..?
 * 	2-1. 가지치기: 현재 탑의 높이가 선반 높이를 넘는다면
 * 		2-1-1. 최소높이와 현재 탑의 높이 비교 후 갱신
 * 		2-1-2. 반환
 * 
 * 	2-2. 기저조건: 마지막 직원까지 결정했다면
 * 		2-2-0. 현재 탑의 높이가 선반 높이를 넘을 때만!
 * 		2-2-1. 최소높이와 현재 탑의 높이 비교 후 갱신
 * 		2-2-2. 반환
 * 
 * 	2-3. 현재 인덱스에 해당하는 직원을 포함할건지 말지 재귀로 처리
 * 		2-3-1. 해당 직원을 포함한 탑의 높이로 부분집합 재귀 호출
 * 		2-3-2. 해당 직원 불포함한 탑의 높이로 부분집합 재귀 호출
 * 
 *
 */
public class Solution {
	
	static int employNum;
	static int shelfH;
	static int[] employH;
	static int MinDiff;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			employNum = Integer.parseInt(st.nextToken());
			shelfH = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			employH = new int[employNum];
			for(int e=0; e<employNum; e++) {
				employH[e] = Integer.parseInt(st.nextToken());
			}
			
			MinDiff = Integer.MAX_VALUE;
			subset(0, 0);
			
			System.out.println("#"+t+" "+MinDiff);
		}
	}
	
	static void subset(int idx, int currentH) {
		if(currentH >= shelfH) {
//			System.out.println("선반 높이를 넘었다!");
//			System.out.println("현재높이: "+currentH);
//			System.out.println("-------------------------");
			MinDiff = Math.min(MinDiff, Math.abs(shelfH-currentH));
			return;
		}
		
		if(idx == employNum) {
			if(currentH >= shelfH) {
//				System.out.println("마지막 직원까지 선택했다!");
//				System.out.println("현재높이: "+currentH);
//				System.out.println("-------------------------");
				MinDiff = Math.min(MinDiff, Math.abs(shelfH-currentH));
			}
			return;
		}
		
		subset(idx+1, currentH+employH[idx]);
		subset(idx+1, currentH);
	}

}
