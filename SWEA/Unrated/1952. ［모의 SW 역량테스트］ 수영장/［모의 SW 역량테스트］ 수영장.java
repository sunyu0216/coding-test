import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1952. 수영장
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 입력받기 T
 * 	1-2. 이용권 가격 입력받기 - 순서대로 1일권, 1달권, 3달권, 1년권
 * 		price[4]
 * 	1-3. 이용계획 입력받기
 * 		plan[12]
 * 
 * 
 * 2. 백트레킹 함수
 * 	2-0. 가지치기: 이미 최솟값보다 비싸졌다면 더 볼 필요 없음
 * 
 * 	2-1. 기저조건: 12개월의 모든 이용권을 배정한 경우
 * 		2-1-1. 현재 이용 금액보고 총 이용금액 갱신
 * 
 * 	2-2. 현재 달의 이용계획이 0일 이라면, 넘기기
 * 	2-3. 1일 이용권을 적용하는 경우 날짜만큼 곱해서 이용 금액에 더해주기
 * 	2-4. 3달권을 적용하는 경우 idx를 3달만큼 늘려주기
 */
public class Solution {
	
	static int[] price = new int[4];
	static int[] plan = new int[12];
	static int minPrice;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			for(int p=0; p<4; p++) {
				price[p] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			for(int p=0; p<12; p++) {
				plan[p] = Integer.parseInt(st.nextToken());
			}
			
			// 1. 초기 최솟값을 1년권 가격으로 설정 (가장 큰 기준점)
            minPrice = price[3];
            
            // 2. 백트래킹 시작
            backtracking(0, 0);

            System.out.println("#" + t + " " + minPrice);
		}
	}
	
	static void backtracking(int idx, int currentPrice) {
		// 가지치기: 이미 최솟값보다 비싸졌다면 더 볼 필요 없음
        if (currentPrice >= minPrice) return;
        
        // 기저조건: 12개월의 모든 이용권을 배정한 경우
		if(idx >= 12) {
			minPrice = Math.min(minPrice, currentPrice);
			return;
		}
		
		if(plan[idx] == 0) {
			backtracking(idx+1, currentPrice);
		}else {
			// 시나리오 1: 1일권 적용하는 경우
			backtracking(idx+1, currentPrice+plan[idx]*price[0]);
			
			// 시나리오 2: 1달권 적용하는 경우
			backtracking(idx+1, currentPrice+price[1]);
			
			// 시나리오 3: 3달권 적용하는 경우
			backtracking(idx+3, currentPrice+price[2]);
			
			// 1년 권은 이미 minPrice에 있으므로 pass
			
		}
	}

}
