import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 
 * @author seonyu
 * BOJ 10775. 공항
 * 
 * 1. 입력받기
 * 	1-1. 게이트의 수 gateNum
 * 	1-2. 비행기의 수 airplaneNum
 * 	1-3. 비행기를 도킹할 수 있는 게이트 범위 
 * 
 * 2. 결국 도킹가능한 범위 내에서 가장 큰 번호에 할당해줘야 다음 비행기들이 도킹할 수 있는 가능성을 많이 열어둘 수 있음.
 * 
 * 가능한 도킹 범위를 조회하면 -> 가능한 가장 큰 번호가 나오는 배열이 필요 possibleGate[]
 * 
 * 3. 현재 도킹 범위를 순회하며,
 * 	3-1. possibleGate[현재 도킹 범위]가 비어있다면 바로 현재 비행기값 넣어주기
 * 	3-2. 만약 다른 값이 있다면? 이 값은 이 범위일때 현재 가능한 도킹 범위를 기록한 것이므로 바로 이 인덱스로 이동
 * 		3-2-1. 이 인덱스가 0이라면 더이상 도킹이 불가능하므로 즉시 cnt 리턴
 * 		3-2-2. 이 인덱스가 1 이상이라면 possibleGate[현재 도킹 범위]를 인덱스로 업데이트 & cnt++
 * -> 유니온 파인드...?
 * 
 * 4. possibleGate[] 배열을 parent[] 배열이라 생각하기
 * 
 *
 */
public class Main {
	static int gateNum;
	static int airplaneNum;
	static int[] possibleGate;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		gateNum = Integer.parseInt(br.readLine());
		airplaneNum = Integer.parseInt(br.readLine());
		
		possibleGate = new int[gateNum+1];
		Arrays.fill(possibleGate, -1);
		
		//System.out.print("초기 가능 게이트: ");
//		for(int i=1; i<possibleGate.length; i++) {
//			System.out.print(possibleGate[i] + " ");
//		}
//		System.out.println();
		
		int cnt = 0;
		
		for(int idx=1; idx<airplaneNum+1; idx++) {
			int currRange = Integer.parseInt(br.readLine());
			
			int dockingPlace = findDockingPlace(currRange);
			if(dockingPlace == 0) {
				System.out.println(cnt);
				return;
			}else {
				possibleGate[dockingPlace] = dockingPlace-1; // 이제 이 범위에서는 currRange보다 이전 게이트에서만 도킹이 가능
				cnt++;
			}
			
//			for(int i=1; i<possibleGate.length; i++) {
//				System.out.print(possibleGate[i] + " ");
//			}
//			System.out.println();
		}
		
		System.out.println(cnt);
	}
	
	
	public static int findDockingPlace(int x) {
		if(possibleGate[x] == -1) { // 텅 비어있으면 자리 내주기
			return x;
		}else if(possibleGate[x] == 0){ // 자리가 차있고 이 인덱스 아래 게이트들은 다 꽉찬 상태
			return 0;
		}else { // 이 자리는 차 있고 가능한 인덱스가 있다면 연결해주기
			return possibleGate[x] = findDockingPlace(possibleGate[x]); 
		}
	}

}
