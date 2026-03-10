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
 * SWEA 1251. [S/W 문제해결 응용] 4일차 - 하나로
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 섬의 개수 islandNum
 * 	1-3. 섬들의 좌표
 * 	1-4. 해저터널의 환경 부담 세율 실수 environmentTax
 * 
 * 2. 해저터널의 정보를 입력받을 클래스 
 * 	2-1. 시작 섬, 끝 섬, 해저터널 비용(가중치)
 * 	2-2. 가중치를 기준으로 오름차순 정렬
 * 
 * 3. UnionFind를 위한 함수들
 * 	3-1. 가장 대장을 찾아주는 함수
 * 	3-2. 두 집합을 합치는 함수
 * 
 *
 * 4. 메인 로직
 * 	4-1. 섬의 좌표들을 다 입력받은 후 이중반복문으로 반복하며,
 * 		4-1-1. 각 섬 사이의 거리를 계산해 
 * 		4-1-2. 터널 클래스 리스트에 간선으로 추가해주기
 * 
 * 	4-2. 터널 리스트 정렬(가중치 작은 순으로)
 * 
 *  4-3. Union Find 함수 초기화
 *  
 *  4-4. 정렬된 터널을 순회하며,
 *  	4-4-1. 각 간선을 잇는 섬들이 합쳐졌는지 체크
 *  		4-4-1-1. 합쳐졌다면 pass
 *  		4-4-1-2. 안합쳐졌다면 합치고, 전체 가중치 갱신, 전체 연결된 터널 개수++
 *  	4-4-2. 만약 전체 연결된 터널 개수가 섬개수-1이 된다면 stop
 *  
 */
public class Solution {
	static int T;
	static int islandNum;
	static List<Tunnel> underSeaList;
	static int environmentTax;
	static int[] parent;
	
	static class Tunnel implements Comparable<Tunnel>{
		int start; // 시작 섬 번호
		int end; // 끝 섬 번호
		double weight;
		
		public Tunnel(int start, int end, double weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}

		@Override
		public int compareTo(Tunnel o) {
			return Double.compare(this.weight, o.weight);
		}
	}
	
	// 메인 로직
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			islandNum = Integer.parseInt(br.readLine());
			underSeaList = new ArrayList<>();
			
			// 좌표들 정보
			int[][] points = new int[islandNum][2];
			
			// 1. X 좌표들 먼저 입력받기
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<islandNum; i++) {
			    points[i][0] = Integer.parseInt(st.nextToken());
			}

			// 2. Y 좌표들 그다음 줄에서 입력받기
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<islandNum; i++) {
			    points[i][1] = Integer.parseInt(st.nextToken());
			}
			
			// 3. 환경 부담 세율 E 입력받기 (double)
			double E = Double.parseDouble(br.readLine());
			
			// 4-1. 섬의 좌표들을 다 입력받은 후 이중반복문으로 반복하며,
			for (int i = 0; i < islandNum; i++) {
			    for (int j = i + 1; j < islandNum; j++) {
			    	
			    	long dx = points[i][0] - points[j][0];
			        long dy = points[i][1] - points[j][1];
			        long distSquare = dx * dx + dy * dy;
			        
			        // 비용 = E * L^2
			        double cost = E * distSquare;
			        
			        // i번 섬과 j번 섬을 잇는 비용 tunnelCost 추가
			        underSeaList.add(new Tunnel(i, j, cost));
			    }
			}
			
			// 4-2. 터널 리스트 정렬(가중치 작은 순으로)
			Collections.sort(underSeaList);
			
			// 4-3. Union Find 함수 초기화
			makeSet();
			
			double totalWeight = 0;
			int count = 0;
			
			// 4-4. 정렬된 터널을 순회하며,
			for(Tunnel tunnel: underSeaList) {
				// 4-4-1. 각 간선을 잇는 섬들이 합쳐졌는지 체크
				int startIsland = tunnel.start;
				int endIsland = tunnel.end;
				
				if(findParent(startIsland) == findParent(endIsland)) {
					// 4-4-1-1. 합쳐졌다면 pass
					continue;
				}else {
					// 4-4-1-2. 안합쳐졌다면 합치고, 전체 가중치 갱신, 전체 연결된 터널 개수++
					union(startIsland, endIsland);
					totalWeight += tunnel.weight;
					count++;
				}
				
				// 4-4-2. 만약 전체 연결된 터널 개수가 섬개수-1이 된다면 stop
				if(count == islandNum-1) {
					break;
				}
			}
			
			long result = Math.round(totalWeight);
			
			System.out.println("#"+t+" "+result);
		}
	}
	
	public static void makeSet() {
		parent = new int[islandNum];
		for(int i=0; i<islandNum; i++) {
			parent[i]=i;
		}
	}
	
	public static int findParent(int a) {
		if(parent[a] == a) {
			return a;
		}else {
			return parent[a] = findParent(parent[a]);
		}
	}
	
	public static boolean union(int a, int b) {
		int rootA = findParent(a);
		int rootB = findParent(b);
		
		if(rootA == rootB) {
			return false;
		}else {
			parent[rootB] = rootA;
			return true;
		}
	}

}
