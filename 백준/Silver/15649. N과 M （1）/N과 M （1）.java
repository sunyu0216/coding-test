import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int M;
	static boolean[] visited;
	static int[] result;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		visited = new boolean[N+1];
		result = new int[M]; // M개를 뽑을 거니까 크기는 M
		
		permutation(0); // 0개 뽑은 상태에서 시작
        System.out.println(sb);
		
	}
	
	static void permutation(int m) {
		if(m==M) {
			for (int val : result) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
		}
		for(int n=1; n<=N; n++) {
			if(visited[n]) continue;
			
			visited[n] = true;
			result[m] = n;
					
			permutation(m+1);
			
			visited[n] = false;
		}
	}
	
}
