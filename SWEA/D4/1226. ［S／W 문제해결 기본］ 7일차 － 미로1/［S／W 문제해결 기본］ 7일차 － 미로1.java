import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.io.FileInputStream;

/**
 * 
 * @author Baekkwanyeol
 * @see public main() 
 * 1. 총 10개의 테스트 코드가 들어옴 
 * 2. 첫줄에는 코드 번호 
 * 3. 두번째 줄에는 16*16 미로가 들어옴 
 * 		3-1 0 은 통로 1 은 벽 2 는 시작점 3은 도착점
 * 
 * @see def public boolean bfs(int y,int x) 
 * 1. y,x 부터 시작해서 넓이 우선 탐색 진행 
 * 2. 탈출조건 3을 만나면 탈출
 *
 */
public class Solution {
	public static boolean bfs(int s_y, int s_x, int[][] map) {

		Deque<int[]> q = new ArrayDeque<>();
		int[][] visited = new int[16][16];

		q.addLast(new int[] { s_y, s_x });

		while (!q.isEmpty()) {

			int c_y;
			int c_x;
			int n_y;
			int n_x;
			int[] temp;
			temp = q.pollFirst();
			c_y = temp[0];
			c_x = temp[1];

			// 만약 3을 만나면 트루
			if (map[c_y][c_x] == 3) {
				return true;
			}

			

			int[] dy = { -1, 1, 0, 0 };
			int[] dx = { 0, 0, 1, -1 };

			for (int i = 0; i < 4; i++) {
				n_y = c_y + dy[i];
				n_x = c_x + dx[i];

				if (0 <= n_x && n_x < 16 && 0 <= n_y && n_y < 16) {
					if (visited[n_y][n_x] == 0 && map[n_y][n_x] != 1) {
						q.add(new int[] { n_y, n_x });
						visited[n_y][n_x] = 1;

					}
				}

			}

		}

		return false;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for (int test_case = 1; test_case <= 10; test_case++) {
			
			String testcase;
			testcase = sc.next();
			int[] start = { 0, 0 };
			int[] end = { 0, 0 };
			int[][] map = new int[16][16];

			for (int i = 0; i < map.length; i++) {
				char[] line = new char[16];
				line = sc.next().toCharArray();
				for (int j = 0; j < line.length; j++) {
					int number;
					number = Character.getNumericValue(line[j]);
					map[i][j] = number;
					if (number == 2) {
						start[0] = i;
						start[1] = j;
					} else if (number == 3) {
						end[0] = i;
						end[1] = j;
					}

				}

			}
			StringBuffer sb = new StringBuffer();
			
			sb.append("#"+testcase+" ");
			
			sb.append(bfs(start[0],start[1],map)?1:0);
			
			System.out.println(sb.toString());

		}
	}

}
