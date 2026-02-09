import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author seonyu
 * SWEA 4008. 숫자 만들기
 *
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 숫자의 개수 numberNum
 * 	1-3. 각 연산자의 개수 operator[4]
 * 		1-3-1. 개수만큼 해당 연산자 인덱스 증가시켜주기
 * 	1-4. 수식에 사용되는 숫자 numbers[numberNum]
 *
 *
 * 2. 재귀함수
 * 	2-1. 기저조건: 모든 연산자를 다 사용했을 때
 * 		2-1-1. 현재 연산값을 최대값, 최소값과 비교하고 갱신
 * 	2-2. 현재 인덱스에 해당하는 연산자를 꺼내서 계산 -> 계산하는 함수 필요
 * 		2-2-1. 현재 인덱스에 해당하는 연산자 꺼냈으므로 개수 하나 줄이기
 * 		2-2-2. 계산값을 다시 더해서 재귀함수 호출
 * 		2-2-3. 호출한 뒤에는 다시 인덱스값 복구시키기
 *
 *
 * 3. 계산하는 함수
 * 	3-1. 연산자를 매개변수로 받기
 * 	3-2. 해당하는 수와 연산자를 계산한 값을 반환하기
 *
 *
 */
public class Solution {
    static int numberNum;
    static int[] operator = new int[4];
    static int[] numbers;
    static int totalMax;
    static int totalMin;

    public static void main(String[] args) throws NumberFormatException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for(int t=1; t<=T; t++) {

            // 입력받기
            numberNum = Integer.parseInt(br.readLine().trim());
            numbers = new int[numberNum];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<4; i++) {
                operator[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<numberNum; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            totalMax = Integer.MIN_VALUE;
            totalMin = Integer.MAX_VALUE;

            dfs(1, numbers[0]); // 주의!

            int answer = totalMax-totalMin;
            System.out.println("#" + t + " " + answer);

        }
    }

    static void dfs(int idx, int currentResult) {
        if(idx == numberNum) {
            totalMax = Math.max(totalMax, currentResult);
            totalMin = Math.min(totalMin, currentResult);
        }

        for(int i=0; i<4; i++) {
            if(operator[i] > 0) {
                operator[i]--;
                int newResult = calculate(i, currentResult, numbers[idx]);

                dfs(idx+1, newResult);

                operator[i]++;
            }
        }
    }

    // 계산해주는 함수
    // 매개변수로 차례대로 연산자, 연산되는 수1, 연산되는 수2
    static int calculate(int operatorIdx, int targetNum1, int targetNum2) {

        if(operatorIdx == 0) {
            return targetNum1+targetNum2;
        }else if(operatorIdx == 1) {
            return targetNum1-targetNum2;
        }else if(operatorIdx == 2) {
            return targetNum1*targetNum2;
        }else if(operatorIdx == 3) {
            return targetNum1/targetNum2;
        }else {
            return 0;
        }
    }

}
