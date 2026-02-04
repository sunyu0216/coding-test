import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 0. 변수들
 * 		0-1. 규영이의 순서가 고정 된 카드 kyuCards
 * 		0-2. 인영이의 카드 inCards
 * 		0-3. 규영이가 가진 카드 체크 용 isKyuHas
 * 		0-4. 순열 생성 시 중복 방문 방지용 visited
 * 		0-5. 승 카운트, 패 카운트 winCount, loseCount
 * 
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 규영이의 카드 입력받기 와 동시에 isKyuHas에 true로 표시해주기
 * 		1-2. isKyuHas 배열을 보고 인영이가 가진 카드 배열 채우기
 * 
 * @see #permutation(int, int, int)
 * 2. 순열을 통해 인영이가 카드를 내는 모든 경우의 수를 탐색
 * 
 * 		2-1. [기저조건] 만약 9장의 카드를 다 냈다면
 * 			2-1-1. 지금까지 쌓인 규영 점수와 인영 점수를 비교한다.
 * 			2-1-2. 규영이가 더 높으면 winCount++, 더 낮으면 loseCount++ 를 하고 return 해준다. 
 * 		
 * 		2-2. 아니라면 인영이가 가진 9장의 카드 중 이미 냈던 카드를 제외하고 하나를 고른다.
 * 			2-2-1. 낸 카드는 방문배열에 표시해준다.
 * 			2-2-2. 이 카드와 이미 정해진 규영 카드를 통해 이번 라운드 점수를 계산한다.
 * 			2-2-3. 그 다음 계산된 점수로 다시 순열을 재귀 호출한다.
 * 			2-2-4. 이렇게 반복 호출된 순열 함수에서 어느 순간 카드 9장을 다 뽑는다면 기저조건으로 이동하게 되고 순차적으로 return된다.
 * 			2-2-5. return 되면 그 카드를 다시 방문 배열에서 빼준다.
 *
 *
 */
public class Solution {

	static int[] kyuCards; // 규영이 카드 (순서 고정)
    static int[] inCards;  // 인영이 카드 (순열 대상)
    static boolean[] isKyuHas; // 1~18 중 규영이가 가진 카드 체크
    static boolean[] visited;  // 순열 생성 시 방문 체크
    static int winCount, loseCount;
    
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int testcase = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= testcase; t++) {
            kyuCards = new int[9];
            inCards = new int[9];
            isKyuHas = new boolean[19];
            visited = new boolean[9];
            winCount = 0;
            loseCount = 0;

            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; i < 9; i++) {
                kyuCards[i] = Integer.parseInt(st.nextToken());
                isKyuHas[kyuCards[i]] = true; // 규영이가 가진 카드 표시
            }

            // 인영이가 가질 9장의 카드 추출
            int idx = 0;
            for (int i = 1; i <= 18; i++) {
                if (!isKyuHas[i]) {
                    inCards[idx++] = i;
                }
            }
            
            // 순열 시작 (깊이 0, 규영 점수 0, 인영 점수 0)
            permutation(0, 0, 0);

            System.out.println("#" + t + " " + winCount + " " + loseCount);
            
		}
	}
	
	static void permutation(int cnt, int kyuSum, int inSum) {
        // 9장의 카드를 모두 다 냈을 때 (기저 조건)
        if (cnt == 9) {
            if (kyuSum > inSum) winCount++;
            else if (kyuSum < inSum) loseCount++;
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            
            // 이번 라운드의 점수 계산
            int kyuScore = kyuCards[cnt];
            int inScore = inCards[i];
            
            if (kyuScore > inScore) {
                permutation(cnt + 1, kyuSum + kyuScore + inScore, inSum);
            } else {
                permutation(cnt + 1, kyuSum, inSum + kyuScore + inScore);
            }
            
            visited[i] = false; // 백트래킹 (원상 복구)
        }
    }
}
