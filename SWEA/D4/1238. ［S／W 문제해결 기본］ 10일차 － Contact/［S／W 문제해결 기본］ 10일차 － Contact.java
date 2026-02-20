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
 * SWEA 1238. Contact
 * 
 * 1. 입력받기
 * 	1-1. 입력받는 데이터 길이 dataLen, 시작점 startNum
 * 	1-2. 입력받는 데이터 길이/2 만큼 반복하며 연락망 정보를 저장하기
 * 		연락망 정보는 List<>[] contact에 입력받기
 * 
 * 2. 시작점을 큐에 넣기
 * 
 * 3. 큐
 * 	3-1. 큐에서 꺼내 리스트 조회
 * 	3-2. 큐에서 꺼낸 값 방문처리
 * 	3-3. 이번에 꺼낸 값의 타이밍이 가장 나중인지 체크
 * 		3-3-1. 가장 나중이라면 정답 변수에 현재 꺼낸 값 넣기
 * 		3-3-2. 가장 큰 타이밍의 값 갱신
 * 	3-4. 같다면 정답 변수와 비교해 더 큰 값으로 갱신 
 * 	3-5. 리스트 조회한 값이 있다면
 * 		3-5-1. 해당 숫자를 방문하지 않았다면
 * 		3-5-2. 큐에 넣기
 *
 */
public class Solution {
	static int dataLen;
	static int startNum;
	static List<Integer>[] contact;
	static boolean[] visited;
	static int lastContact;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int t=1; t<=10; t++) {
			st = new StringTokenizer(br.readLine());
			dataLen = Integer.parseInt(st.nextToken());
			startNum = Integer.parseInt(st.nextToken());
			contact = new ArrayList[101];
			for(int i=1; i<=100; i++) contact[i] = new ArrayList<>();
			visited = new boolean[101];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<dataLen/2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				if(contact[from] == null) {
					contact[from] = new ArrayList<>();
				}
				contact[from].add(to);
			}
			
			Queue<int[]> q = new ArrayDeque<>();
			q.offer(new int[] {startNum, 1});
			
			int lastTime = 1;
			lastContact = 0;
			
			while(!q.isEmpty()) {
				int[] curr = q.poll();
				int currNum = curr[0];
				int currTime = curr[1];
				
				// 3-2. 큐에서 꺼낸 값 방문처리
				visited[currNum] = true;
				
				// 3-3. 이번에 꺼낸 값의 타이밍이 가장 나중인지 체크
				if(lastTime < currTime) {
					// 3-3-1. 가장 나중이라면 정답 변수에 현재 꺼낸 값 넣기
					// 3-3-2. 가장 큰 타이밍의 값 갱신
					lastContact = currNum;
					lastTime = currTime;
				}else if(lastTime == currTime){
					// 3-4. 같다면 정답 변수와 비교해 더 큰 값으로 갱신 
					lastContact = Math.max(lastContact, currNum);
				}
				
				// 3-5. 리스트 조회한 값이 있다면
				for(int nextNum: contact[currNum]) {
					// 3-5-1. 해당 숫자를 방문하지 않았다면
					if(!visited[nextNum]) {
						// 3-5-2. 큐에 넣기
						visited[nextNum] = true;
						q.offer(new int[] {nextNum, currTime+1});
					}
				}
			}
			System.out.println("#"+t+" "+lastContact);
		}
		
	}

}
