import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 12869. 뮤탈리스크
 * 
 * 1. 입력받기
 * 	1-1. SCV의 수 SCVNum
 * 	1-2. SCV의 체력 정보 SCVHealth[]
 * 
 * SCV 체력 순으로 정렬 -> 가장 큰 값에 매번 9를 잃게 하도록 하기
 * ㄴ 어 안됨.. 1, 3, 9 를 맞추는게 좋나?
 * 
 * 2. 뮤탈리스크가 줄 수 있는 데미지의 모든 경우의 수 = 6가지 
 * 		= {9, 3, 1}, {9, 1, 3}, {3, 9, 1}, {3, 1, 9}, {1, 9, 3}, {1, 3, 9}
 * 
 * 3. BFS(체력1, 체력2, 체력3)
 * 	3-1. 큐에 매개변수로 들어온 체력들과 카운트 수 0 추가 & 방문 처리
 * 	3-2. 큐가 비어있지 않을 때까지, 큐값을 꺼내기
 * 		3-2-1. 기저조건: 현재 체력이 모두 0이면 카운트 수 리턴
 * 		3-2-2. 6가지의 공격을 순회하며
 * 			3-2-2-1. 해당 공격에 맞게 체력을 감소시키기
 * 			3-2-2-2. 이 체력 조합이 방문하지 않았다면 큐에 추가
 * 
 *
 */
public class Main {
    // 뮤탈리스크가 줄 수 있는 데미지의 6가지 경우의 수를 다 정의
    static int[][] damages = {
        {9, 3, 1}, {9, 1, 3}, {3, 9, 1}, {3, 1, 9}, {1, 9, 3}, {1, 3, 9}
    };
    static boolean[][][] visited = new boolean[61][61][61];

    public static void main(String[] args) throws NumberFormatException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int scvNum = Integer.parseInt(br.readLine());
    	
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
        int[] scv = new int[3]; // 무조건 3으로!!
        
        for (int i = 0; i < scvNum; i++) {
            scv[i] = Integer.parseInt(st.nextToken());
        }
        
        System.out.println(bfs(scv[0], scv[1], scv[2]));
    }

    public static int bfs(int h1, int h2, int h3) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{h1, h2, h3, 0}); // {hp1, hp2, hp3, count}
        visited[h1][h2][h3] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int c1 = current[0];
            int c2 = current[1];
            int c3 = current[2];
            int cnt = current[3];

            // 1. 기저 조건: 모든 SCV의 체력이 0이 되면 현재 공격 횟수 리턴
            if (c1 == 0 && c2 == 0 && c3 == 0) return cnt;

            // 2. 6가지 공격 조합 시도
            for (int[] d : damages) {
                // 체력은 0보다 작아질 수 없으므로 Math.max 처리
                int n1 = Math.max(0, c1 - d[0]);
                int n2 = Math.max(0, c2 - d[1]);
                int n3 = Math.max(0, c3 - d[2]);

                // 3. 아직 방문하지 않은 체력 상태라면 큐에 삽입
                if (!visited[n1][n2][n3]) {
                    visited[n1][n2][n3] = true;
                    queue.add(new int[]{n1, n2, n3, cnt + 1});
                }
            }
        }
        return -1;
    }
}