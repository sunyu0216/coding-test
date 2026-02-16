import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * SWEA 4012. 요리사
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 개수 T
 *  1-2. 재료의 개수 ingredientNum
 *  1-3. 각 재료들끼리의 시너지 synergy[][]
 *  1-4. 각 요리 A, B에 넣을 재료를 표시할 isSelected 배열 선언
 *  1-5.
 *
 * 2. 조합
 *  2-1. 기저조건: 전체 재료의 1/2을 다 뽑았을 때
 *      2-1-1. isSelected를 기준으로 각 요리의 시너지 합 구하는 함수 호출
 *      2-1-2. 둘의 차를 구해 최소값 비교 후 갱신
 *
 *  2-2. 가지치기: 만약 배열의 끝이라면 return
 *
 *  2-3. 현재 재료를 넣을지 말지 결정하기
 *      2-3-1. 넣는다면 isSelected true로 변환하고 재귀호출
 *      2-3-2. 안넣는다면 그대로 재귀호출
 *
 * 3. 사용한 재료들의 시너지 합을 구해주는 함수
 *  3-1. isSelected 배열을 이중으로 순회하며,
 *      3-1-1. 이 재료 쌍이 둘다 true라면 요리 A 시너지에 추가
 *      3-1-2. 둘다 false라면 요리 B 시너지에 추가
 *      3-1-3. 둘이 다르다면 둘다 맞는 쌍이 나올때까지 계속하기
 *
 */
public class Solution {
    static int ingredientNum;
    static int[][] synergy;
    static boolean[] isSelected;
    static int totalMinDifference;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++) {
            ingredientNum = Integer.parseInt(br.readLine());
            synergy = new int[ingredientNum][ingredientNum];
            isSelected = new boolean[ingredientNum];

            for(int i=0; i<ingredientNum; i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<ingredientNum; j++){
                    synergy[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            totalMinDifference = Integer.MAX_VALUE;

            combination(0, 0);
            System.out.println("#"+t+" "+totalMinDifference);
        }
    }

    static void combination(int idx, int count){
        // 2-1. 기저조건: 전체 재료의 1/2을 다 뽑았을 때
        if(count == ingredientNum/2){
            // 2-1-1. isSelected를 기준으로 각 요리의 시너지 합 구하는 함수 호출
            int[] synergies = getSynergyDifference();

            // 2-1-2. 둘의 차를 구해 최소값 비교 후 갱신
            totalMinDifference = Math.min(totalMinDifference, Math.abs(synergies[0] - synergies[1]));

            return;
        }

        // 2-2. 가지치기: 만약 배열의 끝이라면 return
        if(idx == ingredientNum) return;

        // 2-3. 현재 재료를 넣을지 말지 결정하기
        //   2-3-1. 넣는다면 isSelected true로 변환하고 재귀호출
        isSelected[idx] = true;
        combination(idx+1, count+1);

        //   2-3-2. 안넣는다면 그대로 재귀호출
        isSelected[idx] = false;
        combination(idx+1, count);
    }

    // 3. 사용한 재료들의 시너지 합을 구해주는 함수
    static int[] getSynergyDifference(){
        int sumA = 0;
        int sumB = 0;

        // 3-1. isSelected 배열을 이중으로 순회하며,
        for(int i=0; i<ingredientNum; i++){
            for(int j=0; j<ingredientNum; j++){
                if(isSelected[i] && isSelected[j]){
                    // 3-1-1. 이 재료 쌍이 둘다 true라면 요리 A 시너지에 추가
                    sumA += synergy[i][j];
                }else if(!isSelected[i] && !isSelected[j]){
                    // 3-1-2. 둘다 false라면 요리 B 시너지에 추가
                    sumB += synergy[i][j];
                }
            }
        }

        return new int[] {sumA, sumB};
    }
}
