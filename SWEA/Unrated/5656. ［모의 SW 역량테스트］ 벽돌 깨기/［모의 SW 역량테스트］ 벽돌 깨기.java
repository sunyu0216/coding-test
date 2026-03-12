import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5656. 벽돌깨기
 * 
 * 1. 변수들
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 떨어드릴 구슬의 개수 beadNum, 너비 width, 높이 height
 * 	1-3. 현재 벽돌 상태를 저장할 map[][]
 * 	1-4. 남은 벽돌 최소 개수 totalMinBrick
 * 
 * 2. 벽돌 폭발 함수
 * 	2-1. 큐에서 구슬이 때린 벽돌의 위치를 꺼내,
 * 		2-1-1. 해당 위치 벽돌의 폭발 범위 저장
 * 		2-1-2. 해당 벽돌 폭파시키기 (실제 map에서 사라지는건 여기서)
 * 
 * 	2-2. 해당 벽돌의 폭발 범위 내의 모든 칸을 순회
 * 		2-2-1. 맵을 벗어나지 않고, 해당 칸이 벽돌이며, 아직 방문한 적이 없다면,
 * 			2-2-1-1. 해당 칸을 큐에 넣고
 * 			2-2-1-2. 방문 처리 (곧 없어질 칸이므로 중복 방문 막기)
 * 
 * 3. 중력 작동 함수
 * 	3-1. 열 기준으로 map을 순회하며,
 * 		3-1-1. 벽돌을 만나면 cnt++, 해당 벽돌 List에 추가, 해당 벽돌 없애기
 * 	3-2. map의 해당 열 바닥부터 벽돌 개수만큼 다시 설치
 * 
 * 4. 남은 벽돌 세기 함수
 * 	4-1. 행과 열을 순회하며 남겨진 벽돌의 개수를 반환
 * 
 * 5. 구슬을 떨어뜨릴 곳을 정하는 재귀함수
 *  5-0. 남은 블록 개수가 0이라면 그 즉시 종료
 *  
 * 	5-1. 기저조건: 정해진 구슬의 개수를 다 썼을 때
 * 		5-1-1. 남은 벽돌 세기 함수 호출
 * 		5-1-2. 현재 최소 벽돌 개수와 비교 후 갱신
 * 
 * 	5-2. 각 열마다 벽돌이 있는지 탐색
 * 		5-2-1. 가지치기: 열의 끝까지 벽돌이 없다면 열 건너뛰기
 * 		5-2-2. 있다면
 * 			5-2-2-1. 현재 맵 복사 후 벽돌 폭발 함수, 중력 작동 함수 호출
 * 			
 *
 *
 * 6. 메인 로직
 * 	6-1. 구슬 떨어뜨릴 곳을 모두 탐색하는 함수 호출
 * 
 */
public class Solution {
	static int T;
	static int beadNum, width, height;
	static int[][] map;
	static int totalMinBrick;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			beadNum = Integer.parseInt(st.nextToken());
			width = Integer.parseInt(st.nextToken());
			height = Integer.parseInt(st.nextToken());
			
			map = new int[height][width];
			
			for(int i=0; i<height; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<width; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}// 입력 끝
			
			totalMinBrick = Integer.MAX_VALUE;
			findDropPosition(map, beadNum);
			
			System.out.println("#"+t+" "+totalMinBrick);
		}
	}
	
	static Queue<int[]> q;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	// 2. 벽돌 폭발 함수
	public static void bomb(int[][] currentMap, int x, int y) {
		int bombRange = 0;
		boolean[][] visited = new boolean[height][width];
		
		q = new ArrayDeque<>();
		q.offer(new int[] {x, y});
		visited[x][y] = true;
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			int cx = curr[0];
			int cy = curr[1];
			
			bombRange = currentMap[cx][cy]-1;
			currentMap[cx][cy] = 0;
			
			for(int d=0; d<4; d++) {
				for(int r=1; r<=bombRange; r++) {
					int nx = cx + dx[d]*r;
					int ny = cy + dy[d]*r;
					
					if((nx>=0 && nx<height && ny>=0 && ny<width) && currentMap[nx][ny]!=0 && !visited[nx][ny]) {
						q.offer(new int[] {nx, ny});
						visited[nx][ny] = true;
					}
				}
			}
			
		}
	}
	
	// 3. 중력 작동 함수
	private static void gravity(int[][] currentMap) {
		List<Integer> bombRangeList;
		
		// 3-1. 열 기준으로 map을 순회하며,
		for(int j=0; j<width; j++) {
			bombRangeList = new ArrayList<>();
			for(int i=0; i<height; i++) {
				// 3-1-1. 벽돌을 만나면 해당 벽돌 List에 추가, 해당 벽돌 없애기
				if(currentMap[i][j] != 0) {
					bombRangeList.add(currentMap[i][j]);
					currentMap[i][j] = 0;
				}
			}
			
			// 3-2. map의 해당 열 바닥부터 벽돌 개수만큼 다시 설치
			int setIdx = height-bombRangeList.size();
			for(int bombRange: bombRangeList) {
				currentMap[setIdx][j] = bombRange;
				setIdx++;
			}
		}
	}
	
	// 4. 남은 벽돌 세기 함수
	private static int countRemainBrick(int[][] currentMap) {
		int curBrickNum = 0;
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(currentMap[i][j] != 0) {
					curBrickNum++;
				}
			}
		}
		return curBrickNum;
	}
	
	
	// 5. 구슬을 떨어뜨릴 곳을 정하는 재귀함수
	private static void findDropPosition(int[][] currentMap, int remainBead) {
		// 5-0. 남은 블록 개수가 0이라면 그 즉시 종료
		if (countRemainBrick(currentMap) == 0) {
		    totalMinBrick = 0;
		    return;
		}
		
		// 5-1. 기저조건: 정해진 구슬의 개수를 다 썼을 때
		if(remainBead == 0) {
			int currentCnt = countRemainBrick(currentMap);
			totalMinBrick = Math.min(totalMinBrick, currentCnt);
			return;
		}
		
		// 5-2. 각 열마다 벽돌을 만나면 
		for(int j=0; j<width; j++) {
			int i=0;
			while(i<height && currentMap[i][j] == 0) i++; // 벽돌이 최초로 있는 높이 찾기
			
			while (i < height && currentMap[i][j] == 0) i++;

		    // 5-2-1. 가지치기: 열의 끝까지 벽돌이 없다면 열 건너뛰기
		    if (i == height) continue; 

		    
		    // 5-2-1. 현재 맵 복사
			int[][] nextMap = new int[height][width];
			for(int k=0; k<height; k++) {
				nextMap[k] = currentMap[k].clone();
			}
			
		    bomb(nextMap, i, j);
		    gravity(nextMap);
		    findDropPosition(nextMap, remainBead - 1);
			
		}
	}

}
