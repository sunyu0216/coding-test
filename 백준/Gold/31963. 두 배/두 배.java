import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 31963. 두 배
 * 
 * 1. 입력받기
 * 	1-1. 수열의 개수 num
 * 	1-2. 수열 저장 ascending[]
 * 	1-3. 해당 수에 2가 몇번 곱해졌는지 저장하는 power[]
 * 
 * 2. 로직
 * 	2-1. ascending[i-1] <= ascending[i] 인 경우 = 커서 지수를 아낄 수 있을때!! = 곱하기 횟수를 줄일 수 있을때!!
 * 		2-1-1. ascending[i-1] * 2의 d승 <= ascending[i-1]을 만족시키는 최대의 d를 구해야 함.
 * 		2-1-2. a(i) = a(i-1) + d
 * 
 * 	2-2. ascending[i-1] > ascending[i] 인 경우 = 작아서 보충이 필요할 때!!
 * 		2-2-1. ascending[i] * 2의 c승 >= ascending[i-1]을 만족시키는 최소의 c를 구해야 함.
 * 		2-2-2. a(i) = a(i-1) + c -> *a(i)는 i번쨰 숫자에 2가 곱해진 횟수
 * 
 * 	2-3. 즉 이 2가 곱해지는 횟수를 따로 power에 저장해주고 둘의 값을 비교할땐 원래 숫자끼리 비교해서 곱해지는 횟수를 빠르게 찾아야 함
 *
 */
public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int num = Integer.parseInt(br.readLine());
		int[] ascending = new int[num];
		int[] power = new int[num];
		int totalCnt = 0;
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<num; i++) {
			ascending[i] = Integer.parseInt(st.nextToken());
		}// 입력 끝
		
		for(int i=1; i<num; i++) {
			int pre = ascending[i-1];
			int nxt = ascending[i];
			
			int cnt = 0;
			
			if(pre <= nxt) { // 원래 숫자를 비교하면 앞 숫자보다 큼
				int changedPre = pre;
				while(changedPre*2 <= nxt) { // 조건문 주의: pre에 2를 몇 번곱해야 nxt를 넘지 않는 최대치가 될까?
					changedPre *= 2;
					cnt++;
				}
				power[i] = Math.max(0, power[i-1]-cnt);
				//System.out.println(nxt+"에 2를 "+power[i]+" 만큼 곱했더니 "+pre+" 에 "+power[i-1]+"만큼 2를 곱한 수 보다 커지거나 같아짐.");
			}else { // 원래 숫자를 비교해도 앞 숫자보다 작음
				int changedNxt = nxt;
				while(changedNxt < pre) { // 조건문 주의: nxt가 pre보다 커지기 위한 최소 횟수
					changedNxt *= 2;
					cnt++;
				}
				power[i] = power[i-1] + cnt;
				//System.out.println(nxt+"에 2를 "+cnt+" 만큼 곱했더니 "+changedNxt+"가 되어 "+pre+" 에 "+power[i-1]+"만큼 2를 곱한 수 보다 커지거나 같아짐.");
			}
			
			totalCnt += power[i];
		}
		
		System.out.println(totalCnt);
	}

}