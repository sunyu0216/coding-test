import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 30408. 춘배가 선물하는 특별한 하트
 * 
 * [흐름]
 * 10의 18승이나 되는 범위가 주어졌으므로... 
 * 
 * 1부터 거꾸로 더해가기 + 메모이제이션
 * 	1 <- A가 짝수라면 2였을 것이고, A가 홀수라면 3이였을 것
 * 	2 <- A가 짝수라면 4였을 것이고, A가 홀수라면 5 or 3이었을 것
 * 	3 <- A가 짝수라면 6이었을 것이고, A가 홀수라면 7 or 5였을 것
 * 	4 <- A가 짝수라면 8이었을 것이고, A가 홀수라면 9 or 7이었을 것
 * 	...
 * 	N <- A가 짝수라면 N*2였을 것이고, A가 홀수라면 N*2+1 or N*2-1이었을 것
 * N이 이미 주어졌는데...? 굳이...
 * 
 * ---------------------------------------------------------------
 * 반대로 해보면
 * 	1 -> 0, 1
 * 	2 -> 1
 * 	3 -> 1, 2
 * 	4 -> 2
 * 	...
 * 	N -> N/2
 * 	N+1 -> N/2, N/2+1
 * 
 * N이 홀수일 때와 짝수일 때를 구분해서 재귀 호출
 * 
 */
public class Main {
	static long N;
	static long M;
	static Set<Long> visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Long.parseLong(st.nextToken());
		M = Long.parseLong(st.nextToken());
		
		visited = new HashSet<>();
		
		if(solve(N)) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}
	
	public static boolean solve(long current) {
		
		if(visited.contains(current)) {
			// 방문했던 숫자라면 pass
			return false;
		}else {
			visited.add(current);
		}
		
		if(current == M) {
			// 현재 나눠진 수가 M과 같다면
			return true;
		}
		
		if(current < M) {
			// 현재 나눠진 수가 M보다 작아져버렸다면
			return false;
		}
		
		long half1 = current/2;
		long half2 = current - half1; // 짝수라면 둘다 같은 값이 되고, 홀수라면 하나는 N-1/2 하나는 N-1/2 +1의 값이 된다.
		
		return solve(half1) || solve(half2);
	}

}
