import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 1074. Z
 * 
 * 1. 입력받기 및 변수
 * 	1-1. N, 구해야하는 행 r, 열 c 입력받기
 * 	1-2. 전체 맵의 크기 mapNum = 1 << N = 2^N
 * 	1-3. 현재 맵의 크기 currentMapNum
 * 	1-4. 현재 맵의 크기에서 사분면의 경계를 나눠줄 값 standardNum
 * 	1-5. 구해야하는 행과 열의 순번 answer
 * 
 * 2. 전체 N에서 1씩 감소하며 0까지 순회하며
 * 	2-1. 현재 맵의 크기 구하기 mapNum = 1 << N
 * 	2-2. 현재 맵에서 사분면을 나누는 기준 값 구하기 standardNum = (1<<N)/2
 * 		N = 3 -> 전체 맵의 크기는 8*8 이며, 이를 사분면으로 나누는 기준 값은 4가 됨
 * 		N = 2 -> 전체 맵의 크기는 4*4 이며, 이를 사분면으로 나누는 기준 값은 2가 됨
 * 	2-3. 현재 순번에 더해 줄 넓이 크기 area = (1<<N-1) * (1<<N-1)
 * 
 * 	2-4. 구해야하는 행과 열이 이 사분면 중 어느 곳에 속하는지 구하기
 * 		2-4-1. 1사분면이라면, 
 * 			a. 현재 N/2한 값으로 맵의 크기를 구한 뒤 순번에 더해주기 -> answer += area
 * 			b. 행과 열 좌표 변환 (r,c) -> (r,c-standardNum))
 * 
 * 		2-4-2. 2사분면이라면, 변화없음
 * 
 * 		2-4-3. 3사분면이라면,
 * 			a. 현재 N/2한 값으로 맵의 크기를 구한 뒤 *2 한 값을 순번에 더해주기
 * 			b. 행과 열 좌표 변환 (r,c) -> (r-standardNum,c)
 * 
 * 		2-4-4. 4사분면이라면, 
 * 			a. 현재 N/2한 값으로 맵의 크기를 구한 뒤 *3 한 값을 순번에 더해주기
 * 			b. 행과 열 좌표 변환 (r,c) -> (r-standardNum,c-standardNum)
 * 
 * 
 *
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		// 1. 입력받기 및 변수
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 1-1. N, 구해야하는 행 r, 열 c 입력받기
		int N = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		
		int mapNum; int currentMapNum; int standardNum; int area; int answer=0;
		
		
		// 2. 전체 N에서 1씩 감소하며 0까지 순회하며
		while(N > 0) {
			// 2-1. 현재 맵의 크기 구하기 mapNum = 1 << N
			mapNum = 1 << N;
			
			// 2-2. 현재 맵에서 사분면을 나누는 기준 값 구하기 standardNum = (1<<N)/2
			standardNum = (1 << N) / 2;
			
			// 2-3. 현재 순번에 더해 줄 넓이 크기 area = (1<<N-1) * (1<<N-1)
			area = (1<<N-1) * (1<<N-1);
			
			// 2-4. 구해야하는 행과 열이 이 사분면 중 어느 곳에 속하는지 구하기
			if(c>=standardNum && r<standardNum) {
				// 2-4-1. 1사분면이라면,
				answer += area;
				c -= standardNum;
			}else if(r<standardNum && c<standardNum) {
				// 2-4-2. 2사분면이라면, 변화없음
			}else if(c<standardNum && r>=standardNum) {
				// 2-4-3. 3사분면이라면,
				answer += area*2;
				r -= standardNum;
			}else if(r>=standardNum && c>=standardNum) {
				// 2-4-4. 4사분면이라면, 
				answer += area*3;
				r -= standardNum;
				c -= standardNum;
			}
			N--;
			
		}
		
		System.out.println(answer);
		
	}

}
