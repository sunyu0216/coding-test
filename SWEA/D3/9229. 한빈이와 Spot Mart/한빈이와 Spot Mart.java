import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 1. 입력받기
 * 		1-1. 테스트케이스 int testcase
 * 		1-2. 진열된 과자 개수 int snackNum
 * 		1-3. 들 수 있는 최대 무게 int MaxWeight
 * 		1-4. 진열된 과자 무게 Integer[] snackWeight
 * 
 * 2. 진열된 과자 무게를 오름차순으로 정렬
 * 3. 포인터 2개를 사용해서 무게의 합 판별
 * 		3-1. i=0 시작, j=snackNum-1 로 시작
 * 		3-2. 무게 합이 최대무게보다 크면 j를 하나 땡기고
 * 		3-3. 무게 합이 최대 무게보다 작으면 i를 하나 밀고
 * 
 */

public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testcase = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=testcase; tc++) {
			
			st = new StringTokenizer(br.readLine());
			int snackNum = Integer.parseInt(st.nextToken());
			int MaxWeight = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			Integer[] snackWeight = new Integer[snackNum];
			for(int i=0; i<snackNum; i++) {
				snackWeight[i] = Integer.parseInt(st.nextToken());
			}
			
			// 정렬하기(오름차순)
			Arrays.sort(snackWeight);
			
			// 무게의 합 판별
			int left=0;
			int right=snackNum-1;
			int sum=0;
			int maxResult = -1;
			
			while(left<right) {
				sum = snackWeight[left]+snackWeight[right];
				
				if(sum <= MaxWeight) { // 들 수 있는 최대 무게보다 작으면 비교 후 left 증가
					maxResult = Math.max(maxResult, sum);
					left++;
				}else { // 최대 무게보다 크면 right--;
					right--;
				}
			}
			System.out.println("#" + tc + " " + maxResult);
		}
		
	}
}
