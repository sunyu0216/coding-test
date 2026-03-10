import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1247. [S/W 문제해결 응용] 3일차 - 최적 경로
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 고객의 수 customerNum
 * 	1-3. 좌표들 pos[customerNum+2][2]
 * 	1-4. 전체 맵 map[100][100]
 * 
 * 2. DFS를 활용해 고객의 집을 방문할 순서를 정하기
 * 	2-1. 기저조건: 모든 집의 방문 순서를 정했다면 
 * 		2-1-1. 현재 마지막 고객부터 집까지의 거리 더해서 현재 최종 거리를 구하기
 * 		2-1-2. 최소 거리와 비교 후 갱신
 * 
 * 	2-2. 가지치기: 현재까지의 거리가 현재 최소거리보다 크거나 같다면 return
 * 
 * 	2-3. 재귀호출
 * 		2-3-1. 총 고객의 수만큼 반복문을 돌리며, 방문 안한집이라면,
 * 		2-3-2. 현재 고객의 집 방문처리
 * 		2-3-3. 현재 고객의 집 좌표, 현재 고객의 집 순번, 현재 고객 집까지의 거리와 함께 재귀 호출
 * 		2-3-4. 방문처리 백트래킹
 *
 */
public class Solution {
	static int T;
	static int customerNum;
	static int companyX;
	static int companyY;
	static int homeX;
	static int homeY;
	static int[][] customerPos;
	static int minDis;
	static boolean[] visited;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			customerNum = Integer.parseInt(br.readLine());
			customerPos = new int[customerNum][2];
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			companyX = Integer.parseInt(st.nextToken());
			companyY = Integer.parseInt(st.nextToken());
			homeX = Integer.parseInt(st.nextToken());
			homeY = Integer.parseInt(st.nextToken());
			
			for(int c=0; c<customerNum; c++) {
				customerPos[c][0] = Integer.parseInt(st.nextToken());
				customerPos[c][1] = Integer.parseInt(st.nextToken());
			}
			
			minDis = Integer.MAX_VALUE;
			visited = new boolean[customerNum];
			
			findPath(companyX, companyY, 0, 0);
			
			System.out.println("#"+t+" "+minDis);
		}
	}
	
	public static void findPath(int lastX, int lastY, int currDis, int idx) {
		// 2-1. 기저조건: 모든 집의 방문 순서를 정했다면 
		if(idx == customerNum) {
			// 2-1-1. 현재 마지막 고객부터 집까지의 거리 더해서 현재 최종 거리를 구하기
			int distance = Math.abs(lastX- homeX) + Math.abs(lastY - homeY);
			
			// 2-1-2. 최소 거리와 비교 후 갱신
			minDis = Math.min(minDis, currDis+distance);
			return;
		}
		
		// 2-2. 가지치기: 현재까지의 거리가 현재 최소거리보다 크다면 return
		if(currDis >= minDis) {
			return;
		}
		
		// 2-3. 재귀호출
		for(int i=0; i<customerNum; i++) {
			if(!visited[i]) {
				visited[i] = true;
				int distance = Math.abs(lastX- customerPos[i][0]) + Math.abs(lastY - customerPos[i][1]);
				findPath(customerPos[i][0], customerPos[i][1], currDis+distance, idx+1);
				visited[i] = false;
			}
		}
	}

}
