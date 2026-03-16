import java.io.*;
import java.util.*;
/**
 * 
 * @author seonyu
 * SWEA 1249. 보급로
 * 
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 지도의 크기 N
 * 	1-3. 지형 정보(복구 시간) int[][] map (문자열로 들어오므로 숫자로 변환)
 * 
 * 2. 최단 경로(최소 비용) 탐색 준비
 * 	2-1. 각 칸까지의 최소 복구 시간을 저장할 int[][] dist 배열 생성
 * 	2-2. dist 배열을 아주 큰 값(Integer.MAX_VALUE)으로 초기화
 * 	2-3. 우선순위 큐(PriorityQueue) 생성
 * 		2-3-1. 누적 복구 시간이 적은 순서대로 정렬되도록 설정 (다익스트라 핵심)
 * 
 * 
 * 3. 다익스트라(Dijkstra) 알고리즘 수행
 * 	3-1. 시작점(0, 0)의 복구 시간을 0으로 설정하고 큐에 삽입
 * 	3-2. 큐가 빌 때까지 반복 루프 수행
 * 		3-2-1. 현재 가장 적은 누적 시간을 가진 칸을 꺼냄
 * 		3-2-2. 이미 알고 있는 최소 시간(dist)보다 현재 꺼낸 시간이 크다면 무시(pass)
 * 		3-2-3. 상하좌우 4방향 탐색
 * 			3-2-3-1. 맵 범위를 벗어나지 않는지 확인
 * 			3-2-3-2. (현재 누적 시간 + 다음 칸의 복구 시간)이 기존의 dist[next]보다 작은지 확인
 * 			3-2-3-3. 더 작은 경로를 발견했다면 dist[next] 갱신 후 큐에 삽입
 * 
 * 4. 결과 출력
 * 	4-1. 목적지인 dist[N-1][N-1]에 저장된 최솟값을 출력
 * 
 */
public class Solution {
    static int N;
    static int[][] map;
    static int[][] dist;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            dist = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = line.charAt(j) - '0';
                    dist[i][j] = Integer.MAX_VALUE; // 최대값으로 초기화
                }
            }

            System.out.println("#" + t + " " + dijkstra());
        }
    }

    private static int dijkstra() {
        // [r, c, cost] 를 담고 cost 기준 오름차순 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        
        dist[0][0] = 0;
        pq.offer(new int[]{0, 0, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];

            // 이미 더 짧은 경로를 찾았다면 건너뛰기
            if (cost > dist[r][c]) continue;

            // 목적지 도달 시 즉시 반환 (Dijkstra의 특징)
            if (r == N - 1 && c == N - 1) return cost;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                    // 더 짧은 복구 시간을 찾은 경우 갱신
                    if (dist[nr][nc] > dist[r][c] + map[nr][nc]) {
                        dist[nr][nc] = dist[r][c] + map[nr][nc];
                        pq.offer(new int[]{nr, nc, dist[nr][nc]});
                    }
                }
            }
        }
        return -1;
    }
}