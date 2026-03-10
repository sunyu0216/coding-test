import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.DoubleToLongFunction;

/**
 * 
 * @author seonyu
 * SWEA 3124. 최소 스패닝 트리
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 정점의 개수 nodeNum, 간선의 개수 edgeNum
 * 	1-3. 시작정점, 끝 정점, 간선 가중치
 * 
 * 2. 간선 정보 입력받을 Edge 클래스 정의
 * 	2-1. 시작 정점, 끝 정점, 간선 가중치(long)
 * 	2-2. 정렬 메서드 가중치 기준으로 재정의
 * 
 * 3. 서로소 집합 관련 함수들 정의
 * 	3-1. 초기화 함수 makeSet()
 * 	3-2. 부모 찾기 함수 findParent()
 * 	3-3. 합치기 함수(연결) union()
 * 
 * 4. 메인
 * 	4-1. 입력받은 간선 리스트 정렬
 * 	
 * 	4-2. 간선 리스트를 순회하며,
 * 		4-2-1. 연결되었는지 확인
 * 			4-2-1-1. 아니라면 연결, 최종 가중치 갱신, 연결된 간선 개수++
 * 			4-2-1-2. 연결되었다면 pass
 * 		4-2-2. 연결된 간선 개수가 정점의 개수 -1 개가 되면 stop
 *
 */
public class Solution {
	static int T;
	static int nodeNum;
	static int edgeNum;
	static List<Edge> edgeList;
	
	static int[] parent;
	
	static class Edge implements Comparable<Edge>{
		int start;
		int end;
		long weight;
		
		public Edge(int start, int end, long weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.weight, o.weight);
		}
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			nodeNum = Integer.parseInt(st.nextToken());
			edgeNum = Integer.parseInt(st.nextToken());
			
			edgeList = new ArrayList<>();
			
			for(int i=0; i<edgeNum; i++) {
				st = new StringTokenizer(br.readLine());
				
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				long weight = Long.parseLong(st.nextToken());
				
				edgeList.add(new Edge(start, end, weight));
			}
			
			// 4-1. 입력받은 간선 리스트 정렬
			Collections.sort(edgeList);
			
			makeSet();
			
			long result = 0;
			int count = 0;
			
			// 4-2. 간선 리스트를 순회하며,
			for(Edge edge: edgeList) {
				// 4-2-1. 연결되었는지 확인
				if(findParent(edge.start) != findParent(edge.end)) {
					// 4-2-1-1. 아니라면 연결, 최종 가중치 갱신, 연결된 간선 개수++ 
					union(edge.start, edge.end);
					result += edge.weight;
					count++;
				}else {
					// 4-2-1-2. 연결되었다면 pass
					continue;
				}
				
				if(count == nodeNum-1) {
					break;
				}
			}
			
			System.out.println("#"+t+" "+result);
			
		}
	}
	
	public static void makeSet() {
		parent = new int[nodeNum+1];
		for(int i=1; i<=nodeNum; i++) {
			parent[i] = i;
		}
	}
	
	public static int findParent(int x) {
		if(parent[x] == x) {
			return x;
		}else {
			return parent[x] = findParent(parent[x]);
		}
	}
	
	public static boolean union(int x, int y) {
		int rootX = findParent(x);
		int rootY = findParent(y);
		if(rootX == rootY) {
			return false;
		}else {
			parent[rootY] = rootX;
			return true;
		}
	}

}
