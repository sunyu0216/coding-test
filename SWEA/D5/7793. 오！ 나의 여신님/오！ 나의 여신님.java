import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 7793. 오! 나의 여신님
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 행 개수 rowSize, 열 개수 colSize
 * 	1-3. 맵 정보 char[][] map
 * 
 * 2. 악마의 손아귀를 확장시키기(더 이상 확장할 곳이 없을 때까지)
 * 	* 이떄 악마의 손아귀가 영영 닿지 않는 곳도 존재하므로 악마 맵을 최댓값으로 초기화해주기
 * 	2-1. 이때 수연이의 위치는 평지라고 가정하고 하기
 * 	2-2. 큐에 현재 악마의 위치와 0(확장된 시간) 넣고,
 * 	2-3. 큐 반복
 * 		2-3-1. 현재 큐 값을 꺼내서 해당 위치에 시간을 표시해주기
 * 		2-3-2. 확장 이동 시킨 값이 맵 밖을 안벗어나고, 평지일 때
 * 			2-3-2-1. 큐에 현재시간 +1 한 값과 함께 위치를 넣어주고
 * 			2-3-2-2. 방문처리
 * 
 * 3. 수연이를 여신의 공간으로 이동시키기
 * 	3-1. 수연이의 위치와 0(이동하는 시간) 큐에 넣기
 * 	3-2. 큐 반복
 * 		3-2-1. 현재 큐 값을 꺼내서
 * 		3-2-2. 현재 위치가 여신의 위치인지 확인
 * 			3-2-2-1. 맞다면 현재시간 return
 * 			3-2-2-2. 아니라면 pass
 * 		3-2-3. 확장 이동 시킨 값이 맵 밖을 안 벗어나고, 돌이 있는 곳도 아니고, 해당 위치의 악마맵을 조회했을 때 현재 시간+1보다 크다면(수연이의 현재 시간 + 1 < 해당 위치의 악마 도달 시간)
 * 			3-2-3-1. 큐에 해당 위치와 현재시간+1 넣어주기
 * 			3-2-3-2. 방문처리
 * 	3-3. 큐가 끝나버렸다면 GAME OVER 출력
 *
 */
public class Solution {
	static int T;
	static int rowSize;
	static int colSize;
	static char[][] map;
	static int[][] devilMap;
	
	static int goddessX;
	static int goddessY;
	
	static Queue<int[]> Sq;
	static Queue<int[]> Dq;
	static boolean[][] Svisited;
	static boolean[][] Dvisited;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int totalTime;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			rowSize = Integer.parseInt(st.nextToken());
			colSize = Integer.parseInt(st.nextToken());
			map = new char[rowSize][colSize];
			devilMap = new int[rowSize][colSize];
			for(int i = 0; i < rowSize; i++) {
			    Arrays.fill(devilMap[i], Integer.MAX_VALUE);
			}
			
			Sq = new ArrayDeque<>();
			Dq = new ArrayDeque<>();
			Svisited = new boolean[rowSize][colSize];
			Dvisited = new boolean[rowSize][colSize];
			
			for(int r=0; r<rowSize; r++) {
				char[] line = br.readLine().toCharArray();
				for(int c=0; c<colSize; c++) {
					char value = line[c];
					map[r][c] = value;
					
					if(value == 'D') {
						// 여신위치
						goddessX = r;
						goddessY = c;
					}else if(value == 'S') {
						// 수연이의 위치
						Sq.offer(new int[] {r, c, 0});
						map[r][c] = '.'; //수연이 위치는 평지로 취급
					}else if(value == '*') {
						// 악마의 위치
						Dq.offer(new int[] {r, c, 0});
					}
					
				}
			}// 입력 끝
			
			totalTime = 0;
			
			moveDevil(); // 악마 움직이기
			if(moveSuyeon()) {
				System.out.println("#"+t+" " + totalTime);
			}else {
				System.out.println("#"+t+" " + "GAME OVER");
			}
			
		}
	}
	
	public static void moveDevil() {
		while(!Dq.isEmpty()) {
			int[] curr = Dq.poll();
			int cx = curr[0];
			int cy = curr[1];
			int ctime = curr[2];
			
			devilMap[cx][cy] = ctime;
			
			for(int d=0; d<4; d++) {
				int nx = cx + dx[d];
				int ny = cy + dy[d];
				
				if(nx>=0 && nx<rowSize && ny>=0 && ny<colSize && !Dvisited[nx][ny]) {
					if(map[nx][ny] == '.' || map[nx][ny]=='S') { // 평지거나 수연이의 위치일떄 확장 가능
						Dq.offer(new int[] {nx, ny, ctime+1});
						Dvisited[nx][ny] = true;
					}
				}
			}
		}
	}
	
	public static boolean moveSuyeon() {
		while(!Sq.isEmpty()) {
			int[] curr = Sq.poll();
			int cx = curr[0];
			int cy = curr[1];
			int ctime = curr[2];
			
			
			if(cx == goddessX && cy == goddessY) {
				totalTime = ctime;
				return true;
			}
			
			for(int d=0; d<4; d++) {
				int nx = cx + dx[d];
				int ny = cy + dy[d];
				
				if(nx>=0 && nx<rowSize && ny>=0 && ny<colSize && !Svisited[nx][ny]) {
					if(map[nx][ny] == '.' || map[nx][ny] == 'D') { // 평지거나 여신님 위치일 때
						if(ctime+1<devilMap[nx][ny]) { // 악마 확장시간보다 이 위치에 먼저 도착했을 때
							Sq.offer(new int[] {nx, ny, ctime+1});
							Svisited[nx][ny] = true;
						}
					}
				}
			}
		}
		return false;
	}

}
