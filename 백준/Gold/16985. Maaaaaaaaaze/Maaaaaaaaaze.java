import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 16985. Maaaaaaaaaze
 * 
 * 4**5 * 5! = 대략 24만번의 경우의 수가 있음(통과가 안되는 둥 가지치기 하면 더 줄겠지?)
 * -> 이 24만번 * BFS로 출구까지 길찾기(125)
 * 
 * 1. 입력받기
 * 	1-1. maze1[][],maze2[][],,, 에 일단 판 기준 미로를 담기
 * 
 * 2. 판 미로를 회전시키는 함수: 판 하나에 대해 0, 90, 180, 270도 회전 미리 만들기
 * 	2-1. 회전시키는 횟수만큼
 * 		2-1-1. 열 반복문
 * 			2-1-1-1. 행 반복문을 거꾸로 돌리기
 * 				2-1-1-1-1. 기존 미로 값에서 현재 값을 가져와 -> 0,0 부터 시작하는 새 미로 배열에 입력하기
 * 
 * 
 * 3. 각 판의 순서를 정하는 함수(순열) = 5!
 * 	3-1. 기저조건: 5개의 판을 다 쌓았다면
 * 		3-1-1. 각 판의 회전 상태를 정하기
 * 	3-2. 5번의 반복문을 돌며,
 * 		3-2-1. 아직 쌓지 않은 판이 있다면 -> 방문처리 해주고, stackMaze[현재 층] = 현재 판
 *  	3-2-2. 재귀호출
 *  	3-2-3. 방문처리 풀어주기
 *  
 *  4. 각 판의 상태를 정하는 함수(중복순열) = 4**5
 *  	4-1. 기저조건: 5개의 판의 상태를 다 정했다면,
 *  		4-1-1. 이때 만약 (0, 0, 0) / (0, 5, 0) / (0, 0, 5) / (0, 5, 5) / (5, 0, 0) / (5, 5, 0) / (0, 5, 5) / (5, 5, 5) 반복문
 *  			4-1-1-1. 입구 칸이 뚫려있고, 이에 해당하는 출구 칸도 다 뚫려있다면 BFS 호출
 *  			4-1-1-2. 막힌 입구 칸이나, 출구칸이 막혔다면 pass
 *  	4-2. 5번의 반복문을 돌며
 *  		4-2-1. 현재 층을 r번 돌리기
 *  		4-2-2. 바로 재귀 호출
 *  
 *  5. BFS(stackMaze, 시작 좌표, 끝 좌표)
 *  	5-1. q에 시작좌표, 끝 좌표 넣고 방문처리
 *  	5-2. while q
 *  		5-2-0. 큐에서 꺼낸 값이 출구 좌표라면 거리 반환
 *  		5-2-1. 큐에서 꺼낸 값에 위, 아래, 앞, 뒤, 오, 왼  -> 6 방향으로 이동
 *  			5-2-1-1. 이동한 위치가 미로를 안벗어나고, 방문 전이고, 벽(0)이 아니라면 큐에 넣기, 방문처리
 *  
 */
public class Main {
	static int[][][] mazeFix; // 수정되면 안되는 원본 미로 값
	static int[][][][] preRotated; // 원본 미로 값에 대해 미리 회전시켜 놓은 미로 상태들
	static int[][][] mazeRotated; // 매번 회전시키고 새로 쌓은 미로 값
	static int[] maze3D;
	static boolean[] visited;
	static int totalAnswer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		mazeFix = new int[5][5][5]; // 수정되면 안되는 원본 미로 값
		preRotated = new int[5][4][5][5]; // 미로 인덱스, 회전 수, 행수, 열수
		
		for(int i=0; i<5; i++) {
			for(int s=0; s<5; s++) {
				st = new StringTokenizer(br.readLine());
				for(int e=0; e<5; e++) {
					mazeFix[i][s][e] = Integer.parseInt(st.nextToken());
				}
			}
			// 입력 받은 직후 해당 판의 4가지 회전 상태를 미리 계산
            makePreRotated(i);
		}// 판 미로들 입력받기 끝
		
		
		mazeRotated = new int[5][5][5]; // 회전시키고 쌓은 미로의 모습
		maze3D = new int[5]; // idx 층에 몇번째 판미로가 있는지
		
