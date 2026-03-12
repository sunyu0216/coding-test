import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 2819. 격자판의 숫자 이어 붙이기
 * 
 * 1. dfs 함수
 * 	1-1. 기저조건: 6번 이동을 다 끝냈다면,
 * 		1-1-1. 완성된 수가 집합에 있는지 체크
 * 		1-1-2. 없다면 집합에 추가하고 return
 * 
 * 	1-2 반복: 현재 위치에서 동서남북 이동을 반복
 *
 */
public class Solution {
	static int T;
	static int[][] map;
	static Set<String> numberSet;
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			map = new int[4][4];
			
			for(int i=0; i<4; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<4; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 입력 끝
			
			numberSet = new HashSet<>();
			
			// 모든 칸에서 DFS 돌리기
	        for(int i=0; i<4; i++) {
	            for(int j=0; j<4; j++) {
	                // 시작 좌표의 숫자를 미리 넣고 시작 (이동 횟수는 0)
	                dfs(0, i, j, "" + map[i][j]);
	            }
	        }
	        
	        // 2. Set의 크기가 곧 중복 없는 숫자의 개수입니다.
	        System.out.println("#" + t + " " + numberSet.size());
			
		}
		
	}
	
	public static void dfs(int moveCnt, int x, int y, String num) {
		// 1-1. 기저조건: 6번 이동을 다 끝냈다면,
		if(moveCnt == 6) {
	        numberSet.add(num);
	        return;
	    }
		
		// 1-2 반복: 현재 위치에서 동서남북 이동을 반복
		for(int d=0; d<4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(0<=nx && nx<4 && 0<=ny && ny<4) {
				dfs(moveCnt+1, nx, ny, num + map[nx][ny]);
			}
		}
	}

}
