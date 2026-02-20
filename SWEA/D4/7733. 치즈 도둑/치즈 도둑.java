import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 7733. 치즈 도둑
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 치즈 한 변의 길이 cheeseSize
 * 	1-3. 치즈가 맛있는 정도 cheese[][]를 입력받으며 가장 큰 맛있는 정도를 파악 maxDegree
 * 
 *
 * 2. 가장 큰 맛있는 정도(날짜)까지 반복문을 돌리며,
 * 	2-1. cheese[][]에서 현재 날짜에 해당하는 날짜를 없애기
 * 	2-2. visited[][] 배열 초기화
 * 	2-3. BFS로 덩어리가 몇개인지 파악하기
 * 	2-4. 현재 가장 큰 덩어리 개수와 비교 후 갱신
 * 
 * 3. BFS
 * 	3-1. 덩어리 개수 cheeseNum
 * 	3-2. cheese[][]의 전체를 돌면서,
 * 		3-2-1. 아직 방문하지 않고, 치즈맛이 현재 날짜보다 큰 치즈가 있다면
 * 			a. 큐에 넣기
 * 			b. cheeseNum++;
 * 
 * 		3-2-2. 큐를 돌며
 * 			a. 상하좌우를 살피며 붙어있는 치즈들을 큐에 넣기
 * 			b. 방문한 치즈들은 방문처리 해주기
 * 
 * 	3-3. 다 돌았다면 cheeseNum 반환
 */
public class Solution {
	static int cheeseSize;
	static int[][] cheese;
	static int maxDegree;
	static boolean[][] visited;
	static int[] dx = {0, 0, 1, -1};
	static int[] dy = {1, -1, 0, 0};
	static int totalMaxNum;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			cheeseSize = Integer.parseInt(br.readLine());
			cheese = new int[cheeseSize][cheeseSize];
			maxDegree = 0;
			
			for(int i=0; i<cheeseSize; i++) {
				st = new StringTokenizer(br.readLine());
				
				for(int j=0; j<cheeseSize; j++) {
					int value = Integer.parseInt(st.nextToken());
					maxDegree = Math.max(maxDegree, value);
					cheese[i][j] = value;
				}
			}
			
			totalMaxNum = 0;
			// 2. 가장 큰 맛있는 정도(날짜)까지 반복문을 돌리며,
			for(int day=0; day<maxDegree; day++) {
				// 2-1. cheese[][]에서 현재 날짜에 해당하는 날짜를 없애기
				// 2-2. visited[][] 배열 초기화
				visited = new boolean[cheeseSize][cheeseSize];
				
				// 2-3. BFS로 덩어리가 몇개인지 파악하기
				int currNum = bfs(day);
				
				// 2-4. 현재 가장 큰 덩어리 개수와 비교 후 갱신
				totalMaxNum = Math.max(totalMaxNum, currNum);
			}
			
			System.out.println("#"+t+" "+totalMaxNum);
		}
	}
	
	static int bfs(int day) {
		Queue<int[]> q = new ArrayDeque<>();
		
		// 3-1. 덩어리 개수 cheeseNum
		int cheeseNum = 0;
		
		// 3-2. cheese[][]의 전체를 돌면서,
		for(int i=0; i<cheeseSize; i++) {
			for(int j=0; j<cheeseSize; j++) {
				
				// 3-2-1. 아직 방문하지 않고, 치즈맛이 현재 날짜보다 큰 치즈가 있다면
				if(!visited[i][j] && cheese[i][j]>day) {
					cheeseNum++; // 덩어리 개수 1 증가
					q.offer(new int[] {i, j});
					visited[i][j] = true;
				}
				
				// 3-2-2. 큐를 돌며
				while(!q.isEmpty()) {
					int[] curr = q.poll();
					int x = curr[0];
					int y = curr[1];
					
					// a. 상하좌우를 살피며 붙어있는 치즈들을 큐에 넣기
					for(int d=0; d<4; d++) {
						int nx = x+dx[d];
						int ny = y+dy[d];
						
						if((0<=nx && nx<cheeseSize) && (0<=ny && ny<cheeseSize) && cheese[nx][ny] > day && !visited[nx][ny]) {
							q.offer(new int[] {nx, ny});
							// b. 방문한 치즈들은 방문처리 해주기
							visited[nx][ny] = true;
						}
					}
				}
			}
		}
		// 3-3. 다 돌았다면 cheeseNum 반환
		return cheeseNum;
	}	

}
