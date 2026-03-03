import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.management.Query;

/**
 * 
 * @author seonyu
 * 
 * 틀린이유: 각 행, 열마다 마지막에 저장된 빈칸을 체크해줬어야 함... 안쪽 for문 다 돌고 나서 체크를 한번 더 해주기 
 *
 */
public class Solution {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int groundSize = Integer.parseInt(st.nextToken());
			int rootSize = Integer.parseInt(st.nextToken());
			
			int[][] ground = new int[groundSize][groundSize];
			
			for(int i=0; i<groundSize; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<groundSize; j++) {
					ground[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			
			// 가로방향으로 심어보기
			boolean flag = false;
			int count1 = 0;
			
			for(int r=0; r<groundSize; r++) {
				int tempCount = 0; // 초기화
				flag = false;
				for(int c=0; c<groundSize; c++) {
					if(ground[r][c] == 1) {
						flag = true;
						tempCount++;
						//System.out.println("현재 토양의 구간: "+ tempCount);
					}else if(ground[r][c] == 0 && flag) {
						//System.out.println("돌을 만남 ");
						if(tempCount == rootSize) {
							//System.out.println("지금까지 필요한 구간과 딱 맞음 ");
							count1++;
						}
						flag = false;
						tempCount = 0;
					}
				}
				if(tempCount == rootSize) {
					count1++;
				}
			}
			//System.out.println("가로방향으로 심었을때 가능 한 구역개수 " + count);
			
			
			// 세로방향으로 심어보기
			flag = false;
			int count2 = 0;
			
			for(int c=0; c<groundSize; c++) {
				int tempCount = 0; // 초기화
				flag = false;
				for(int r=0; r<groundSize; r++) {
					if(ground[r][c] == 1) {
						flag = true;
						tempCount++;
						//System.out.println("현재 토양의 구간: "+ tempCount);
					}else if(ground[r][c] == 0 && flag) {
						//System.out.println("돌을 만남 ");
						if(tempCount == rootSize) {
							//System.out.println("지금까지 필요한 구간과 딱 맞음 ");
							count2++;
						}
						flag = false;
						tempCount = 0;
					}
				}
				if(tempCount == rootSize) {
					count2++;
				}
			}
			//System.out.println("세로방향으로 심었을때 가능 한 구역개수 " + count2);
			
			int answer = count1+count2;
			
			System.out.println("#"+t+" "+answer);

		}
	}

}
