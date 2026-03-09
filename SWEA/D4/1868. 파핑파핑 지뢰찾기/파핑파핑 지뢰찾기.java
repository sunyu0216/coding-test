import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1868. 파핑파핑 지뢰찾기
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T 입력받기
 * 	1-2. 지뢰찾기 맵 크기 size
 * 	1-3. 지뢰찾기 맵 정보 입력받기 char[][] map
 * 
 * 2. 맵 전체를 순회하면서,
 * 	2-1. 현재 칸이 0이 나오는 칸인지 판단
 * 		2-1-1. dx, dy 배열을 통해 현재 칸 주위의 8칸에 지뢰가 있는지 없는지 파악하기
 * 	2-2. 0이 나오는 곳이라면, map 상에 0으로 표시, 클릭횟수++
 * 		2-2-1. bfs 탐색 시작
 * 		2-2-2. 해당 칸 주위의 8칸을 큐에 넣기
 * 		2-2-3. 큐에서 꺼낸 칸이 0인지 판단
 * 		2-2-4. 0이 맞다면 
 * 			2-2-4-1. map상에 0으로 바꿔주기
 * 			2-2-4-2. 2-2-2. 처럼 반복
 * 		2-2-5. 0이 아니라면
 * 			2-2-5-1. map상에 해당 수로 바꿔주기
 * 	2-3. 0이 안나오는 곳이라면, pass, 계속 탐색
 * 
 * 3. 이제 맵에서 남은 . 부분의 개수를 클릭횟수에 더해주기
 *
 */
public class Solution {
	static int T;
	static int size;
	static char[][] map;
	static int clickCnt;
	static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			size = Integer.parseInt(br.readLine());
			
			map = new char[size][size];
			for(int i=0; i<size; i++) {
				map[i] = br.readLine().toCharArray();
			}
			
			clickCnt = 0;
			// 2. 맵 전체를 순회하면서,
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					// 2-0. 지뢰있는칸이면 pass
					if(map[i][j]=='.') {
					
						// 2-1. 현재 칸이 0이 나오는 칸인지 판단 ->
						if(isZero(i, j) == 0) {
							// 2-2. 0이 나오는 곳이라면, map 상에 0으로 표시, 클릭횟수++
							map[i][j] = '0';
							clickCnt++;
							
							// 2-2-1. bfs 탐색 시작
							bfs(i, j);
							
//							for(int r=0; r<size; r++) {
//								for(int c=0; c<size; c++) {
//									System.out.print(map[r][c]+" ");
//								}
//								System.out.println();
//							}
//							System.out.println(clickCnt);
//							System.out.println();
//							System.out.println();
						}
					}
				}
			}
			
			// 3. 이제 맵에서 남은 . 부분의 개수를 클릭횟수에 더해주기
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					if(map[i][j] == '.') {
						clickCnt++;
						//System.out.println(clickCnt);
					}
					
				}
			}
			
			// 4. 출력
			System.out.println("#"+t+" "+clickCnt);
		}
		
	}
	
	public static int isZero(int x, int y) {
		int bombCnt = 0;
		
		for(int d=0; d<8; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if((0<=nx && nx<size) && (0<=ny && ny<size)) {
				if(map[nx][ny]=='*') {
					bombCnt++;
				}
			}
		}
		return bombCnt;
	}
	
	
	// 2-2-1. bfs 탐색 시작
	public static void bfs(int x, int y) {
		Queue<int[]> q = new ArrayDeque<>();
		
		// 2-2-2. 해당 칸 큐에 넣기
		q.offer(new int[] {x, y});
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			
			// 2-2-3. 큐에서 꺼낸 칸이 0인지 판단
			int bombCnt = isZero(curr[0], curr[1]);
			
			if(bombCnt == 0) {
				// 2-2-4. 0이 맞다면
				// 2-2-4-1. map상에 0으로 바꿔주기
				map[curr[0]][curr[1]] = '0';
				
				// 2-2-4-2. 주위 칸 8개를 큐에 넣기
				for(int d=0; d<8; d++) {
					int nx = curr[0] + dx[d];
					int ny = curr[1] + dy[d];
					
					if((0<=nx && nx<size) && (0<=ny && ny<size) && (map[nx][ny]=='.')) {
						map[nx][ny] = 'v'; // 미리 방문처리
						q.offer(new int[] {nx, ny});
					}
				}
			}else {
				// 2-2-5. 0이 아니라면  map상에 해당 수로 바꿔주기
				map[curr[0]][curr[1]] = (char)(bombCnt+'0');
			}
		}
	}

}
