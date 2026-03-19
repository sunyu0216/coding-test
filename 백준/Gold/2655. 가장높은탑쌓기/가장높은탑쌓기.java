import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 2655. 가장 높은 탑 쌓기
 * 
 * 1. 입력받기
 * 	1-1. 벽돌의 수 brickNum
 * 	1-2. 벽돌의 밑면, 높이, 무게 저장하기 bricks[][3]
 * 
 * 2. 백트래킹을 모든 경우의 수 확인.. 2**100이라 시간초과
 * 
 * 3. dp[i] = i번 벽돌이 맨 위에 있을때 쌓을 수 있는 탑의 최대 높이 / vistied[i] = i번 벽돌 아래에 몇번 벽돌이 있는지
 * 
 * 4. 벽돌들을 밑면 크기 기준으로 내림차순 정렬
 * 
 * 5. 전체 벽돌을 순회 idx
 * 	5-1. 현재 dp[idx]를 idx 벽돌 높이로 초기화
 * 	5-2. 이 idx 벽돌부터 다시 전체 벽돌 순회 nxt
 * 		5-2-1. idx무게가 nxt보다 가볍고 현재 dp값이 nxt돌+현재돌높이 보다 작은 경우에만 -> dp[idx] = dp[nxt] + idx 벽돌 높이
 * 		5-2-2. visited[idx] = nxt 기록 => idx 벽돌 아래에는 nxt 벽돌이 잇음
 * 		5-2-3. 이 벽돌이 최대 높이인지 검사
 * 			5-2-3-1. 최대 높이 갱신
 * 			5-2-3-2. 맨 위 벽돌 번호 갱신
 * 	
 * 6. 출력
 * 	6-1. visited를 따라가면서 큐에 순서대로 번호 넣어주기
 * 	6-2. 개수도 같이 증가
 * 	6-3. visited[i] = i이면 끝내기
 * 
 * 7. 그대로 출력
 *
 */
public class Main {
	static int brickNum;
	static int[][] bricks;
	static int totalHeight;
	static int[] dp;
	static int[] visited;
	static int topBrick;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		brickNum = Integer.parseInt(br.readLine());
		bricks = new int[brickNum+1][4];
		
		for(int b=1; b<brickNum+1; b++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			bricks[b][0] = b;
			bricks[b][1] = Integer.parseInt(st.nextToken());
			bricks[b][2] = Integer.parseInt(st.nextToken());
			bricks[b][3] = Integer.parseInt(st.nextToken());
		}// 입력 끝
		
		Arrays.sort(bricks, 1, brickNum + 1, (a, b) -> {
		    return Integer.compare(b[1], a[1]); // 밑면 내림차순
		});
		
		totalHeight = 0;
		dp = new int[brickNum+1];
		visited = new int[brickNum+1];
		topBrick = bricks[brickNum][0];
		
		// 5. 전체 벽돌을 순회 idx
		for(int idx = 1; idx<brickNum+1; idx++) {
			dp[idx] = bricks[idx][2];
			
			// idx 밑에 오는 벽돌을 찾는 것이므로 nxt는 당연히 idx 보다 밑면이 커야함. 즉 내림차순되어있으므로 idx보다 더 이전의 벽돌들만 참고하면 됨.
			for(int nxt = 1; nxt<idx; nxt++) {
				// 무게만 확인할 것.
				if (bricks[nxt][3] > bricks[idx][3] && dp[idx] < dp[nxt] + bricks[idx][2]) {
					dp[idx] = dp[nxt] + bricks[idx][2];
					visited[idx] = nxt;
				}
			}
			if(totalHeight < dp[idx]) {
				totalHeight = dp[idx];
				topBrick = idx;
			}
		}
		
		// 6. 출력
		Deque<Integer> answer = new ArrayDeque<>();
		answer.offer(topBrick);
		int i = topBrick;
		while(visited[i] != 0) {
			answer.offer(visited[i]);
			i = visited[i];
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(answer.size()).append("\n");
		
		// 큐에서 꺼낸 값은 '인덱스'이므로, 그 인덱스에 저장된 '원래 번호'를 출력!
		while (!answer.isEmpty()) {
		    int sortedIdx = answer.poll(); 
		    sb.append(bricks[sortedIdx][0]).append("\n");
		}
		
		System.out.println(sb);
	}

}
