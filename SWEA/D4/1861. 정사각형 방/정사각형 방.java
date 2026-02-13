import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1861. 정사각형 방
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 T
 * 	1-2. 맵의 크기 Num
 * 	1-3. Num의 수만큼 각 줄의 숫자 정보를 입력받기 map[][]
 * 
 * 2. visited[] 배열을 Num*Num+1 의 크기로 생성
 * 3. map을 순회하며
 * 	3-1. 현재 좌표에서 갈 수 있는 경로가 있다면 visited[해당 좌표에 있는 방의 숫자] = true
 * 	3-2. 없다면 false
 * 
 * 4. visited 배열을 순회하며
 * 	4-1. true를 만나면 구간개수와 방번호를 기록
 * 	4-2. false를 만나면 
 * 		4-2-1. 지금까지 센 구간 개수+1을 한 값과 최대 구간길이를 비교 후 갱신
 * 		4-2-2. 방번호도 비교 후 더 작은 걸 갱신
 * 
 * 5. 처음 출발해야 하는 방 번호와 최대 몇 개의 방을 이동할 수 있는지 출력
 *
 */
public class Solution {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			int Num = Integer.parseInt(br.readLine());
			int[][] map = new int[Num][Num];
			boolean[] visited = new boolean[Num*Num+1];
			int[] dx = new int[] {0, 0, 1, -1};
			int[] dy = new int[] {1, -1, 0, 0};
			
			for(int i=0; i<Num; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<Num; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 3. map을 순회하며
			for(int i=0; i<Num; i++) {
				for(int j=0; j<Num; j++) {
					for(int d=0; d<4; d++) {
						int nx = i+dx[d];
						int ny = j+dy[d];
						int currentRoomNum = map[i][j];
						
						if((0<=nx && nx<Num)&&(0<=ny && ny<Num)) {
							if(currentRoomNum+1 == map[nx][ny]) {
								visited[currentRoomNum] = true;
							}
						}
					}
				}
			}
			
			//  4. visited 배열을 순회하며
			int currentCount = 0;
			int currentRoomNum = 0;
			int maxCount = 0;
			int minRoomNum = Num*Num+1;
			boolean isCount = false;
			
			for(int v=0; v<Num*Num+1; v++) {
				
				if(visited[v]) {
					if(!isCount) {
						currentRoomNum = v;
						isCount = true;
					}
					currentCount++;
				}else {
					if(isCount) {
						currentCount++;
						if(currentCount > maxCount) {
							maxCount = currentCount;
							minRoomNum = currentRoomNum;
						}else if(currentCount == maxCount) {
							minRoomNum = Math.min(minRoomNum, currentRoomNum);
						}
						currentCount = 0;
						currentRoomNum = 0;
						isCount = false;
					}
					
				}
			}
			
			// 5. 처음 출발해야 하는 방 번호와 최대 몇 개의 방을 이동할 수 있는지 출력
			System.out.println("#"+t+" "+minRoomNum+" "+maxCount);
		}
	}

}
