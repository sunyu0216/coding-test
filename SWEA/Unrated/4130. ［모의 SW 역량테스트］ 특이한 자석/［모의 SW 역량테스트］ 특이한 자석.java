import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * * @author seonyu
 * 0. 시계방향이 1, 반시계방향이 -1 / N극이 0, S극이 1
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 테스트케이스 개수 T
 * 		1-2. 자석을 회전시키는 횟수 K
 * 		1-3. 4개 자석의 자성 정보 magnets[][] 배열에 저장 (8개 날)
 * 		1-4. 자석 회전 정보 (자석 번호 idx, 회전 방향 dir)
 * 
 * @see #processRotation(int, int)
 * 2. 현재 입력으로 들어온 자석을 기준으로 연쇄 회전 결정
 * 		2-1. 타겟 자석 기준 왼쪽으로 퍼지며 인접한 날의 자성이 다를 경우 반대 방향 회전 결정
 * 		2-2. 타겟 자석 기준 오른쪽으로 퍼지며 인접한 날의 자성이 다를 경우 반대 방향 회전 결정
 * 		2-3. 모든 자석의 회전 방향이 결정되면 rotate 메서드를 호출하여 실제 배열 이동
 * 
 * @see #rotate(int, int)
 * 3. 단일 자석 실제 회전 처리 (배열 요소 시프트)
 * 		3-1. 시계 방향(1): 배열 요소를 오른쪽으로 한 칸씩 이동
 * 		3-2. 반시계 방향(-1): 배열 요소를 왼쪽으로 한 칸씩 이동
 * 
 * @see #calculateScore()
 * 4. 결과 점수 계산
 * 		4-1. 각 자석의 12시 방향(인덱스 0)이 S극(1)인 경우 점수 부여 (1, 2, 4, 8점)
 *
 */


public class Solution {
    static int[][] magnets;
    static int K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int t = 1; t <= T; t++) {
            K = Integer.parseInt(br.readLine().trim());
            magnets = new int[4][8];

            // 1. 자석 정보 입력 (0: N극, 1: S극)
            for (int i = 0; i < 4; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                for (int j = 0; j < 8; j++) {
                    magnets[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. K번의 회전 수행
            for (int i = 0; i < K; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                int idx = Integer.parseInt(st.nextToken()) - 1; // 자석 번호를 인덱스로 (0~3)
                int dir = Integer.parseInt(st.nextToken());    // 1: 시계, -1: 반시계

                processRotation(idx, dir);
            }

            // 3. 점수 계산
            System.out.println("#" + t + " " + calculateScore());
        }
    }

    // 각 자석의 회전 방향을 먼저 정하고 한꺼번에 회전시키는 함수
    static void processRotation(int idx, int dir) {
        int[] dirs = new int[4];
        dirs[idx] = dir;

        // 왼쪽 자석들 확인
        for (int i = idx; i > 0; i--) {
            // 현재 자석의 9시(인덱스 6)와 왼쪽 자석의 3시(인덱스 2) 비교
            if (magnets[i][6] != magnets[i - 1][2]) {
                dirs[i - 1] = -dirs[i];
            } else {
                break; // 자성이 같으면 더 이상 전파되지 않음
            }
        }

        // 오른쪽 자석들 확인
        for (int i = idx; i < 3; i++) {
            // 현재 자석의 3시(인덱스 2)와 오른쪽 자석의 9시(인덱스 6) 비교
            if (magnets[i][2] != magnets[i + 1][6]) {
                dirs[i + 1] = -dirs[i];
            } else {
                break;
            }
        }

        // 결정된 방향대로 실제 회전 수행
        for (int i = 0; i < 4; i++) {
            if (dirs[i] != 0) {
                rotate(i, dirs[i]);
            }
        }
    }

    // 단일 자석을 실제로 회전시키는 함수
    static void rotate(int idx, int dir) {
        if (dir == 1) { // 시계 방향: 요소들을 오른쪽으로 밀기
            int temp = magnets[idx][7];
            for (int i = 7; i > 0; i--) {
                magnets[idx][i] = magnets[idx][i - 1];
            }
            magnets[idx][0] = temp;
        } else { // 반시계 방향: 요소들을 왼쪽으로 밀기
            int temp = magnets[idx][0];
            for (int i = 0; i < 7; i++) {
                magnets[idx][i] = magnets[idx][i + 1];
            }
            magnets[idx][7] = temp;
        }
    }

    // 12시 방향(인덱스 0) 확인하여 점수 합계
    static int calculateScore() {
        int sum = 0;
        if (magnets[0][0] == 1) sum += 1;
        if (magnets[1][0] == 1) sum += 2;
        if (magnets[2][0] == 1) sum += 4;
        if (magnets[3][0] == 1) sum += 8;
        return sum;
    }
}