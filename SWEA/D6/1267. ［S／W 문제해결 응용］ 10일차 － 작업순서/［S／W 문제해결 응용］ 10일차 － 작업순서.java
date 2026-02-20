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
 * SWEA 1267. 작업순서
 * 
 * 1. 입력받기
 * 	1-1. 정점의 개수 nodeNum, 간선의 개수 edgeNum
 * 	1-2. 간선의 정보 List<Integer>[] nxtInfo에는 후행관계를 다 추가해주고, int[] inDegree에는 진입차수를 기록
 * 
 * 2. 큐 선언 후 inDegree[]가 0인 작업을 큐에 추가
 * 
 * 3. 큐
 * 	3-0. 큐값을 꺼낼 때 정답 배열에 큐값 추가
 * 	3-1. 큐 값으로 nxtInfo에서 모든 값을 순회
 * 		3-1-1. 해당 값으로 inDegree를 조회해 -1
 * 		3-1-2. 삭제 후 inDegree가 0이 되면
 * 			a. 큐에 추가

 */
public class Solution {
	static int nodeNum;
	static int edgeNum;
	static List<Integer>[] nxtInfo;
	static int[] inDegree;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int t=1; t<=10; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			nodeNum = Integer.parseInt(st.nextToken());
			edgeNum = Integer.parseInt(st.nextToken());
			
			inDegree = new int[nodeNum+1];
			nxtInfo = new ArrayList[nodeNum+1];
			for(int n=0; n<=nodeNum; n++) {
				nxtInfo[n] = new ArrayList<>();
			}
			
			st = new StringTokenizer(br.readLine());
			for(int e=0; e<edgeNum; e++) {
				int pre = Integer.parseInt(st.nextToken());
				int nxt = Integer.parseInt(st.nextToken());
				
				nxtInfo[pre].add(nxt);
				inDegree[nxt]++;
			}
			
			sb = new StringBuilder();
			
			//2. 큐 선언 후 inDegree[]가 0인 작업을 큐에 추가
			Queue<Integer> q = new ArrayDeque<>();
			
			for(int i=1; i<=nodeNum; i++) {
				if(inDegree[i] == 0) {
					q.offer(i);
				}
			}
			
			// 3. 큐
			while(!q.isEmpty()) {
				// 3-0. 큐값을 꺼낼 때 정답 배열에 큐값 추가
				int workNum = q.poll();
				sb.append(workNum).append(" ");
				
				// 3-1. 큐 값으로 nxtInfo에서 모든 값을 순회
				for(int nxtNum: nxtInfo[workNum]) {
					// 3-1-1. 해당 값으로 inDegree를 조회해 -1
					inDegree[nxtNum]--;
					
					// 3-1-2. 삭제 후 inDegree가 0이 되면
					if(inDegree[nxtNum] == 0) {
						q.offer(nxtNum);
					}
				}
			}
			
			System.out.println("#"+t+" "+sb);
		}
		
	}
}
