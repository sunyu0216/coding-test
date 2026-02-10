import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 3109. 빵집
 * 
 * 1. 입력받기
 * 	1-1. 전체 지도의 행과 열 개수 입력받기 Row, Col
 * 	1-2. 행과 열만큼 반복하며 char map[][]에 입력받기
 * 
 * 2. 필요한 변수들
 * 	2-1. 가스관 연결 이동을 위한 dx[], dy[] -> 우선순위 이동을 위해 방향 순서는 오른쪽 대각선 위, 오른쪽, 오른쪽 대각선 아래 순으로
 * 	2-2. 연결 가능한 전체 가스관 개수  totalGas
 * 
 * 3. dfs()
 * 	3-1. 기저조건1 : 원웅이네 빵집 가스관에 도착했을 때
 * 		3-1-1. totalGas 증가
 * 		3-1-2. 리턴
 * 	3-2. 방향 3개를 반복하며 
 * 		3-2-1. 해당 방향으로 갈 수 있다면 바로 재귀 호출
 * 		3-2-2. 간 길은 방문처리 할것
 * 		3-2-3. 기저조건2 : 더 이상 갈 수 있는 길이 없을 때 -> 리턴
 * 
 */
public class Main {
	
	static int Row;
	static int Col;
	static char[][] map;
	static int totalGas;
	static int[] dx = {-1, 0, 1}; 
	static int[] dy = {1, 1, 1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		Row = Integer.parseInt(st.nextToken());
		Col = Integer.parseInt(st.nextToken());
		
		map = new char[Row][Col];
		for(int r=0; r<Row; r++) {
			map[r] = br.readLine().toCharArray();
		}
		
		totalGas = 0;
		for(int r=0; r<Row; r++) {
			dfs(r, 0);
		}
		System.out.println(totalGas);
		
	}
	
	static boolean dfs(int x, int y) {
		// 3-1. 기저조건 : 원웅이네 빵집 가스관에 도착했을 때
		if(y==Col-1) {
			//System.out.println("원웅이네 빵집 가스관에 도착함!!");
			totalGas++;
			return true;
		}
		
		// 3-2. 방향 3개를 반복하며 
		for(int i=0; i<3; i++) {
			// 3-2-1. 해당 방향으로 갈 수 있다면 바로 재귀 호출
			int nx = x+dx[i];
			int ny = y+dy[i];
			
			if((0<=nx && nx<Row) && (0<=ny && ny<Col) && map[nx][ny]=='.') {
				// 3-2-2. 간 길은 방문처리 할것
				map[nx][ny] = 'g';
				
				// [디버깅 추가] 현재 맵 상태 출력
			    //System.out.println("--- 현재 좌표: (" + nx + ", " + ny + ") ---");
			    //printMap();
				
				if(dfs(nx, ny)) {
					return true;
				}
			}
		}
		// 갈 길이 더 이상 없는 경우
		return false;
		
	}
	
	
	// [디버깅용]
	static void printMap() {
	    for (int i = 0; i < Row; i++) {
	        for (int j = 0; j < Col; j++) {
	            System.out.print(map[i][j] + " ");
	        }
	        System.out.println();
	    }
	    System.out.println("-------------------------");
	}

}