		visited = new boolean[5]; // 미로 쌓을 때 방문처리 용
		totalAnswer = Integer.MAX_VALUE;
		stackMaze(0);
		
		if(totalAnswer == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(totalAnswer);
		}
		
		
	
	}
	
	// 2. 판 미로를 회전시키는 함수: 판 하나에 대해 0, 90, 180, 270도 회전 미리 만들기
    public static void makePreRotated(int mazeIdx) {
        // 0도 (원본)
        for (int i = 0; i < 5; i++) preRotated[mazeIdx][0][i] = mazeFix[mazeIdx][i].clone();
        
        // 90, 180, 270도 순차적 계산
        for (int r = 1; r < 4; r++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    preRotated[mazeIdx][r][j][4 - i] = preRotated[mazeIdx][r - 1][i][j];
                }
            }
        }
    }
	

	
	// 3. 각 판의 순서를 정하는 함수(순열) = 5!
	public static void stackMaze(int depth) {
		if(depth == 5) {
			rotateLayer(0);
			return;
		}
		
		for(int i=0; i<5; i++) {
			if(!visited[i]) {
				visited[i] = true;
				maze3D[depth] = i;
				stackMaze(depth+1);
				visited[i] = false;
			}
		}
	}

	// 4. 각 판의 상태를 정하는 함수(중복순열) = 4**5
	public static void rotateLayer(int depth) {
		if(depth == 5) {
			// 다 체크를 안해도 언젠간.. 돌아가면서 됨
			// int[][] starts = {{0, 0, 0}, {0, 4, 0}, {0, 0, 4}, {0, 4, 4}, {4, 0, 0}, {4, 4, 0}, {0, 4, 4}, {4, 4, 4}};
			
			// [수정 포인트 1] (0,0,0)과 (4,4,4)만 체크해도 모든 층을 다 회전시켜보고 모든 적재 경우를 다 보니까 ㄱㅊ음
	        if (mazeRotated[0][0][0] == 1 && mazeRotated[4][4][4] == 1) {
	            int currAnswer = BFS(new int[]{0, 0, 0}, new int[]{4, 4, 4});
	            
	            if (currAnswer != -1) {
	                totalAnswer = Math.min(totalAnswer, currAnswer);
	                
	                // 최단 거리 12를 찾았다면 바로 리턴
	                if (currAnswer == 12) {
	                    totalAnswer = 12;
	                    return;
	                }
	            }
	        }
	        return;
		}
		
		for(int rotateCnt=0; rotateCnt<4; rotateCnt++) { // 회전 횟수 
			mazeRotated[depth] = preRotated[maze3D[depth]][rotateCnt]; // 미리 만들어둔 rotateCnt 만큼 회전한 판 사용
			rotateLayer(depth+1);
		}
	}
	
	// 5. BFS(stackMaze, 시작 좌표, 끝 좌표)
	static int[] dx = {-1, 1, 0, 0, 0, 0}; // 위, 아래, 앞, 뒤, 오, 왼
	static int[] dy = {0, 0, 0, 0, -1, 1};
	static int[] dz = {0, 0, -1, 1, 0, 0};
	
	public static int BFS(int[] start, int[] end) {
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][][] visited = new boolean[5][5][5];
		
		q.offer(new int[] {start[0], start[1], start[2], 0});
		visited[start[0]][start[1]][start[2]] = true;
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			
			if(curr[0] == end[0] && curr[1] == end[1] && curr[2] == end[2]) {
				return curr[3];
			}
			
			for(int d=0; d<6; d++) {
				int nx = curr[0] + dx[d];
				int ny = curr[1] + dy[d];
				int nz = curr[2] + dz[d];
				
				if((0<=nx && nx<5 && 0<=ny && ny<5 && 0<=nz && nz<5) && mazeRotated[nx][ny][nz] == 1 && !visited[nx][ny][nz]) {
					visited[nx][ny][nz] = true;
					q.offer(new int[] {nx, ny, nz, curr[3]+1});
				}
			}
		}
		return -1;
	}
}
