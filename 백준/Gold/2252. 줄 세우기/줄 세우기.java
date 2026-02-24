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
 * BOJ 2252. 줄 세우기
 * 
 * 1. 입력받기
 * 	1-1. 학생의 수 studentNum, 키를 비교하는 횟수 comparisonNum
 * 	1-2. A B -> 학생 A가 학생 B 앞에 서야 함
 * 		inDegree[], nxtStudents[]에 학생들의 키 비교 정보를 담아주기
 * 
 * 2. inDegree의 값이 0인 학생들을 큐에 넣기
 * 
 * 3. 큐
 * 	3-1. 값을 꺼내서 해당 값 뒤에 오는 학생을 조회 & 해당 값 방문처리 & 해당 값 StringBuilder에 추가하기
 * 	3-2. 역으로 그 학생 다음에 와야할 학생들을 순회
 * 		3-2-1. 그 학생들의 inDegree값을 1씩 줄이기
 * 		3-2-2. 만약 inDegree가 0이 되었다면 그 학생을 큐에 넣기
 * 
 *
 */

public class Main {
	static int studentNum;
	static int comparisonNum;
	static int[] inDegree;
	static List<Integer>[] nxtStudents;
	static boolean[] visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		studentNum = Integer.parseInt(st.nextToken());
		comparisonNum = Integer.parseInt(st.nextToken());
		
		inDegree = new int[studentNum+1];
		nxtStudents = new ArrayList[studentNum+1];
		visited = new boolean[studentNum+1];
		for(int i=0; i<=studentNum; i++) {
			nxtStudents[i] = new ArrayList<>();
		}
		
		for(int m=0; m<comparisonNum; m++) {
			st = new StringTokenizer(br.readLine());
			int pre = Integer.parseInt(st.nextToken());
			int nxt = Integer.parseInt(st.nextToken());
			
			inDegree[nxt]++;
			nxtStudents[pre].add(nxt);
			
		}
		
		Queue<Integer> q = new ArrayDeque<>();
		
		// 2. inDegree의 값이 0인 학생들을 큐에 넣기
		for(int i=1; i<=studentNum; i++) {
			if(inDegree[i] == 0) {
				q.offer(i);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		// 3. 큐
		while(!q.isEmpty()) {
			// 3-1. 값을 꺼내서 해당 값 방문처리 & 해당 값 StringBuilder에 추가하기
			int curr = q.poll();
			
			visited[curr] = true;
			sb.append(curr).append(" ");
			
			// 3-2. 역으로 그 학생 다음에 와야할 학생들을 순회
			for(int nxtStudent: nxtStudents[curr]) {
				// 3-2-1. 그 학생들의 inDegree값을 1씩 줄이기
				inDegree[nxtStudent]--;
				
				// 3-2-2. 만약 inDegree가 0이 되었다면 그 학생을 큐에 넣기
				if(inDegree[nxtStudent] == 0) {
					q.offer(nxtStudent);
				}
			}
		}
		
		System.out.println(sb);
		
	}

}
