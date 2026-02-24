import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 28107. 회전초밥
 * 
 * 1. 입력받기 & 변수
 * 	1-1. 손님의 수 customerNum, 초밥의 수 sushiNum
 * 	1-2. 각 손님의 주문 목록 
 * 		1-2-1. 초밥 종류를 인덱스로, List[] preferredCustomerList에도 이 초밥을 선호하는 손님을 저장
 * 			* 이때 초밥의 개수가 5개라 해서 꼭 초밥 종류 수가 5이하로 주어지지 않는다.
 * 	1-3. 제공되는 초밥의 순서 sushiOrder[]
 * 	1-4. 각 손님이 먹을 수 있는 초밥의 개수 sushiCount[]
 * 
 * 
 * 2. 제공되는 초밥의 순서를 순회하며,
 * 	2-1. 현재 초밥을 선호하는 손님이 있는지 체크
 * 		2-1-1. 있다면 가장 빠른 손님에게 초밥을 제공
 * 		2-1-2. 그 손님 번호를 preferredCustomer에서 제거
 * 		2-1-3. 그 손님이 먹을 수 있는 초밥 개수 +1
 * 	2-2. 없다면 이 초밥은 pass
 * 
 *
 */
public class Main {
	static int customerNum;
	static int sushiNum;
	static List<Integer>[] preferredCustomerList;
	static int[] sushiOrder;
	static int[] sushiCount;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		customerNum = Integer.parseInt(st.nextToken());
		sushiNum = Integer.parseInt(st.nextToken());
		
		preferredCustomerList = new ArrayList[200001];
		for(int i=0; i<=200000; i++) {
			preferredCustomerList[i] = new ArrayList<>();
		}
		
		sushiOrder = new int[sushiNum];
		sushiCount = new int[customerNum+1];
		
		for(int customer=1; customer<=customerNum; customer++) {
			st = new StringTokenizer(br.readLine());
			
			int wantedSushiNum = Integer.parseInt(st.nextToken());
			for(int w=0; w<wantedSushiNum; w++) {
				int sushiType = Integer.parseInt(st.nextToken());
				preferredCustomerList[sushiType].add(customer);
			}
		}
		
		for (int i = 1; i <= customerNum; i++) {
		    Collections.sort(preferredCustomerList[i]);
		}
		
		st = new StringTokenizer(br.readLine());
		for(int order=0; order<sushiNum; order++) {
			sushiOrder[order] = Integer.parseInt(st.nextToken());
		}
		
		
		// 2. 제공되는 초밥의 순서를 순회하며,
		for(int currOrder: sushiOrder) {
			// 2-1. 현재 초밥을 선호하는 손님이 있는지 체크
			if(preferredCustomerList[currOrder].size() > 0) {
				// 2-1-1. 있다면 가장 빠른 손님에게 초밥을 제공
				int customer = preferredCustomerList[currOrder].get(0);
				
				// 2-1-2. 그 손님 번호를 preferredCustomer에서 제거
				preferredCustomerList[currOrder].remove(0);
				
				// 2-1-3. 그 손님이 먹을 수 있는 초밥 개수 +1
				sushiCount[customer]++;
			}
			// 2-2. 없다면 이 초밥은 pass
		}
		
		for(int i=1; i<=customerNum; i++) {
			System.out.print(sushiCount[i]+ " ");
		}
	}

}
