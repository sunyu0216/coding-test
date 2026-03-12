import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 17472. 다리 만들기2
 * 
 * 1. 필요한 것들
 * 	1-1. 섬의 개수 islandNum -> 
 * 		1-1-1. 1을 만나면 dfs를 통해 섬 덩어리를 구하고 해당 섬에 번호를 붙여 갱신
 * 
 * 	1-2. 각 섬을 잇는 다리의 길이 -> 
 * 		1-2-1. 행, 열을 쭉 순회하면서 섬이 나오면 
 * 			1-2-1-1. 그 섬 이후로 나온 바다의 개수를 세기
 * 			1-2-1-2. 그 방향으로 쭉 갔을때 섬이 나오고 바다의 개수가 2이상이면 저장
 * 			1-2-1-3. 섬이 안나오거나 맵 밖이면 pass
 * 
 * 2. 다리의 길이 순대로 다리 정보 정렬
 * 
 * 3. 최소 스패닝 트리 - 크루스칼로 모든 섬을 연결
 *
 */
public class Main {
	static int rowSize, colSize;
	static int[][] map;
	static int totalBridgeDist;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		map = new int[rowSize][colSize];
		
		for(int i=0; i<rowSize; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<colSize; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		} // 입력 끝
		countIsland();
		findBridge();
		
		Collections.sort(bList);
		
		makeSet();
		
		int bridgeCnt = 0;
		for(Bridge b: bList) {
			if(findParent(b.start) != findParent(b.end)) {
				union(b.start, b.end);
				bridgeCnt++;
				totalBridgeDist += b.dist;
			}
			
			if(bridgeCnt == islandNum-2-1) { // 실제 섬 개수는 -2 한 값
				System.out.println(totalBridgeDist);
				return;
			}
		}
		System.out.println(-1);
		
		
		
//		// 테스트문
//		// 1) 섬 번호 테스트
//		for (int i = 0; i < rowSize; i++) {
//	        for (int j = 0; j < colSize; j++) {
//	            System.out.print(map[i][j]);
//	        }
//	        System.out.println();
//	    }
//		// 2) 다리 정보 테스트
//		for(Bridge b: bList) {
//			System.out.println("출발섬: "+ b.start+" 끝 섬: "+ b.end+ " 거리: "+ b.dist);
//		}
	}
	
	
	// 1. 섬의 개수와 섬 번호 붙이는 함수
	static int islandNum;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	private static void countIsland() {
		boolean[][] visited = new boolean[rowSize][colSize];
		int islandIdx = 2; // 1은 원래 섬의 표시이므로, 구분하기 위해 2번부터 할당
		
		for (int i = 0; i < rowSize; i++) {
	        for (int j = 0; j < colSize; j++) {
	            // 아직 방문하지 않은 섬을 발견하면 dfs 호출
	            if (map[i][j] == 1 && !visited[i][j]) {
	                dfs(i, j, islandIdx, visited);
	                islandIdx++;
	            }
	        }
	    }
		islandNum = islandIdx;
		
	}
	// 1-1. dfs로 섬 덩어리 찾는 함수
	private static void dfs(int i, int j, int islandIdx, boolean[][] visited) {
		visited[i][j] = true;
		map[i][j] = islandIdx;
		
		for(int d=0; d<4; d++) {
			int nx = i+dx[d];
			int ny = j+dy[d];
			
			if((0<=nx && nx<rowSize && 0<=ny && ny<colSize) && !visited[nx][ny] && map[nx][ny] == 1) {
				dfs(nx, ny, islandIdx, visited);
			}
		}
	}
	
	
	static List<Bridge> bList;
	static class Bridge implements Comparable<Bridge>{
		int start;
		int end;
		int dist;
		public Bridge(int start, int end, int dist) {
			super();
			this.start = start;
			this.end = end;
			this.dist = dist;
		}
		@Override
		public int compareTo(Bridge b) {
			return Integer.compare(this.dist, b.dist);
		}
		
	}
	// 2. 섬끼리의 다리 정보를 구하는 함수
	private static void findBridge() {
		bList = new ArrayList<>();
		
		// 행, 열을 쭉 순회하면서 섬이 나오면 
		for(int i=0; i<rowSize; i++) {
			for(int j=0; j<colSize; j++) {
				if(map[i][j] != 0) {
					int startIsland = map[i][j];
					
					for(int d=0; d<4; d++) { // 4 방향으로 뻗어나가면서 다리를 탐색
						int nr = i;
	                    int nc = j;
	                    int dist = 0;
	                    
	                    while (true) {
	                        nr += dx[d];
	                        nc += dy[d];
	                        
	                        // 맵 밖으로 나가면 stop
	                        if (nr < 0 || nr >= rowSize || nc < 0 || nc >= colSize) break;
	                        
	                        // 다른 섬을 만났을 때
	                        if (map[nr][nc] > 1) {
	                            int endIsland = map[nr][nc];
	                            
	                            if (endIsland != startIsland) { // 출발했던 섬이랑 다른 섬이고
	                                if (dist >= 2) { // 길이가 2 이상이면 다리 조건 충족
	                                	bList.add(new Bridge(startIsland, endIsland, dist));
	                                }
	                            }
	                            break; // 어떤 섬이든 만났으면 이 방향 탐색은 종료
	                        } 
	                        
	                        // 바다를 만났을 때
	                        if (map[nr][nc] == 0) {
	                            dist++;
	                        }
	                    }
					}
				}
			}
		}
	}
	
	
	static int[] parent;
	// 3. 섬들의 부모 배열 초기화 작업
	private static void makeSet() {
		parent = new int[islandNum]; // 섬 번호가 2번 부터 시작하므로 
		for(int i=2; i<islandNum; i++) {
			parent[i] = i;
		}
	}
	
	// 4. 섬들의 루트 연결 확인 함수
	private static int findParent(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent[x]);
	}
	
	// 5. 섬들을 연결해주는 함수
	private static void union(int x, int y) {
		int rootX = findParent(x);
		int rootY = findParent(y);
		if(rootX != rootY) {
			parent[rootY] = rootX;
		}
	}

}
