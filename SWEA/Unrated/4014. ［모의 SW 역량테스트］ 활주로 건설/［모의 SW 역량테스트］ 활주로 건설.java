import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 4014. 활주로 건설
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 맵 사이즈 size, 경사로 가로 길이 slideLen
 * 	1-3. 지형 정보 int[][] map
 * 
 * 2. 활주로를 설치할 수 있는 조건들
 * 	2-1. 하나의 행이나 열의 지형 높이가 다 똑같은 경우
 * 	2-2. 경사로를 활용해 설치하는 경우
 * 		2-2-1. 하나의 행이나 열에서 높이 차이가 1밖에 안나는 경우
 * 			2-2-1-1. 높이 차이가 나는 부분부터 동일한 높이가 경사로 길이만큼 이어져 있는 경우
 * 			2-2-1-2. 높이 차이가 날때까지의 부분이 경사로 길이만큼 이어져 있는 경우
 * 			=> 높이 차이가 날떄까지의 부분이 1적으면 경사로 길이만큼 이어져 있어야하고, 1 높으면 그 다음부터 동일한 높이가 경사로 길이만큼 이어져 있어야 함.
 * 		
 *
 */
public class Solution {
	static int T, N, X;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 지도 크기
            X = Integer.parseInt(st.nextToken()); // 경사로 길이
            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            System.out.println("#" + t + " " + process());
        }
    }

    private static int process() {
        int count = 0;

        // 1. 행 검사
        for (int i = 0; i < N; i++) {
            if (canBuild(map[i])) count++;
        }

        // 2. 열 검사 (따로 배열을 만들어서 전달)
        for (int j = 0; j < N; j++) {
            int[] col = new int[N];
            for (int i = 0; i < N; i++) {
                col[i] = map[i][j];
            }
            if (canBuild(col)) count++;
        }

        return count;
    }

    private static boolean canBuild(int[] arr) {
        boolean[] visit = new boolean[N]; // 경사로 설치 여부 체크

        for (int i = 0; i < N - 1; i++) {
            // 1. 높이가 같은 경우 패스
            if (arr[i] == arr[i + 1]) continue;

            // 2. 높이 차이가 1보다 크면 설치 불가
            if (Math.abs(arr[i] - arr[i + 1]) > 1) return false;

            // 3. 내리막 경사로 설치 (현재 > 다음)
            if (arr[i] == arr[i + 1] + 1) {
                for (int j = i + 1; j <= i + X; j++) {
                    // 범위를 벗어나거나, 높이가 일정하지 않거나, 이미 경사로가 있는 경우
                    if (j >= N || arr[j] != arr[i + 1] || visit[j]) return false;
                    visit[j] = true;
                }
            } 
            // 4. 오르막 경사로 설치 (현재 < 다음)
            else if (arr[i] == arr[i + 1] - 1) {
                for (int j = i; j > i - X; j--) {
                    // 범위를 벗어나거나, 높이가 일정하지 않거나, 이미 경사로가 있는 경우
                    if (j < 0 || arr[j] != arr[i] || visit[j]) return false;
                    visit[j] = true;
                }
            }
        }
        return true;
    }

}
