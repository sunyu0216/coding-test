import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 13913. 숨바꼭질 4
 * 
 * 1. 수빈이의 위치 subinPosition, 동생의 위치 sisterPosition
 * 
 * 2. BFS
 * 	2-1. 수빈이의 위치를 큐에 넣기 & time[수빈이의 위치] = 0 넣어주기, 이때 time[idx]는 수빈이의 위치에서 현재 idx번 점까지의 최소 이동횟수를 나타냄
 * 	2-2. 큐를 순회하며
 * 		2-2-1. 현재 위치가 동생의 위치이면 break
 * 		2-2-2. 세가지 이동을 반복하며
 * 			2-2-2-1. 다음 이동한 위치가 범위에서 벗어나지 않고 time[다음이동위치] = -1일 때(아직 방문하지 않았다면)
 * 				2-2-2-1-1. time[다음이동위치] = time[현재 위치] + 1
 * 				2-2-2-1-2. parent[다음이동위치] = 현재 위치 -> 경로 출력할 저장용
 * 				2-2-2-1-3. 큐에 다음 이동 위치 추가
 *  	
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int subinPosition = Integer.parseInt(st.nextToken());
		int sisterPosition = Integer.parseInt(st.nextToken());
		
		// 1. 필요한 배열 (최대 범위 100,000 기준)
		int[] time = new int[100001];
		int[] parent = new int[100001];
		Arrays.fill(time, -1); // 방문하지 않은 곳은 -1

		// 2. BFS 시작
		Queue<Integer> q = new LinkedList<>();
		q.add(subinPosition);
		time[subinPosition] = 0;

		while(!q.isEmpty()) {
		    int curr = q.poll();
		    if(curr == sisterPosition) break;

		    // 3. 세 가지 이동 시도 (curr-1, curr+1, curr*2)
		    int[] nextPositions = {curr - 1, curr + 1, curr * 2};
		    for(int next : nextPositions) {
		        if(next >= 0 && next <= 100000 && time[next] == -1) {
		            time[next] = time[curr] + 1;
		            parent[next] = curr; // "next는 curr에서 왔어!"라고 기록
		            q.add(next);
		        }
		    }
		}
		
		Stack<Integer> stack = new Stack<>();
		int temp = sisterPosition;
		while(temp != subinPosition) {
		    stack.push(temp);
		    temp = parent[temp]; // 직전 위치로 이동
		}
		stack.push(subinPosition);

		// 동생까지 가는데 걸리는 이동횟수 출력하기
		System.out.println(time[sisterPosition]);
		
		// 스택에서 하나씩 꺼내 출력하기
		while(!stack.isEmpty()) {
		    System.out.print(stack.pop() + " ");
		}
		
	}

}
