import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 
 * @author seonyu
 * SWEA 1227. 미로2
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 번호 입력받기
 * 	1-2. map[100][100] 생성
 * 		1-2-1. 입력받으면서 출발지 2와 도착지 3의 좌표를 저장
 * 
 * 2. bfs 탐색하기
 * 	2-1. 출발지 좌표를 q에 넣기
 * 	2-2. while(q)
 * 		2-2-1. 현재 좌표가 3이라면(= 도착지라면) stop, 1 출력
 * 		2-2-2. 아니라면 현재 좌표를 q에 넣고 반복
 * 	2-3. 0 출력
 *
 */

public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int testcase = 1; testcase<=10; testcase++) {
			
			int[][] maze = new int[100][100];
			boolean[][] visited = new boolean[100][100];
			
			int tc = Integer.parseInt(br.readLine());
			int startX=0, startY=0, endX=0, endY=0;
			
			for(int r=0; r<100; r++) {
				String line = br.readLine();
				for (int c=0; c<100; c++) {
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
					
					if((0<=nx && nx<100) && (0<=ny && ny<100) && !visited[nx][ny] && (maze[nx][ny]!=1)) {
						q.offer(new int[] {nx, ny});
						visited[nx][ny] = true;
					}
				}
			}
			
			System.out.println("#"+tc+" "+answer);
		}
	}
}
