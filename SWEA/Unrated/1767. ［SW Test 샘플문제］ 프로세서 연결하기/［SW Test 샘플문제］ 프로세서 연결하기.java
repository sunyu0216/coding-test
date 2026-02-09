import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1767. 프로세서 연결하기
 * 
 * 1. 입력받기 및 필요한 변수 생성
 * 	1-1. 테스트케이스 개수 T 입력받기
 * 	1-2. 테스트케이스대로 순회하며,
 * 		1-2-1. 전체 맵의 크기 size
 * 		1-2-2. 전체 맵 배열 map[][]
 * 		1-2-3. Core 좌표 기록 core[][], 연결된 Core 개수 connectedNum
 * 		1-2-4. 멕시노스 초기 상태 입력받기
 * 			a. 입력받으면서, Core의 좌표 core[][] 에 기록하기
 * 			b. 이미 Core가 가장자리에 위치해있다면 connectedNum++
 * 		1-2-5. 전선의 전체 길이를 기록할 totalLength
 * 		1-2-6. 방향 이동을 위한 dx[] dy[];
 * 
 * 
 * @see #dfs(int, int, int)
 * 2. 재귀함수 설계 
 * 	2-1. 기저조건: Core 개수를 다 확인했을 때
 * 		2-1-1. 현재 Core 개수가 지금까지의 연결된 Core 개수보다 많은지 판단
 * 			a. 많으면 무조건 현재 length로 갱신
 * 			b. 같으면 totalLength가 지금까지의 totalLength보다 짧은지 판단 후 갱신
 * 		2-1-2. totalLength가 지금까지의 totalLength보다 짧은지 판단
 * 	2-2. 현재 인덱스에 해당하는 Core를 찾아 네 방향으로 반복문을 돌며,
 * 		2-2-1. 방향 정하고, 그 방향으로 끝까지 도달할 수 있는지 체크 -> isPossible()
 * 			a. 도달할 수 있다면 map에 전선을 표시 -> fill 하고 재귀함수 호출
 * 			b. return으로 돌아올 경우 다시 전선 복구 시키기
 * 		2-2-2. 이 Core에는 전선을 연결하지 않는 경우의 재귀함수도 호출
 * 
 * 
 * @see #isPossible(int, int, int)
 * 3. 해당 방향으로 끝까지 전선을 연결할 수 있는지 탐색해주는 함수
 * 	3-1. 매개변수로 들어온 방향으로 전선 연결이 가능한지 체크
 * 	3-2. 연결이 가능하면 더해놓았던 전선 길이를 반환
 * 	3-3. 불가능하다면 바로 -1 반환
 * 
 * 
 * @see #fill(int, int, int, int)
 * 4. 주어진 좌표와 방향으로 전선을 채우거나 복구해주는 함수
 * 	4-1. 마지막 매개변수로 2가 들어오면 map에 전선을 2로 채워주고,
 * 	4-2. 매개변수로 0이 들어오면 다시 map에 전선을 복구시켰단 뜻으로 0으로 채워주기
 * 
 * 
 *
 */
public class Solution {
	
	static int size;
	static int[][] map;
	static List<int[]> core;
	static int initialConnected;
	static int maxConnected;
	static int totalLength;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static BufferedReader br;
	static StringTokenizer st;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		for(int t=1; t<=T; t++) {
			
			size = Integer.parseInt(br.readLine().trim());
			map = new int[size][size];
			core = new ArrayList<int[]>();
			initialConnected = 0; // 초기 가장자리 코어 수
			
			for(int i=0; i<size; i++) {
				st = new StringTokenizer(br.readLine());
				
				for(int j=0; j<size; j++) {
					int value = Integer.parseInt(st.nextToken());
					map[i][j] = value;
					
					if(value == 1) {
                        if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                            initialConnected++;
                        } else {
                            core.add(new int[]{i, j});
                        }
					}
				}
			}
			
			maxConnected = 0;
			totalLength = Integer.MAX_VALUE;
			dfs(0, 0, initialConnected);
			
			System.out.println("#" + t + " " + totalLength);
		}
	}
	
	static void dfs(int coreIdx, int currentLength, int currentConnected) {
		
		// 기저조건 달성
		if(coreIdx == core.size()) {
			//System.out.println("기저조건 달성");
			if (currentConnected > maxConnected) {
                maxConnected = currentConnected;
                totalLength = currentLength;
            } else if (currentConnected == maxConnected) {
                totalLength = Math.min(totalLength, currentLength);
            }
            return;
		}
		
	    int[] pos = core.get(coreIdx);
		
		int cx=pos[0];
		int cy=pos[1];
		
			
		for(int i=0; i<4; i++) {
			int len = isPossible(cx, cy, i);

			if (len > 0) { // 연결 가능한 경우
	             fill(cx, cy, i, 2);
	             dfs(coreIdx + 1, currentLength + len, currentConnected + 1);
	             fill(cx, cy, i, 0);
	        }

		}
	    dfs(coreIdx + 1, currentLength, currentConnected);
	}
	
	static int isPossible(int x, int y, int d) {
		int nx = x;
		int ny = y;
		int count = 0;
			
		while((0<=nx+dx[d] && nx+dx[d]<size) && (0<=ny + dy[d] && ny + dy[d]<size)) {
			if(map[nx+dx[d]][ny+dy[d]] == 0) {
				nx = nx+dx[d];
				ny = ny+dy[d];
				count++;
			}else {
				//System.out.println("이방향은 안됨. 다른 방향 찾아야함.");
				return -1;
			}
		}
			
		return count;
	}
	
	static void fill(int x, int y, int d, int flag) {
		// flag가 2이면 전선 채우기, 0면 다시 복구
		int nx = x;
		int ny = y;
		
		while((0<=nx+dx[d] && nx+dx[d]<size) && (0<=ny + dy[d] && ny + dy[d]<size)) {
			nx = nx + dx[d];
			ny = ny + dy[d];
			map[nx][ny] = flag;
		}
	}


}
