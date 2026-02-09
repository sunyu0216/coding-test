import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 10972. 다음 순열
 * 
 * 1. 입력받기
 * 	1-1. 자연수 범위 N 입력받기
 * 	1-2. 현재 상태 순열 currentP[] 입력받기
 * 
 * 
 * @see #NextPermutation()
 * 2. NextPermutation 구현
 * 	2-1. 현재 배열에서 거꾸로 순회하며, 값이 줄어드는 인덱스 찾기 -> 각각 pivot, pivotIdx에 저장
 * 		2-1-1. 여기서 값이 줄어드는 인덱스를 찾지 못하면 false 반환하기
 * 	2-2. 다시 거꾸로 순회하며 pivot보다 큰 숫자가 나오는 인덱스 찾기
 * 	2-3. pivot과 이 값을 서로 교체
 * 	2-4. pivotIdx+1 부터 배열의 끝까지 거꾸로 뒤집기
 *
 */
public class Main {
	static int N;
	static int[] currentP;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		currentP = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			currentP[i] = Integer.parseInt(st.nextToken());
		}
		
        if (NextPermutation()) {
            StringBuilder sb = new StringBuilder();
            for (int val : currentP) sb.append(val).append(" ");
            System.out.println(sb.toString().trim());
        } else {
            System.out.println(-1);
        }

	}
	
	static boolean NextPermutation() {
		int pivot=0; int pivotIdx=0;
		boolean flag = false;
		
		for(int i=N-1; i>0; i--) {
			if(currentP[i]>currentP[i-1]) {
				pivot = currentP[i-1];
				pivotIdx = i-1;
				flag = true;
				break;
			}
		}
		
		if(flag) {
			for(int i=N-1; i>=0; i--) {
				if(pivot<currentP[i]) {
					int temp = currentP[i];
					currentP[i] = pivot;
					currentP[pivotIdx] = temp;
					break;
				}
			}
			
			int start = pivotIdx+1;
			int end = N-1;
			while (start < end) {
				int temp = currentP[start];
				currentP[start] = currentP[end];
				currentP[end] = temp;
				
				start++;
				end--;
				
			}
			return true;
		}else {
			return false;
		}
	}

}
