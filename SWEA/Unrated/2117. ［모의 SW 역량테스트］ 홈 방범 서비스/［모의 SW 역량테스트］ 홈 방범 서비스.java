import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 2117. 홈 방범 서비스
 * 
 * 1. 입력받기 및 변수
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 도시의 크기 citySize, 하나의 집이 지불할 수 있는 비용 cost
 * 	1-3. 각 도시 정보 city[][]
 * 	1-4. 현재 
 * 
 * [흐름]
 * 범위가 1일땐 집 1개만 가능
 * 범위가 2일땐 집 2~5개 가능
 * 범위가 3일땐 집 6~13개 가능
 * 즉 범위가 포함할 수 있는 집의 최대 개수는 운영비용과 같음
 * 당연히 적은 범위에 많은 집을 놓을수록 보안회사의 이익이 커짐 -> dp[i] = 범위가 i일때 보안회사의 최대 이익
 * 
 * 2. 보안영역의 범위만큼(1~도시사이즈의 -1)
 * 	2-1. dp[1] = cost - 1
 * 	2-2. 2부터는 city[][]를 탐색하며 해당 영역에 최대 집이 몇개 올 수 있는지 구하기 -> BFS() 호출
 * 	2-3. dp[i] = cost*집 개수 - 운영비용
 * 	2-4. 보안영역의 범위가 커질때마다 최종 비용 갱신
 *
 */
public class Solution {
	static int T;
	static int citySize;
	static int cost;
	static int[][] city;
	static int[] dp;
	static int originalHomeNum;
	static int totalHomeNum;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			citySize = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			city = new int[citySize][citySize];
			originalHomeNum = 0;
			
			for(int i=0; i<citySize; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<citySize; j++) {
					int value = Integer.parseInt(st.nextToken());
					if(value == 1) {
						originalHomeNum++;
					}
					city[i][j] = value;
				}
			}// 입력 끝
			
			dp = new int[citySize+2];
			dp[1] = cost - 1;
			totalHomeNum = 1;
			
			if(totalHomeNum == originalHomeNum) {
				System.out.println("#"+t+" "+totalHomeNum);
				continue;
			}
			
			for(int r=2; r<=citySize+1; r++) {
				int currHomeNum = findStart(r);
				//System.out.println("보안범위영역이 "+r+"일때 가능한 집의 최대 개수는 : "+currHomeNum);
				
				dp[r] = (cost*currHomeNum) - (r*r + (r-1)*(r-1));
				//System.out.println("이때의 이익: "+dp[r]);
				
				if(dp[r]>=0) {
					totalHomeNum = Math.max(totalHomeNum, currHomeNum);
				}
				
				if(totalHomeNum == originalHomeNum) {
					break;
				}
			}
			
			System.out.println("#"+t+" "+totalHomeNum);
			
		}
	}
	
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {-1, 1, 0, 0};
	public static int findStart(int range) {
		Queue<int[]> q = new ArrayDeque<>();
		int cityCnt = 0;
		
		for(int i=0; i<citySize; i++) {
			for(int j=0; j<citySize; j++) {
				q.offer(new int[] {i, j}); // 이게 보안 영역의 중심 좌표가 됨.
				int currCnt = BFS(i, j, range);
				cityCnt = Math.max(cityCnt, currCnt);
			}
		}
		return cityCnt;
	}
	
	public static int BFS(int i, int j, int range) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {i, j});
		
		int cityCnt = 0;
		if(city[i][j] == 1) {
			cityCnt++;
		}
		
		boolean[][] visited = new boolean[citySize][citySize];
		visited[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			
			for(int d=0; d<4; d++) {
				int nx = dx[d]+curr[0];
				int ny = dy[d]+curr[1];
				
				int dist = Math.abs(nx-i)+Math.abs(ny-j);
				
				if(nx>=0 && nx<citySize && ny>=0 && ny<citySize && (dist <= range-1) && !visited[nx][ny]) {
					if(city[nx][ny] == 1) {
						cityCnt++;
					}
					q.offer(new int[] {nx, ny});
					visited[nx][ny] = true;
				}
			} 
		}
		
		return cityCnt;
	}

}
