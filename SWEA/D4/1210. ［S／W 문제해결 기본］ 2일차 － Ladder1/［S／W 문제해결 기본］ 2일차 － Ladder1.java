import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 0. static 변수들
 * 		0-1. 사다리를 탐지할 위, 왼쪽, 오른쪽으로 이동시켜주는 배열 dx, dy
 * 		0-2. 도착지 좌표 targetX, targetY
 * 
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 테스트케이스 번호 입력받기 testcase
 * 		1-2. 100x100의 사다리 현황 입력받기 totalLadders[100][100]
 * 			a. 2인 부분을 체크해 도착지 좌표에 저장하기 targetX, targetY
 * 
 * 2. 도착지에서 역순으로 BFS
 * 		2-1. 큐 생성 ArrayDeque
 * 		2-2. 방문배열 생성하기 visited[100][100]
 * 		2-3. 도착지 방문처리 + 도착지 큐에 넣어주기
 * 		2-4. while(!큐.isEmpty())
 * 			a. 큐에서 꺼낸 X 좌표가 0일 경우에 break
 * 			b. 아니라면 계속 이동
 * 
 *
 */
public class Solution {
	static int[] dx = new int[] {0, 0, -1};
	static int[] dy = new int[] {-1, 1, 0};
	static int targetX;
	static int targetY;

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int T=0; T<10; T++) {
			int testcase = Integer.parseInt(br.readLine());
			
			int[][] totalLadders = new int[100][100];
			List<Integer> startPositions = new ArrayList<>();
			
			for(int i=0; i<100; i++) {
				st = new StringTokenizer(br.readLine());
				
				for(int j=0; j<100; j++) {
					int input = Integer.parseInt(st.nextToken());
					
					if(input==2) {
						targetX=i;
						targetY=j;
						//System.out.println("도착지: "+targetX+", "+targetY);
					}
					
					totalLadders[i][j] = input;
				}
			}
			
			// 2. BFS
			Deque<int[]> dq = new ArrayDeque<>();
			boolean[][] visited = new boolean[100][100];
			int result = 0;
			
			dq.offer(new int[] {targetX, targetY});
			visited[targetX][targetY] = true;
			
			while(!dq.isEmpty()) {
				int[] curr = dq.poll();
				int currX = curr[0];
				int currY = curr[1];
				
				if(currX == 0) {
					result = currY;
					break;
				}
				
				for(int d=0; d<3; d++) {
					int nx = dx[d]+currX;
					int ny = dy[d]+currY;
					
					if((0<=nx && nx<100) && (0<=ny && ny<100) && !visited[nx][ny] && totalLadders[nx][ny]==1) {
							//System.out.println("왼쪽 또는 오른쪽으로 이동함");
							dq.add(new int[] {nx, ny});
							visited[nx][ny] = true;
							break;
					}
				}
			}
			System.out.println("#"+testcase+ " "+result);
		}
	}
}
