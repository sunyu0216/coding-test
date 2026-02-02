import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 
 * @author seonyu
 * 
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 테스트케이스  번호 입력받기
 * 		1-2. 16*16 배열 생성 -> maze
 * 		1-3. 16*16 미로 입력받기 -> 2가 출발점, 3이 도착점
 * 
 * 2. 출발점의 좌표를 q에 넣고 BFS
 * 		2-1. 현재 좌표가 3이라면 stop, 1출력 (가능)
 * 		2-2. 아니라면 현재 좌표 q에 넣고 반복
 * 		2-3. while 문 벗어나면 0출력(불가능)
 *
 */
public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int testcase = 1; testcase<=10; testcase++) {
			
			int[][] maze = new int[16][16];
			boolean[][] visited = new boolean[16][16];
			
			int tc = Integer.parseInt(br.readLine());
			int startX=0, startY=0, endX=0, endY=0;
			
			for(int r=0; r<16; r++) {
				String line = br.readLine();
				for (int c=0; c<16; c++) {
				    // 문자를 숫자로 변환
					int state = line.charAt(c) - '0'; 
					if(state == 2) {
						//System.out.println("출발점: "+ r + c);
						startX = r;
						startY = c;
					}
					if(state == 3) {
						//System.out.println("도착점: "+ r + c);
						endX = r;
						endY = c;
					}
					maze[r][c] = state;
				}
			}
			
			Deque<int[]> q = new ArrayDeque<>();
			q.offer(new int[] {startX, startY});
			visited[startX][startY] = true;
			
			int[] dx = new int[] {0, 0, 1, -1};
			int[] dy = new int[] {1, -1, 0, 0};
			int answer = 0;
			
			while(!q.isEmpty()) {
				int[] current = q.poll();
				int x = current[0];
				int y = current[1];
				
				if(x==endX && y==endY) {
					answer = 1;
					break;
				}
				
				for(int i=0; i<4; i++) {
					int nx = dx[i]+x;
					int ny = dy[i]+y;
					
					if((0<=nx && nx<16) && (0<=ny && ny<16) && !visited[nx][ny] && (maze[nx][ny]!=1)) {
						q.offer(new int[] {nx, ny});
						visited[nx][ny] = true;
					}
				}
			}
			
			System.out.println("#"+tc+" "+answer);
		}
	}
}
