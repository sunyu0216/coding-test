import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 8275. 햄스터
 * 
 * @see #main(String[])
 * 1. 테스트 케이스 입력받기
 * 	1-1. 햄스터우리 개수 cageNumber, 각 우리마다의 햄스터 최대 마리수 maxHamster, 경근이가 남긴 기록 개수 recordNumber
 * 	1-2. 경근이가 남긴 기록들 입력받기 records
 * 		1-2-1. I번 우리에서 r번 우리까지의 햄스터 수를 세었더니 s마리였다.
 * 
 * 
 * @see #findHamsterNumber(int, int)
 * 2. 완전탐색 + 순열로 모든 우리에 대해 햄스터의 마리수를 다 조합해보는 함수
 * 	2-1. 기저조건: 마지막 우리까지 다 조합해본 경우
 * 		2-1-1. 경근이가 남긴 기록과 맞는지 체크,
 * 		2-1-2. 기록과 맞다면 현재까지의 전체 햄스터 마리 수가 이전 수보다 큰지 체크,
 * 		2-1-3. 크다면 현재 각 우리의 햄스터 마리수 상태를 저장
 * 	2-2. 각 우리에 햄스터가 0마리부터 maxHamster 마리수 까지 반복하며 재귀로 호출
 * 
 * 
 * @see #isValid()
 * 3. 경근이가 남긴 기록과 맞는지 체크해주는 함수
 * 	3-1. records 배열을 돌며,
 * 		3-1-1. 하나의 기록마다 현재의 햄스터마리수가 경근이가 기록한 햄스터 마리수를 만족하는지 체크
 * 		3-1-2. 만족하면 true를 아니라면 false를 반환
 *
 */
public class Solution {
	
	static int cageNumber;
	static int maxHamsterNumber;
	static int recordNumber;
	static int[][] records;
	static int[] currentCage;
	static int[] bestCage;
	static int maxTotalSum;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		int testcase = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=testcase; t++) {
			
			// 1. 입력받기
			st = new StringTokenizer(br.readLine());
			
			cageNumber = Integer.parseInt(st.nextToken());
			maxHamsterNumber = Integer.parseInt(st.nextToken());
			recordNumber = Integer.parseInt(st.nextToken());
			
			records = new int[recordNumber][3];
			for(int r=0; r<recordNumber; r++) {
				st = new StringTokenizer(br.readLine());
				
				records[r][0] = Integer.parseInt(st.nextToken())-1;
                records[r][1] = Integer.parseInt(st.nextToken())-1;
                records[r][2] = Integer.parseInt(st.nextToken());
			}
		
			// 초기화
            currentCage = new int[cageNumber];
            bestCage = null;
            maxTotalSum = -1;
            
            findHamsterNumber(0, 0);
			
			System.out.print("#" + t + " ");
            if (bestCage == null) {
                System.out.println("-1");
            } else {
                for (int count : bestCage) System.out.print(count + " ");
                System.out.println();
            }
		}
	}
	
	static void findHamsterNumber(int idx, int currentSum) {
		// 기저조건: 끝까지 다 돈 경우
		if(idx == cageNumber) {
			if(isValid()) {
				if(currentSum > maxTotalSum) {
					maxTotalSum = currentSum;
					bestCage = currentCage.clone();
				}
			}
			return;
		}
		
		for(int hamster=0; hamster<=maxHamsterNumber; hamster++) { // 우리당 최대 마리수를 넘지 않게
			currentCage[idx] = hamster; // 현재 우리에 햄스터가 몇마리인지
			findHamsterNumber(idx+1, currentSum+hamster);
		}
	}
	
	static boolean isValid() {
		for(int[] record : records) {
			int total = 0;
			for(int i=record[0]; i<=record[1]; i++) {
				total += currentCage[i];
			}
			if(total!=record[2]) {
				return false;
			}
		}
		return true;
	}

}
