import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 6497. 전력난
 * 
 * 1. 입력받기
 * 	1-1. 집의 수 homeNum, 길의 수 roadNum
 * 	1-2. 각 길에 대한 정보 받기
 * 	1-3. 각 길의 루트를 저장하는 배열 parent[]
 * 
 * 2. 길에 대한 정보를 받기 위한 클래스 Road
 * 	2-1. 시작 집, 끝 집, 길의 거리
 * 	2-2. 길의 비용을 누적 저장
 * 
 * 3. 길의 거리 기준으로 클래스 리스트를 오름차순 정렬하기
 * 
 * 4. 길 리스트를 순회하며,
 * 	4-1. 해당 길의 시작 집과 끝 집이 이미 연결되었다면 PASS
 * 	4-2. 아직 연결되지 않았다면 연결, 그리고 해당 길의 비용 누적
 * 
 * 5. 총 길의 비용에서 연결된 길의 비용만 빼주기
 */
public class Main {
	static int homeNum;
	static int roadNum;
	static int parent[];
	static List<Road> roadList;
	static int totalCost;
	static int sumCost;
	
	static class Road implements Comparable<Road>{
		int start;
		int end;
		int cost;
		
		public Road(int start, int end, int cost) {
			super();
			this.start = start;
			this.end = end;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Road r) {
			return Integer.compare(this.cost, r.cost);
		}
	}
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			homeNum = Integer.parseInt(st.nextToken());
			roadNum = Integer.parseInt(st.nextToken());
			
			if(homeNum == 0 && roadNum == 0) {
				break;
			}
			
			// 초기화
			parent = new int[homeNum];
			for(int h=0; h<homeNum; h++) {
				parent[h] = h;
			}
			roadList = new ArrayList<>();
			totalCost = 0;
			sumCost = 0;
			
			for(int r=0; r<roadNum; r++) {
				st = new StringTokenizer(br.readLine());
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				
				roadList.add(new Road(start, end, cost));
				totalCost += cost;
			}
			
			Collections.sort(roadList);
			
			int count = 0;
			for(Road r: roadList) {
				if(findParent(r.start) == findParent(r.end)) {
					continue;
				}else {
					union(r.start, r.end);
					sumCost += r.cost;
					count++;
				}
				
				if(count == homeNum-1) {
					break;
				}
			}
			
			int answer = totalCost - sumCost;
			System.out.println(answer);
		}
		
	}
	
	public static int findParent(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent[x]);
	}
	
	public static void union(int x, int y) {
		int rootX = findParent(x);
		int rootY = findParent(y);
		
		if(rootX != rootY) {
			parent[rootY] = rootX;
		}
	}
}
