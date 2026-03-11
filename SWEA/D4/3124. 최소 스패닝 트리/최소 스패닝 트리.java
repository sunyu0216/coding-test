import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/**
 * 
 * @author seonyu
 * SWEA 3124-2(프림). 최소 스패닝 트리
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 정점의 개수 nodeNum, 간선의 개수 edgeNum
 * 	1-3. 시작정점, 끝 정점, 간선 가중치
 * 	1-4. 정점을 연결하는 가중치 원본을 저장 adjList
 * 	1-5. 최저가를 계속 업데이트 해주는 minCost[]
 * 	1-6. 가장 낮은 가중치의 섬을 바로 알려주는 PQ<Node>
 * 
 * 2. 가중치 정보 입력받을 Node 클래스
 * 	2-1. 정점 번호, 가중치
 * 	2-2. 정렬 메서드를 가중치 기준으로 오름차순
 * 
 * 3. 우선순위 큐 생성하고 1번 정점부터 넣어주기
 * 
 * 4. 우선순위 큐가 빌 때까지
 * 	4-1. 큐 값을 꺼내 해당 정점 방문처리, 전체 가중치에 현재 정점 가중치 더해주기
 * 	4-2. 해당 정점을 기준으로 아직 방문하지 않은 정점을 순회하며,
 * 		4-2-1. map이 minCost보다 더 작다면 minCost값 갱신
 *
 */
public class Solution {

	static int T, nodeNum, edgeNum;
    static List<Node>[] adjList; // 가중치 원본 저장
    static long[] minCost;       // 가중치가 크므로 long 배열
    static boolean[] visited;

    // 2. 가중치 정보 입력받을 Node 클래스
    static class Node implements Comparable<Node> {
        int id;
        long weight;

        public Node(int id, long weight) {
            this.id = id;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            nodeNum = Integer.parseInt(st.nextToken());
            edgeNum = Integer.parseInt(st.nextToken());

            // 초기화
            adjList = new ArrayList[nodeNum + 1];
            for (int i = 1; i <= nodeNum; i++) adjList[i] = new ArrayList<>();
            
            minCost = new long[nodeNum + 1];
            Arrays.fill(minCost, Long.MAX_VALUE);
            visited = new boolean[nodeNum + 1];

            // 간선 입력 (인접 리스트 채우기)
            for (int i = 0; i < edgeNum; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                long w = Long.parseLong(st.nextToken());
                
                adjList[s].add(new Node(e, w));
                adjList[e].add(new Node(s, w));
            }

            // 3. 프림 알고리즘 실행
            long result = solve();
            System.out.println("#" + t + " " + result);
        }
    }

    static long solve() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        long totalWeight = 0;
        int count = 0;

        // 3. 우선순위 큐 생성하고 1번 정점부터 넣어주기
        minCost[1] = 0;
        pq.offer(new Node(1, 0)); // 1번 정점, 가중치 0

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 이미 방문한 정점이면 패스
            if (visited[current.id]) continue;

            visited[current.id] = true;
            totalWeight += current.weight;
            
            // 모든 정점을 다 연결했다면 종료
            if (++count == nodeNum) break;

            // 현재 정점의 이웃들 확인
            for (Node next : adjList[current.id]) {
                if (!visited[next.id] && minCost[next.id] > next.weight) {
                    minCost[next.id] = next.weight;
                    pq.offer(new Node(next.id, minCost[next.id]));
                }
            }
        }
        return totalWeight;
    }
	
}
