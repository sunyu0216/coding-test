import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author seon-yu
 * BOJ 5719. 거의 최단 경로
 * 
 * 1. 입력받기
 * 	1-1. 장소의 수 placeNum, 도로의 수 roadNum
 * 	1-2. 시작점 start, 도착점 end
 * 	1-3. 도로의 정보 roads
 * 	1-4. 0 0이 들어오면 테케 끝내기
 * 
 * [흐름 정리]
 * 	일단 특정 시작점에서 도착점에 가는 경로를 구하는거니까.. 다익스트라
 * 	근데 다익스트라로 구하는건 최소거리.. 이때구한 최소거리를 제외한 다른 점들을 다 탐색?
 * 	도로가 좀 많아도 다익스트라로 최소 거리 구하는게 약 구만번이고 
 * 	최단 경로에 포함된 거리는 넣으면 안되는데 이때 한 도시에서 도시로 가는 도로는 딱 하나이기에 가지치기가 많이 되지 않을까?
 * 	
 * 	즉 다익스트라 돌리기 -> 다익스트라에 포함된 도로 정보 삭제 -> 다시 돌리
 * 
 * 2. 다익스트라로 최단거리 찾기
 * 	2-1. djk[] 만들고 INF로 초기화, 시작점만 0으로 초기화, 시작점 큐에 넣기
 * 	2-2. 큐가 빌때까지
 * 		2-2-1. 큐에서 꺼낸 값이 현재 거리보다 더 크다면 pass
 * 		2-2-2. 현재 값에서 인접한 도시들 확인
 * 			2-2-2-1. 인접한 도시 중 현재 거리+도시까지의 거리 < 도시까지의 거리 이면 갱
 * 			2-2-2-2. 큐에 넣
 * 	2-3. roads 보면서 시작점부터 갈 수 있는 도시들 거리 갱신
 * 
 * 
 */
public class Main {
	static int placeNum;
	static int roadNum;
	static int start;
	static int end;
	static List<City>[] roads; // 도로정보 저장하는 리스트 배열 
	static List<City>[] reverseRoads; // 리버스 도로 저장 배열 
	static boolean[][] isInvalid; // 지워진 도로 체크
	static int[] dist; // 시작점부터 끝점까지의 최소 거리를 갱신해주는 배열 
	
	static class City implements Comparable<City>{
		int to;
		int distance;
		
		public City(int to, int distance) {
			super();
			this.to = to;
			this.distance = distance;
		}
		
		@Override
		public int compareTo(City other) {
			return Integer.compare(this.distance, other.distance);
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			placeNum = Integer.parseInt(st.nextToken());
			roadNum = Integer.parseInt(st.nextToken());
			
			if(placeNum == 0 && roadNum == 0) {
				break;
			}
			
			st = new StringTokenizer(br.readLine());
			start = Integer.parseInt(st.nextToken());
			end = Integer.parseInt(st.nextToken());
			
			// 필요한 배열 초기
			roads = new ArrayList[placeNum];
			reverseRoads = new ArrayList[placeNum];
			isInvalid = new boolean[placeNum][placeNum];
			
			for(int i=0; i<placeNum; i++) {
				roads[i] = new ArrayList<>();
				reverseRoads[i] = new ArrayList<>();
			}
			
			dist = new int[placeNum];
			int INF = 987654321;
			Arrays.fill(dist, INF);
			
			for(int c=0; c<roadNum; c++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				
				roads[s].add(new City(e, cost));
				reverseRoads[e].add(new City(s, cost));
			} // 도로 입력 끝
			
			// 첫 다익스트라 돌리기
			Djk();
			
			// 최단거리 경로 삭제
			backTracking(end);
			
			// 다시 다익스트라 돌리기
			Arrays.fill(dist, INF);
			Djk();
			
			if(dist[end] == INF) {
				System.out.println(-1);
			}else {
				System.out.println(dist[end]);
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * @return
	 * 
	 * 항상 헷갈려 하는거지만 큐에 들어가는 City의 distance는 시작점부터 현재 도착지까지의 누적합이다. 
	 */
	public static void Djk() {
		PriorityQueue<City> q = new PriorityQueue<>();
		q.offer(new City(start, 0));
		dist[start] = 0;
		
		while(!q.isEmpty()) {
			City curr = q.poll();
			
			// 현재위치까지의 거리가 최단 거리인지 확인, 즉 현재 도착지까지의 누적합이 더 커버리면 얘는 볼 필요가 없으므로 pass
			if(dist[curr.to] < curr.distance) continue;
			
			// 현재 도착지까지의 누적합과 같다면(작을수는 없음), 이 도착지를 다시 시작점으로 해서 새 경로를 탐색해봐야겠지?
			for(City c: roads[curr.to]) {
				if(!isInvalid[curr.to][c.to]) { // 유효한 도로에 한해서 구하기 
					int cost = dist[curr.to] + c.distance; // 현재 도착지까지의 거리와 새 도착지까지의 거리를 cost로 잡고
					
					if(cost < dist[c.to]) { // 갱신이 안되어 있다면 
						dist[c.to] = cost;
						q.offer(new City(c.to, cost)); // 지금까지 누적된 거리 값을 넣어주기 
						
					}
				}
			}
		}
		return;
	}
	
	/**
	 * 백트래킹을 통해 도착지부터 시작점까지 거슬러올라가며 간선 지우기 
	 * 
	 * 1) 기저조건: destination이 시작점이 되면 멈추기
	 * 2) dist[prev] + roads[prev].get(?) = dist[destination] 이 되는 prev를 찾아야 함.
	 * 		어떻게..? -> 역방향 리스트를 하나 더 만들자..
	 * 3) 지울 도로는 어떻게 체크하지? isInvalid 배열 만들기 이차원!!
	 * 4) 다익스트라 한번 더돌리기 전에 지울도로 배열 참고해서 건너뛰기 
	 * 
	 */
	public static void backTracking(int destination) {
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(destination);
		boolean visited[] = new boolean[placeNum];
		visited[destination] = true;
		
		while(!q.isEmpty()) {
			int curr = q.poll();
			
			if(curr == start) continue; // 시작점에 도달하면 더 갈 곳 없음
			
			for(City c: reverseRoads[curr]) {
				int weight = c.distance;
				if((dist[c.to] + weight) == dist[curr]) {
					isInvalid[c.to][curr] = true;
					if(!visited[c.to]) {
						visited[c.to] = true;
						q.offer(c.to);
					}
				}
				
			}
		}
		return;
	}
}
