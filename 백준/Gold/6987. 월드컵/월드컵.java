import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 6987. 월드컵
 * 
 * 1. 반복문을 4번 돌며,
 * 	1-1. 현재 경기 결과를 차레대로 입력받기
 * 	1-2. 하나의 경기 결과를 저장할 gameResult[6][3]; -> 6개의 팀
 * 	1-3. 조합 경우의 수를 미리 다 만들어놓기 15개 뿐이니까
 * 		A팀 : 0
 * 		B팀 : 1
 * 		C팀 : 2
 * 		D팀 : 3
 * 		E팀 : 4
 * 		F팀 : 5
 * 
 * 2. 조합 합수 (경기 승패의 유무를 결정하는 함수)
 * 	2-1. 기저조건1: 15개 경기에 대해 모두 승패를 결정해준 경우
 * 		2-1-1. isEnd를 true로 바꿔주고
 * 		2-1-2. return 해주기
 * 	2-2. 기저조건2: isEnd가 true 이면 바로 return
 * 	2-3. 현재 경기에 나가는 팀에 총 시나리오 3개를 적용
 * 		2-3-1. 이때 가지치기를 활용해 시나리오 적용이 가능한지 미리 탐색
 * 		2-3-2. gameResult에 저장된 값이 0 이상이면 해당 승패무가 가능한 것
 * 		2-3-3. 가능하다면 재귀호출
 * 		2-3-4. 복구해주기
 * 
 *
 */
public class Main {
	
	static int[][] gameResult = new int[6][3];
	static int[][] allTeamCombi = {{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {1,2}, {1,3}, {1,4}, {1,5}, {2,3}, {2,4}, {2,5}, {3,4}, {3,5}, {4,5}};
	static boolean isEnd;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();;
		
		for(int t=0; t<4; t++) {
			st = new StringTokenizer(br.readLine());
			int totalGames = 0;
			
			for(int team=0; team<6; team++) {
				gameResult[team][0] = Integer.parseInt(st.nextToken());
				gameResult[team][1] = Integer.parseInt(st.nextToken());
				gameResult[team][2] = Integer.parseInt(st.nextToken());
				totalGames += (gameResult[team][0] + gameResult[team][1] + gameResult[team][2]);
			}
			
			isEnd = false;
            
			// 미리 탐색해야 하는 조건: 한 팀당 5경기씩 총 30이 아니면 무조건 0 (기초 필터링)
            if (totalGames != 30) {
                sb.append(0).append(" ");
            } else {
                backtracking(0);
                sb.append(isEnd ? 1 : 0).append(" ");
            }
        }
        System.out.println(sb.toString());
			
	}
	
	static void backtracking(int idx) {
		if (isEnd) return; // 이미 가능한 결과임을 찾았다면 조기 종료

        if (idx == 15) { // 15경기를 모두 성공적으로 배정함
        	//System.out.println("15경기를 모두 성공적으로 배정함");
            isEnd = true;
            return;
        }
		
		int team1 = allTeamCombi[idx][0];
		int team2 = allTeamCombi[idx][1];
		
		// [갈래 1] 현재 경기에서 t1이 이기는 시나리오 탐색
		// t1 승 - t2 패
        if (gameResult[team1][0] > 0 && gameResult[team2][2] > 0) {
            gameResult[team1][0]--; gameResult[team2][2]--; // 상태 변경
            backtracking(idx + 1);
            gameResult[team1][0]++; gameResult[team2][2]++; // 상태 복구 (Backtrack)
        }

        // [갈래2] 현재 경기에서 둘다 비기는 시나리오 탐색
        // 2. t1 무 - t2 무
        if (gameResult[team1][1] > 0 && gameResult[team2][1] > 0) {
            gameResult[team1][1]--; gameResult[team2][1]--;
            backtracking(idx + 1);
            gameResult[team1][1]++; gameResult[team2][1]++;
        }

        // [갈래3] 현재 경기에서 t2가 이기는 시나리오 탐색
        // 3. t1 패 - t2 승
        if (gameResult[team1][2] > 0 && gameResult[team2][0] > 0) {
            gameResult[team1][2]--; gameResult[team2][0]--;
            backtracking(idx + 1);
            gameResult[team1][2]++; gameResult[team2][0]++;
        }
	}

}
