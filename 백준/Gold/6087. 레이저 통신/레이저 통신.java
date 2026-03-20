import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 6087. 레이저 통신
 * 
 * 1. 입력받기
 * 	1-1. 너비 width, 높이 height
 * 	1-2. 맵 정보 입력받기 -> C 좌표 기록
 * 
 * 2. BFS
 * 	2-1. C좌표 중 하나를 넣고 시작
 * 	2-2. while Q:
 * 		2-2-1. 현재 좌표가 다른 C 좌표면 break
 * 		2-2-2. 4방향으로 이동하면서,
 * 			2-2-2-1. 현재 방향과 다른 경우에만 거울 개수 +1 증가
 * 			2-2-2-2. 현재 방향으로 갔을때 맵에서 안벗어나고, 벽이 아니라면 계속 가기
 * 				2-2-2-2-1. 현재 칸에 기록된 거울 개수보다 지금 경로의 거울 개수가 더 적으면 가망이 있음.
 * 					2-2-2-2-1-1. 최소 거울 개수 갱신 후 큐에 넣어주기.
 * 				2-2-2-2-2. 지금 경로의 거울 개수가 더 많아버리면 가망 없음. break
 * 				2-2-2-2-3. 현재 방향으로 1만큼 이동시키기
 * 
 * [정리]
 * 1. 원래 BFS는 방문처리를 해주는데 이건 꺾는 방향을 세어야 하므로 방문처리를 하면 제대로 최소 거울개수를 셀수가 없다.
 * 
 * 2. 그래서 visited배열에 최소 거울개수를 저장하며 모든 곳을 돌리면 메모리초과가 남.
 * 		-> 같은 거울 개수를 쓰는 경로들이 많으면 그만큼 갔던 곳을 다시 큐에 넣어야 하므로...
 * 
 * 3. 그래서 직진을 하면서 최소 거울 개수보다 지금 경로의 거울 개수가 더 작을 때만 큐에 넣어주기로 변경
 * 	- 즉, 기존에는 맵 안벗어나고, 최소 거울 개수보다 작으면 모든 경로에 해당하는 위치를 하나하나 다 큐에 넣었는데,
 *  - 이건 직진을 하다가 꺾어볼 부분만 큐에 넣는 것.
 *  - 이건 최소거리가 아니라 최소 거울 개수니까
 *
 */
public class Main {
	static int width;
	static int height;
	static char[][] map;
	static int[][] visited;
	static int startX;
	static int startY;
	static Deque<int[]> q;
	static int mirrorCnt;
	
	static int[] dx = {0, 0, -1, 1}; // 왼, 오, 위, 아
	static int[] dy = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());
		
		map = new char[height][width];
		visited = new int[height][width];
		
		// 시작점부터 이 좌표까지의 최소 거울 개수를 저장하므로 초기화는 최대값으로
		for(int v=0; v<height; v++) {
			Arrays.fill(visited[v], Integer.MAX_VALUE);
		}
		
		for(int h=0; h<height; h++) {
			char[] line = br.readLine().toCharArray();
			for(int w=0; w<width; w++) {
				char value = line[w];
				if(value == 'C') {
					startX = h;
					startY = w;
				}
				map[h][w] = value;
			}
		}// 입력 끝
		
		q = new ArrayDeque<>();
		q.offer(new int[] {startX, startY, 0, -1}); // x좌표, y좌표, 거울개수, 방향(처음이므로 -1)
		visited[startX][startY] = 0;
		mirrorCnt = Integer.MAX_VALUE;
		
		BFS();
		
		System.out.println(mirrorCnt);
		
	}
	
	public static void BFS() {
	    while (!q.isEmpty()) {
	        int[] curr = q.poll();
	        int cx = curr[0];
	        int cy = curr[1];
	        int cm = curr[2];
	        int cd = curr[3];

	        // 목표 지점 도달 시 최솟값 갱신
	        if (map[cx][cy] == 'C' && (cx != startX || cy != startY)) {
	            mirrorCnt = Math.min(mirrorCnt, cm);
	            continue;
	        }

	        for (int d = 0; d < 4; d++) {
	            int nx = cx + dx[d];
	            int ny = cy + dy[d];

	            int nm = (cd == -1) ? cm : (cd == d ? cm : cm + 1); // 새 거울 개수
	            
	            // 특정 방향(d)으로 벽을 만나거나 맵 끝에 갈 때까지 직진
	            while (nx >= 0 && nx < height && ny >= 0 && ny < width && map[nx][ny] != '*') {
	                
	                // 현재 칸에 기록된 거울 개수보다 지금 경로의 거울 개수가 더 적을 때만 전진
	                if (visited[nx][ny] < cm) break; // 이미 더 좋은 경로가 있다면 이 방향은 가망 없음

	                if (visited[nx][ny] > cm) { // 일단 현재 거울 개수가 더 작다면 가망이 있음
	                    visited[nx][ny] = cm;
	                    q.offer(new int[] {nx, ny, nm, d}); // 갱신하고 큐에 넣어주기
	                }
	                
	                // 같은 방향으로 계속 전진
	                nx += dx[d];
	                ny += dy[d];
	            }
	        }
	    }
	}

}
