import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * BOJ 17281. ⚾️
 *
 * 1. 입력받기 및 변수
 *  1-1. 전체 이닝 수 totalInning
 *  1-2. 각 이닝에 따른 선수들 득점 기록 scores[totalInning][10]
 *  1-3. 9명의 선수를 타순에 세웠는지 판단하기 위한 visited[10]
 *  1-4. 매번 타순을 기록하기 위한 order[10]
 *  1-5. 최고 점수를 기록하기 위한 maxScore
 *  
 *
 * @see #permutation(int, int[]) 
 * 2. 순열로 타순 정하기
 *  2-1. 기저조건: 모든 선수의 타순을 정했다면,
 *      2-1-1. 점수를 계산해보기 -> calculateScore
 *      2-1-2. 현재 점수가 더 높으면 갱신
 *  2-2. 조건: 현재 타순이 4번째라면 (4번째 타순은 무조건 1번 선수이므로) 바로 재귀 호출
 *  2-3. 아니라면, 2번에서 9번 선수까지 중 반복하며,
 *      2-2-1. 해당 인덱스 선수 방문처리
 *      2-2-2. 현재 순서 배열에 선수 추가하기 -> 재귀 호출
 *      2-2-3. 모든 순회를 위해 방문처리 해제
 *
 * 
 * @see #calculateScore(int[]) 
 * 3. 점수 계산하는 함수
 *  3-1. 각 이닝마다 아웃을 세는 outCount, 전체 점수를 계산하는 totalScore, 현재 베이스 상황을 나타내는 bases, 이닝에 상관없이 타순을 나타내는 batterIdx
 *  3-2. 각 이닝을 순회하며,
 *  3-3. 이닝이 초기화 될 때마다, outCount, bases 는 초기화 되어야 함
 *  3-4. outCount가 3 미만일 동안 타순을 계속 순회하며, 득점 계산 + 진루 계산하기
 *  
 *      3-4-1. batterIdx 를 통해 현재 타순에 선 선수의 현재 이닝에 대한 득점을 받아오기 result
 *      3-4-2. 이 득점이 만약 0이라면, 아웃이므로 outCount++
 *      3-4-3. 아니라면 일단 진루하므로, 
 *      
 *          a. bases를 거꾸로 순회하며, 현재 bases에 나가있는 선수들 이동시켜주기
 *              i) 현재 루 + 득점한 값 result가 4를 넘으면 홈에 들어온 것이므로 totalScore++
 *              j) 못넘었다면, 현재 루 + 득점한 값 result 만큼 진루시키기
 *              k) 원래 자리 false로 바꿔주기
 *              
 *          b. 현재 타자 이동시켜주기
 *              i) 득점 값이 4라면 홈런이므로 totalScore++
 *              j) 아니라면 result 값 만큼 진루시켜주기
 *              
 *      3-4-4. batterIdx%9 +1 만큼 해줘서 계속 타순이 돌아가게끔 유지
 *
 */

public class Main {
    static int totalInning;
    static int[][] scores;
    static boolean[] visited = new boolean[10]; // 해당 선수를 타순에 세웠는지 판단용
    static int maxScore;
    static int[] order;

    public static void main(String[] args) throws IOException {

        // 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        totalInning = Integer.parseInt(br.readLine());

        scores = new int[totalInning][10];
        for (int i = 0; i < totalInning; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                scores[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        order = new int[10];
        order[4] = 1; // 항상 타순 4번은 1번으로 고정
        visited[1] = true;

        permutation(1, order);

        System.out.println(maxScore);

    }

    // 선수들의 인덱스,
    static void permutation(int idx, int[] currentOrder){

        // 기저조건: 모든 선수의 타순을 정했다면 (idx=1이 선수 한명 배정했다는 뜻이므로 10에서 멈춰야 9명을 배정하는 것)
        if(idx == 10){
            int currentScore = calculateScore(currentOrder);
            maxScore = Math.max(maxScore, currentScore);
            return;
        }

        // 조건: 네번째 타순이라면 바로 다음 재귀로 넘기기 -> 4번째 타순은 1번 선수로 고정이니까
        if(idx == 4){
            //System.out.println("4번 타순은 1번 선수로 고정");
            permutation(idx+1, currentOrder);
        }else{
            for(int i=2; i<=9; i++){ // 선수들을 순회하며,
                if(!visited[i]){
                    visited[i] = true;
                    currentOrder[idx] = i;
                    permutation(idx+1, currentOrder);
                    visited[i] = false;
                }
            }
        }
    }


    // 점수 계산 함수
    // 매개변수로 현재 타순 배열을 받음
    static int calculateScore(int[] currentOrder){
        int outCount;
        int totalScore = 0;
        int batterIdx = 1;

        for(int[] eachInning: scores){
            outCount = 0; // 매 이닝마다 0으로 초기화
            boolean[] bases = new boolean[4]; // 매 이닝마다 베이스 상황도 초기화

            while(outCount < 3){
                int nowPlayerNum = currentOrder[batterIdx]; // 현재 타석의 선수 번호
                int result = eachInning[nowPlayerNum]; // 그 선수의 이닝 결과

                if(result == 0){ // 아웃이라면 아웃카운트 증가
                    outCount++;
                }else {
                    // 진루 및 득점 로직 = 아웃이 아니라면 무조건 진루는 하게 되므로

                    // 1. 기존 주자들 이동 (3루부터 체크해야 겹치지 않음)
                    for (int i = 3; i >= 1; i--) {
                        if (bases[i]) {
                            if (i + result >= 4) { // 현재 인덱스와 선수가 득점한 게 4이상이면 점수가 남
                                totalScore++;
                            } else { // 아니여도 득점한 만큼 진루시키기
                                bases[i + result] = true;
                            }
                            bases[i] = false; // 원래 자리는 다시 비워주기
                        }
                    }
                    // 2. 타자 본인 이동
                    if (result == 4) {
                        totalScore++; // 홈런이면 타자도 득점
                    } else {
                        bases[result] = true;     // 안타, 2루타, 3루타면 해당 루로 이동
                    }
                }

                batterIdx = (batterIdx%9)+1;
            }

        }
        return totalScore;
    }
}
