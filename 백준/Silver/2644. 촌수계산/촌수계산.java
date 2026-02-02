import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 전체 사람들 수 입력받기 peopelNum -> 이차원 배열 생성 relationMap
 * 		1-2. 촌 수를 알아내야 하는 사람들 입력받기 findP1, findP2
 * 		1-3. 관계를 알려주는 줄 수 입력받기 N
 * 		1-4. 관계 정보를 relationMap에 저장
 * 
 * 2. findP1, findP2를 기반으로 관계 파악
 * 		2-0. ArrayDeque로 방문 제어, 이차원 배열 visited로 중복 방문 방지
 * 		2-1. q에 findP1을 기준으로 탐색 시작
 * 		2-2. 관계에 있는 사람을 만나면 찾던 findP2인지 검사
 * 		2-3. 맞다면 현재 촌수+1을 반환하고 끝
 * 		2-4. 아니라면 q에 현재 사람과 촌수+1을 넣고 반복
 * 
 */
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int peopleNum = Integer.parseInt(br.readLine());
		int[][] relationMap = new int[peopleNum+1][peopleNum+1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int findP1 = Integer.parseInt(st.nextToken());
		int findP2 = Integer.parseInt(st.nextToken());
		
		int N = Integer.parseInt(br.readLine());
		
		for(int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			
			int parent = Integer.parseInt(st.nextToken());
			int child = Integer.parseInt(st.nextToken());
			
			relationMap[parent][child] = 1;
			relationMap[child][parent] = 1;
		}
		
		// 큐 선언
		Deque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {findP1, 0});
		
		// 방문 제어
		boolean[] visited = new boolean[peopleNum + 1];
		
		visited[findP1] = true;
		
		while(!q.isEmpty()) {
			int[] current = q.poll();
			int currNum = current[0];
			int currDegree = current[1];
			
			for(int r=0; r<peopleNum+1; r++) {
				//System.out.print(relationMap[currNum][r]+" ");
				if(relationMap[currNum][r] == 1 && !visited[r]) { // (방문 안한 사람 중)현재 사람과 관계인 사람이 있다면
					if(r==findP2) { // 관계에 있는 사람이 찾던 사람이면
						// 지금까지 계산한 촌수 반환
						System.out.println(currDegree+1);
						return;
					}else {
						// r을 기준으로 다시 찾기
						q.offer(new int[] {r, currDegree+1});
						// 방문처리
						visited[r] = true;
					}
				}
			}
		}
		System.out.println(-1);
		
		
		
		
	}
}
