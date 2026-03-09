import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 15683. 감시
 * 
 * 1. 입력받기
 * 	1-1. 사무실 세로크기 rowSize, 가로크기 colSize를 입력받는다
 * 	1-2. 사무실 각 칸의 정보 office[][]
 * 		1-2-1. CCTV가 있다면 CCTV의 각 좌표, 번호, 방향을 cctv[][4]에 저장
 * 
 * 2. 시뮬레이션 함수
 * 	2-1. cctv[][] 배열을 순회하며,
 * 		2-1-1. 방향을 하나씩 바꾸기
 * 		2-1-2. 바꾼 방향에 따라 사각지대의 크기 구하기
 * 		2-1-3. 최소 사각지대 크기와 비교 후 갱신
 *
 */
public class Main {
	static int rowSize;
	static int colSize;
	static int[][] office;
	static int[][] cctves = new int[8][4];
	static int cctvCnt;
	static int minBlindSpot;
	
	// 0:상, 1:우, 2:하, 3:좌 (시계 방향)
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		office = new int[rowSize][colSize];
		
		cctvCnt = -1;
		for(int r=0; r<rowSize; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c=0; c<colSize; c++) {
				int value = Integer.parseInt(st.nextToken());
				
				if(value == 0 || value == 6) {
					office[r][c] = value;
				}else {
					cctvCnt++;
					cctves[cctvCnt][0] = value;
					cctves[cctvCnt][1] = 0; // 초기 방향은 0
					cctves[cctvCnt][2] = r;
					cctves[cctvCnt][3] = c;
					office[r][c] = value;
				}
			}
		}
		
		// 1. 최소 사각지대 초기값을 아주 큰 값으로 설정
	    minBlindSpot = Integer.MAX_VALUE;
	    
	    // 2. 방향 결정 재귀함수 시작
	    direction(0);
	    
	    // 3. 최종 결과 출력
	    System.out.println(minBlindSpot);
	}
	
	/**
	 * 
	 * @param idx
	 * 씨씨티비 방향을 결정해주는 재귀함수
	 * 
	 * 1. 기저조건: 모든 씨씨티비의 방향을 다 결정했을 때
	 * 	1-1. 방향이 결정되었으므로 씨씨티비 설치 함수 호출
	 * 
	 * 2. 방향 4개를 돌아가며 방향 결정해주기
	 * 
	 */
	public static void direction(int idx) {
		// 3. 마지막 씨씨티비가 결정되었으므로 시뮬레이션 함수 호출
		if(idx == cctvCnt+1) {
			installCCTV();
			return;
		}
		
		for(int dir=0; dir<4; dir++) {
			// 1. 방향 결정 (기록)
			cctves[idx][1] = dir;
			// 2. 다음 재귀함수 호출
			direction(idx+1);
		}
	}
	
	/**
	 * 씨씨티비 방향대로 설치하는 함수
	 * 
	 * 0. office 배열 복사
	 * 
	 * 1. 씨씨티비 배열을 순회하며,
	 * 	1-1. 방향을 확인 후 벽을 만나기 전까지 #으로 채우기
	 * 
	 * 2. 사각지대 구하는 함수 호출
	 * 
	 */
	public static void installCCTV() {
		
		int[][] copy = new int[rowSize][colSize];

		for (int i = 0; i < rowSize; i++) {
		    copy[i] = office[i].clone();
		}
		
		for(int idx=0; idx<=cctvCnt; idx++) {
			int[] cctv = cctves[idx];
			installOneCCTV(copy, cctv[0], cctv[2], cctv[3], cctv[1]);
		}
		
		// 시뮬레이션 끝났으니 사각지대 계산
	    countBlindSpot(copy);
	}
	
	/**
	 * 
	 * @param copy
	 * @param type
	 * @param r
	 * @param c
	 * @param dir
	 * 
	 * 씨씨티비 한대의 방향대로 설치해주는 함수
	 */
	public static void installOneCCTV(int[][] copy, int type, int r, int c, int dir) {
	    if (type == 1) {
	        watch(r, c, dir, copy); // 한 방향
	    } else if (type == 2) {
	        watch(r, c, dir, copy); // 기준 방향
	        watch(r, c, (dir + 2) % 4, copy); // 반대 방향
	    } else if (type == 3) {
	        watch(r, c, dir, copy); // 기준 방향 (상)
	        watch(r, c, (dir + 1) % 4, copy); // 90도 방향 (우)
	    } else if (type == 4) {
	        watch(r, c, dir, copy);
	        watch(r, c, (dir + 1) % 4, copy);
	        watch(r, c, (dir + 2) % 4, copy); // 3방향
	    } else if (type == 5) {
	        for (int i = 0; i < 4; i++) watch(r, c, i, copy); // 4방향 전체
	    }
	}
	
	/**
	 * 
	 * @param r
	 * @param c
	 * @param dir
	 * @param map
	 * 
	 * 벽을 만나기 전까지 계속 방향대로 쭉 직진하는 함수
	 */
	public static void watch(int r, int c, int dir, int[][] map) {
	    int nr = r + dr[dir];
	    int nc = c + dc[dir];

	    while (nr >= 0 && nr < rowSize && nc >= 0 && nc < colSize) {
	        if (map[nr][nc] == 6) break; // 벽을 만나면 중단
	        
	        if (map[nr][nc] == 0) { // 빈칸일 때만 감시 구역(#) 표시 -> 코드상에서 #을 -1로 표현
	            map[nr][nc] = -1;
	        }
	        
	        nr += dr[dir];
	        nc += dc[dir];
	    }
	}
	
	public static void countBlindSpot(int[][] map) {
	    int cnt = 0;
	    for(int i=0; i<rowSize; i++) {
	        for(int j=0; j<colSize; j++) {
	            if(map[i][j] == 0) cnt++;
	        }
	    }
	    minBlindSpot = Math.min(minBlindSpot, cnt);
	}
}
