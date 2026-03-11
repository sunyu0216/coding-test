import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 
 * @author seonyu
 * SWEA 1251-2(프림). [S/W 문제해결 응용] 4일차 - 하나로
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 섬의 개수 islandNum
 * 	1-3. 섬들의 좌표
 * 	1-4. 해저터널의 환경 부담 세율 실수 environmentTax
 * 
 * 2. 변수들
 * 	2-1. 해저터널의 정보를 입력받을 행렬 tunnel[시작 섬][끝 섬] = 해저터널 비용(가중치)
 * 	2-2. 방문한 해저터널을 표시할 visited[]
 * 	2-3. 각 해저터널까지의 최저 비용을 표시할 minCost[]
 * 
 * 
 * 3. 메인 로직
 * 	3-1. 모든 정점을 돌며, (시작 정점)
 * 		3-1-1. 모든 정점을 돌며,
 * 			3-1-1-1. 아직 방문하지 않은 정점 중 가장 가까운 정점을 찾기
 * 
 * 		3-1-2. 찾은 정점 방문처리, 해당 정점까지의 비용 더해주기
 * 		3-1-3. 해당 정점을 연결시킨 것이므로, 다시 모든 정점을 돌며,
 * 			3-1-3-1. 아직 방문하지 않은 정점들을 확인하며, 현재 비용보다 더 싸졌는지 확인
 * 			3-1-3-2. 싸졌다면 minCost 갱신
 * 
 *  
 */
public class Solution {
	static int T;
	static int islandNum;
	static int environmentTax;
	static double[][] tunnel;
	static int[][] points;
	static boolean[] visited;
	static double[] minCost;
	static double totalCost;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			init(br);
			
			// 3-1. 모든 정점을 돌며, (시작 정점)
			for(int start=0; start<islandNum; start++) {
				int minIsland = -1;
				double min = Double.MAX_VALUE;
				
				for(int des=0; des<islandNum; des++) {
					if(!visited[des] && min > minCost[des]) {
						minIsland = des;
						min = minCost[des]; // 가장 비용이 작은 섬 찾기
					}
				}
				
				// 3-1-2. 찾은 정점 방문처리, 해당 정점까지의 비용 더해주기
				if(minIsland == -1) {
					// 섬을 찾지 못한 것이므로 break
					break;
				}
				visited[minIsland] = true;
				totalCost += min;
				
				// 3-1-3. 해당 정점을 연결시킨 것이므로, 다시 모든 정점을 돌며,
				for(int other=0; other<islandNum; other++) {
					if(!visited[other] && tunnel[minIsland][other] != 0 && minCost[other] > tunnel[minIsland][other]) {
						minCost[other] = tunnel[minIsland][other];
					}
				}
			}
			
			System.out.println("#"+t+" "+Math.round(totalCost));
		}
	}
	
	static void init(BufferedReader br) throws IOException {
        islandNum = Integer.parseInt(br.readLine());
        points = new int[islandNum][2];
        tunnel = new double[islandNum][islandNum];

        // X 좌표들 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < islandNum; i++) {
            points[i][0] = Integer.parseInt(st.nextToken());
        }

        // Y 좌표들 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < islandNum; i++) {
            points[i][1] = Integer.parseInt(st.nextToken());
        }

        // 환경 부담 세율 E 입력
        double E = Double.parseDouble(br.readLine());

        // 모든 섬 쌍에 대해 간선 비용 계산 (인접 행렬 채우기)
        for (int i = 0; i < islandNum; i++) {
            for (int j = 0; j < islandNum; j++) {
                if (i == j) continue; // 자기 자신으로의 통로는 0 (이미 초기화됨)
                
                long dx = points[i][0] - points[j][0];
                long dy = points[i][1] - points[j][1];
                long distSquare = dx * dx + dy * dy;
                
                // 프림 알고리즘을 쓸 예정이라면 양방향 모두 채워주는 게 좋습니다!
                tunnel[i][j] = tunnel[j][i] = E * distSquare;
            }
        }
        minCost = new double[islandNum];
        Arrays.fill(minCost, Double.MAX_VALUE);
        minCost[0] = 0; // 0번 섬부터 시작하므로 해당 비용 0으로
        totalCost = 0;
        
        visited = new boolean[islandNum];
        
    }
	
	

}
