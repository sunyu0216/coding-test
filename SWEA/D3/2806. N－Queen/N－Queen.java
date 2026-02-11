import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author seonyu
 * SWEA 2806. N-Queen
 * 
 * 0. 변수들
 * 	0-1. N: 퀸의 개수 및 체스판 크기
 * 	0-2. cols[N]: 1차원 배열 (index: 행, value: 열)
 * 		-> ex) cols[0] = 3 이면 "0번 행의 퀸은 3번 열에 있다"는 의미
 * 	0-3. totalCount: 가능한 배치의 총 가짓수
 * 
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 자연수 N 입력받기 (각 테스트케이스마다 totalCount 초기화 필수)
 * 
 * 
 * 2. 백트래킹 함수
 * 	2-1. 기저조건: row == N 인 경우
 * 		-> 모든 행에 퀸을 하나씩 안전하게 배치했다는 뜻이므로 totalCount 증가 후 리턴
 * 
 * 	2-2. 현재 행(row)에서 0부터 N-1 열(col)까지 반복하며 퀸 배치 시도
 * 	2-3. 유효성 검사 (isAvailable 함수 호출)
 * 	2-4. 유효하다면 cols[row] 에 현재 열을 기록 후 row+1한 값으로 재귀 호출
 * 
 * 
 * 3. 유효성 검사 함수
 * 	3-1. 현재 배치하려는 row와 col의 위치가 이전 행들에 놓인 퀸들과 충돌하는지 확인
 * 		3-1-1. 같은 열에 퀸이 있는가? -> 현재 배치하려는 col과 cols[i]가 같은지 체크
 * 			a. 같으면 false 반환
 * 			b. 아니면 계속
 * 		3-1-2. 대각선에 퀸이 있는가? -> 현재 행과 이전 행의 차이(1) 와 열의 차이가 같은지 체크
 * 			a. 같으면 false 반환
 * 			b. 아니면 true 반환
 * 	
 *
 */
public class Solution {
	
	static int N;
	static int[] cols;
	static int totalCount;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			N = Integer.parseInt(br.readLine());
			cols = new int[N];
			totalCount = 0;
			
			backtracking(0);
			
			System.out.println("#"+t+" "+totalCount);
			
		}
	}
	
	
	// 2. 백트래킹 함수
	static void backtracking(int row) {
		// 2-1. 기저조건: row == N 인 경우
		// 		-> 모든 행에 퀸을 하나씩 안전하게 배치했다는 뜻이므로 totalCount 증가 후 리턴
		if(row == N) {
			totalCount++;
			return;
		}
		
		// 2-2. 현재 행(row)에서 0부터 N-1 열(col)까지 반복하며 퀸 배치 시도
		for(int c=0; c<N; c++) {
			// 2-3. 유효성 검사 (isAvailable 함수 호출)
			if(isAvailable(row, c)) {
				cols[row] = c;
				backtracking(row+1);
			}
		}
	}
	
	// 3. 유효성 검사 함수
	static boolean isAvailable(int r, int c) {
		// 3-1. 현재 배치하려는 row와 col의 위치가 이전 행들에 놓인 퀸들과 충돌하는지 확인
		for (int i = 0; i < r; i++) {
	        // 3-1-1. 같은 열에 퀸이 있는가? -> 현재 배치하려는 col과 cols[i]가 같은지 체크
	        if (cols[i] == c) {
	            return false;
	        }
	        
	        // 3-1-2. 대각선에 퀸이 있는가?
	        // (현재 행 - 비교 행)의 절대값 == (현재 열 - 비교 열)의 절대값
	        if (Math.abs(r - i) == Math.abs(c - cols[i])) {
	            return false;
	        }
	    }
		return true;
	}

}
