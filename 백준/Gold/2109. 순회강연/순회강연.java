import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import javax.sound.midi.Soundbank;

/**
 * 
 * @author seonyu
 * 
 * 1. 입력받기
 * 	1-1. 대학 요청 개수 requestNum
 * 	1-2. 보수와 요청 일수 제한 - 우선순위 큐 = reward, days
 * 
 * 2. 우선순위 큐를 시간 오름차순으로 정렬
 * 
 * 3. 큐
 * 	3-1. 큐에서 꺼낸 값 중 보수를 임시 큐에 넣어주기
 * 	3-2. 현재 임시 큐의 크기(강연하러 가기로 결정한 대학교) > 현재 마감일 이면 다 갈수가 없으므로 임시 큐 중 가장 작은 값 삭제
 * 
 * 4. 임시 큐를 돌면서 남은 보수를 다 더해주기
 *
 */
public class Main {
	static int requestNum;
	static PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
		return Integer.compare(a[1], b[1]);
	});
	
	static int totalReward;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		requestNum = Integer.parseInt(br.readLine());
		
		if(requestNum == 0) {
			System.out.println(0);
			return;
		}
		
		StringTokenizer st;
		for(int r=0; r<requestNum; r++) {
			st = new StringTokenizer(br.readLine());
			int reward = Integer.parseInt(st.nextToken());
			int days = Integer.parseInt(st.nextToken());
			
			q.offer(new int[] {reward, days});
		}// 입력 끝
		
		
		totalReward = 0;
		PriorityQueue<Integer> tempQ = new PriorityQueue<>();
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			int re = curr[0];
			int deadline = curr[1];
			
			tempQ.offer(re);
			
			if(tempQ.size() > deadline) {
				tempQ.poll(); // 가장 작은 보수 제외
			}
		}
		while(!tempQ.isEmpty()) {
			totalReward+=tempQ.poll();
		}
		
		System.out.println(totalReward);
	}

}
