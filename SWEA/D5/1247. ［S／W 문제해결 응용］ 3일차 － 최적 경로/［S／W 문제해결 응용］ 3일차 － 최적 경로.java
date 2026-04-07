import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1247. 최적 경로
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 고객의 수 customerNum
 * 	1-3. 회사의 좌표 companyX, companyY / 집의 좌표 homeX, homeY / 고객의 좌표 customer[][2]
 * 
 * 2. 외판원 순회 알고리즘 - dp[현재고객][방문한 상태] = 현재 방문한 상태를 거쳐 현재 고객까지 왔을때 남은 고객들을 다 도는 최소 비용
 * 	2-1. 기저조건: 모든 도시를 다 방문했다면 => (mask == (1<<고객 수)-1)
 * 		2-1-1. return 마지막 고개게서 집까지의 거리
 * 	2-2. 모든 고객을 순회하며,
 * 		2-2-1. 해당 고객을 이미 방문했는지 검사 => (방문상태 & (1<<현재 고객 번호) == 0) 방문안했다는 뜻
 * 			2-2-1-1. 방문하지 않았다면 재귀호출(해당 고객, 현재 방문상태에 해당 고객 추가)
 * 			2-2-1-2. 현재 고객에서 해당고객까지의 거리 dist
 * 			2-2-1-3. 그리고 dp[현재고객][방문상태] 값을 dist+재귀호출리턴값 과 비교 후 더 작은 것으로 갱신
 * 		2-2-2. 계속해서 고객으로 갈 수 있는 여러 후보지들을 다 탐색
 * 
 * 	2-3.
 *
 */
public class Solution {
	static int T;
	static int customerNum;
	static int companyX, companyY;
	static int homeX, homeY;
	static int[][] customer;
	static int[][] dp;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			customerNum = Integer.parseInt(br.readLine());
			customer = new int[customerNum][2];
					
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			companyX = Integer.parseInt(st.nextToken());
			companyY = Integer.parseInt(st.nextToken());
			homeX = Integer.parseInt(st.nextToken());
			homeY = Integer.parseInt(st.nextToken());
			
			for(int i=0; i<customerNum; i++) {
				customer[i][0] = Integer.parseInt(st.nextToken());
				customer[i][1] = Integer.parseInt(st.nextToken());
			}// 입력끝
			
			dp = new int[customerNum][1<<customerNum];
			for(int i=0; i<customerNum; i++) Arrays.fill(dp[i], -1);
			
			int answer = 100000000;

			// 모든 고객(i)을 첫 번째 방문지로 시도해보기
			for (int i = 0; i < customerNum; i++) {
			    // 회사 -> i번 고객까지의 거리
			    int startDist = Math.abs(companyX - customer[i][0]) + Math.abs(companyY - customer[i][1]);
			    
			    // (회사->i 거리) + (i부터 나머지 다 돌고 집에 가는 거리)
			    int midDist = TSP(i, 1<<i);
			    
			    answer = Math.min(answer, startDist + midDist);
			}
			System.out.println("#" + t + " " + answer);
			
		}
	}
	
	public static int TSP(int customerId, int visited) {
		if(visited == (1<<customerNum)-1) {
			int dist = Math.abs(customer[customerId][0] - homeX) + Math.abs(customer[customerId][1] - homeY); // 마지막 고객에서 집까지의 거리
			return dist;
		}
		
		
	    if (dp[customerId][visited] != -1) {
	        return dp[customerId][visited];
	    }
	    
		
		// 최솟값을 찾기 위해 아주 큰 값으로 초기화
	    int minState = 987654321; 

	    for (int i = 0; i < customerNum; i++) {
	        if ((visited & (1 << i)) == 0) {
	            int dist = Math.abs(customer[customerId][0] - customer[i][0]) + Math.abs(customer[customerId][1] - customer[i][1]);
	            int result = dist + TSP(i, visited | (1 << i));
	            
	            // 그중 최솟값 선택
	            minState = Math.min(minState, result);
	        }
	    }
	    
	    
	    return dp[customerId][visited] = minState;
	    
	}

}
