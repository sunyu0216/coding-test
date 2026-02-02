import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * @see #main(String[])
 * 
 * 1. 테스트케이스 개수 입력받기
 * 	1-1. 파리가 있는 영역의 크기 flyMapSize, 파리채의 크기 flapperSize 입력받기
 * 	1-2. 파리정보 입력받기 - 누적합을 활용
 * 
 * 		<2차원 누적합(2D Prefix Sum)>
 * 		- 배열의 시작점(0,0)부터 현재 좌표(i,j)까지의 모든 원소 합을 미리 구해놓은 것.
 * 		- 누적합 공식: S[i][j] = S[i-1][j] + S[i][j-1] - S[i-1][j-1] + A[i][j]
 * 
 * 2. 잡을 수 있는 파리의 MAX값 구하기 
 * 	2-1. 파리채 크기에 맞는 구간 합을 누적합 배열에서 구하기
 * 
 * 		<구간합 구하기>
 * 		- 시작점부터가 아니라 원하는 위치부터 현재 좌표까지의 합을 구하는 것.
 * 		- (x1, y1)부터 (x2, y2)까지의 합: Sum = S[x2][y2] - S[x1-1][y2] - S[x2][y1-1] + S[x1-1][y1-1]
 * 
 *
 */
public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int testCase = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=testCase; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			int flyMapSize = Integer.parseInt(st.nextToken());
			int flapperSize = Integer.parseInt(st.nextToken());
			int[][] flyMap = new int[flyMapSize+1][flyMapSize+1];
			
			// 1. 파리정보 입력받기
			for(int i=1; i<=flyMapSize; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=1; j<=flyMapSize; j++) {
					int flies = Integer.parseInt(st.nextToken());
					// 누적합 공식
					flyMap[i][j] = flyMap[i-1][j] + flyMap[i][j-1] - flyMap[i-1][j-1] + flies;
				}
			}
			
			// 2. 파리채를 반복하며 잡을 수 있는 파리의 MAX 값 구하기
			int MaxFlies = 0;
			
			for(int i=1; i<=flyMapSize-flapperSize+1; i++) {
				for(int j=1; j<=flyMapSize-flapperSize+1; j++) {
					// 파리채의 오른쪽 하단 좌표
					int x = i+flapperSize-1;
					int y = j+flapperSize-1;
					
					int currentFlies=flyMap[x][y]-flyMap[i-1][y]-flyMap[x][j-1]+flyMap[i-1][j-1];
					
					MaxFlies = Math.max(currentFlies, MaxFlies);
				}
			}
			
			System.out.println("#"+tc+" "+MaxFlies);
			
		}
	}
}
