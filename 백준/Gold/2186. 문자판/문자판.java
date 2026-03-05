import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 2186. 문자판
 * 
 * 1. 입력받기
 * 	1-1. 행의 크기, 열의 크기, 이동할 수 있는 범위 (rowSize, colSize, range)
 * 	1-2. 맵에 알파벳 대문자 입력받기
 * 	1-3. 영단어 입력받기
 * 
 * 
 * 2. dp[x좌표][y좌표][영단어 인덱스] = 해당 좌표에서 시작해 해당 영단어 인덱스부터 마지막 글자까지 완성할 수 있는 총 방법의 수.
 * 
 * 3. dfs
 * 	3-1. 기저조건: 영단어 인덱스가 마지막이라면 = 마지막 영단어까지 찾았다면
 * 		3-1-1. return 1 -> 한가지 경로가 완성이므로 1을 리턴
 * 
 * 	3-2. 만약 해당 dp값이 -1이 아니라면 이미 방문한 곳이므로 바로 dp값을 리턴
 * 
 * 	3-3. 위 조건을 모두 pass했다면 마지막 영단어도 아니고, 방문했던 곳도 아니므로 
 * 		3-3-0. 방문처리 -> dp값 0으로 바꿔주기
 * 		3-3-1. 범위만큼 상하좌우 이동을 반복하며,
 * 			3-3-1-1. 맵을 벗어나지 않고, 현재 영어 인덱스+1한 알파벳값이 있다면
 * 				3-3-1-1-1. dfs함수를 호출(이동한 좌표, 영어인덱스 +1) 한 값을 dp에 누적시키기
 * 				3-3-1-1-2. 만약 알파벳 값이 없다면 pass
 * 		3-3-2. 상하좌우 이동이 다 끝났다면 현재 dp값을 리턴
 * 	
 *
 */
public class Main {
	
	static int rowSize;
	static int colSize;
	static int range;
	static char[][] map;
	static int[][][] dp;
	static char[] targetWord;
	static int wordLen;
	
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {1, -1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		range = Integer.parseInt(st.nextToken());
		
		map = new char[rowSize][colSize];
		for(int r=0; r<rowSize; r++) {
			map[r] = br.readLine().toCharArray();
		}
		
		targetWord = br.readLine().toCharArray();
		wordLen = targetWord.length;
		
		dp = new int[rowSize][colSize][wordLen];
		for (int[][] row : dp) {
		    for (int[] col : row) {
		        Arrays.fill(col, -1);
		    }
		}
		
		int answer = 0;
		
		// 맵 전체를 순회하면서,
		for(int r=0; r<rowSize; r++) {
			for(int c=0; c<colSize; c++) {
				
				// 시작 문자를 찾으면,
				if(map[r][c] == targetWord[0]) {
					// 여기서 dfs 호출
					answer += dfs(r, c, 0);
				}
			}
		}
		
		System.out.println(answer);
	}
	
	public static int dfs(int x, int y, int idx) {
		// 3-1. 기저조건: 영단어 인덱스가 마지막이라면 = 마지막 영단어까지 찾았다면
		if(idx == wordLen-1) {
			return 1;
		}
		
		// 3-2. 만약 해당 dp값이 -1이 아니라면 이미 방문한 곳이므로 바로 dp값을 리턴
		if(dp[x][y][idx] != -1) {
			return dp[x][y][idx];
		}
		
		// 3-3. 위 조건을 모두 pass했다면 마지막 영단어도 아니고, 방문했던 곳도 아니므로 
		dp[x][y][idx] = 0;
		
		for(int d=0; d<4; d++) {
			for(int k=1; k<=range; k++) {
				int nx = x + dx[d]*k;
				int ny = y + dy[d]*k;
				
				if((0<=nx && nx<rowSize) && (0<=ny && ny<colSize) && map[nx][ny] == targetWord[idx+1]) {
					dp[x][y][idx] += dfs(nx, ny, idx+1);
				}
			}
		}
		
		return dp[x][y][idx];
	}

}
