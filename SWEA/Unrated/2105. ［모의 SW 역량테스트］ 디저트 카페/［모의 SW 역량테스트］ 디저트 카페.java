import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 2105. 디저트 카페
 * 
 * @see #main(String[])
 * 1. 입력받기 & 변수 선언
 * 	1-1. 테스트케이스개수 T
 * 	1-2. 지역의 한 변의 길이 size
 * 	1-3. 디저트 종류에 대한 정보 map[size][size]
 * 
 * 	1-4. 방문처리 visited[size][size]
 * 	1-5. 방문했던 가게들의 디저트 종류 dessertType[101] -> 디저트 종류는 100가지 이하이므로
 * 	1-6. 대각선 탐색용 dx[4] = {1, 1, -1, -1} -> 대각선 4방향 (오아, 왼아, 왼위, 오위)
 * 				   dy[4] = {1, -1, -1, 1}
 * 	1-7. 시작 위치 startX, startY
 * 
 * 
 * 2. 모든 맵의 좌표를 순회하며 DFS를 적용하기
 * 	2-1. dfs를 호출할 때마다 dessertCount, start좌표, dessertType 초기화해주기
	2-2. 시작좌표 방문 표시
	2-3. dfs 시작 -> @see #dfs(int, int, int, int)
	2-4. 방문 표시 복구해주기
 * 	
 * @see #dfs(int, int, int, int)
 * 3. DFS
 * 	3-1. 사각형을 그리며 움직여야 함 = 꺾는 방향은 정해져 있음 = 즉 현재 방향의 이전 방향으로는 가면 안됨 = 현재 방향~마지막 방향까지 순회하며,
 * 		3-1-1. 해당 방향으로 움직인 좌표를 가지고
 * 
 * 		3-1-2. 기저조건: 해당 좌표가 시작위치인 경우
 * 			a. 디저트 종류의 개수를 갱신하고
 * 			b. 리턴
 * 
 * 		3-1-3. 유도탐색: 해당 좌표가 갈 수 있는 방향 탐색
 * 			a. 맵을 벗어나거나, 이미 방문한 지역이거나(시작위치 제외), 현재까지 방문했던 디저트의 종류와 같은 디저트를 파는 곳은 갈 수 없음.
 * 			b. 갈 수 있는 방향을 만난다면, 
 * 				b-a. 해당 좌표의 디저트 종류에 true 표시, 방문 배열에 true 표시
 * 				b-b. 디저트 종류의 개수 +1한 값과 해당 좌표로 재귀 호출
 * 				b-c. 디저트 종류, 방문배열 복구
 *
 */
public class Solution {
	static int size;
	static int[][] map;
	//static boolean[][] visited; -> gemini) 필요없음.. dessertType이 이미 방문한 곳을 걸러주는 역할도 하기 때문에..
	static boolean[] dessertType;
	static int[] dx = new int[] {1, 1, -1, -1};
	static int[] dy = new int[] {1, -1, -1, 1};
	static int startX; static int startY;
	static int dessertCount;
	static int totalMaxCount;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			size = Integer.parseInt(br.readLine());
			map = new int[size][size];
			//visited = new boolean[size][size];
			totalMaxCount = -1;
			
			for(int i=0; i<size; i++) {
				st = new StringTokenizer(br.readLine());
				
				for(int j=0; j<size; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 2. 모든 맵의 좌표를 순회하며 DFS를 적용하기
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					// 2-1. dfs를 호출할 때마다 dessertCount, start 좌표, dessertType 초기화
					dessertCount = 0;
					startX = i;
					startY = j;
					dessertType = new boolean[101];
					
					// 2-2. 시작좌표 방문 표시
					dessertType[map[i][j]] = true;
					// 2-3. dfs 시작 -> @see #dfs(int, int, int, int)
					dfs(i, j, 1, 0);
					// 2-4. 방문 표시 복구해주기
					dessertType[map[i][j]] = false;
					
				}
			}
			
			System.out.println("#" + t + " " + totalMaxCount);
		}
	}
	
	
	// 3. DFS
	static void dfs(int currX, int currY, int count, int dir) {
		// 3-1. 사각형을 그리며 움직여야 함 = 꺾는 방향은 정해져 있음(오아, 왼아, 왼위, 오위) = 즉 현재 방향의 이전 방향으로는 가면 안됨 = 현재 방향~마지막 방향까지 순회하며,
		for(int d=dir; d<4; d++) {
			// 3-1-1. 해당 방향으로 움직인 좌표를 가지고
			int nx = currX + dx[d];
			int ny = currY + dy[d];
			
			// 3-1-2. 기저조건: 해당 좌표가 시작위치인 경우
			if(nx == startX && ny == startY && count >= 3) {
				// a. 디저트 종류의 개수를 갱신하고
				totalMaxCount = Math.max(totalMaxCount, count);
				return;
			}
			
			// 3-1-3. 유도탐색: 해당 좌표가 갈 수 있는 방향 탐색
			// a. 맵을 벗어나거나, 이미 방문한 지역이거나(시작위치 제외), 현재까지 방문했던 디저트의 종류와 같은 디저트를 파는 곳은 갈 수 없음.
			if(isRange(nx, ny) && !dessertType[map[nx][ny]]) {
				// b. 갈 수 있는 방향을 만난다면, 
				
				// b-a. 해당 좌표의 디저트 종류에 true 표시, 방문 배열에 true 표시
				dessertType[map[nx][ny]] = true;
				//visited[nx][ny] = true;
				
				// b-b. 디저트 종류의 개수 +1한 값과 해당 좌표로 재귀 호출
				dfs(nx, ny, count+1, d);
				
				// b-c. 디저트 종류, 방문배열 복구
				dessertType[map[nx][ny]] = false;
				//visited[nx][ny] = false;
			}
		}
	}
	
	
	// 맵의 범위를 벗어나는지 체크해주는 함수
	static boolean isRange(int x, int y) {
		if((0<=x && x<size) && (0<=y && y<size)) {
			return true;
		}else {
			return false;
		}
	}
}
