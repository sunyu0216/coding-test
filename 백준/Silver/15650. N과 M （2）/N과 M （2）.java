import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 15650. N과 M(2)
 * 
 * 1. 입력받기
 * 		1-1. 자연수 범위 Num
 * 		1-2. 길이 len
 * 
 * 2. 중복된 숫자를 뽑을 수 없으며, 완성된 수열의 순서를 고려하지 않는다. = 조합
 * 		2-1. 중복 없이 숫자를 선택하되, 현재 선택한 숫자보다 큰 숫자만 다음으로 선택 가능 (오름차순)
 *      2-2. start 인덱스를 사용하여 이전에 선택한 숫자 이후부터 탐색
 *
 */
public class Main {
	static int Num;
	static int len;
	static int[] result;
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		
		Num = Integer.parseInt(st.nextToken());
		len = Integer.parseInt(st.nextToken());
		
		result = new int[len]; // 결과 담을 배열 초기화
        
        // 0개 뽑은 상태, 시작 숫자 1부터 탐색
        combination(0, 1); 
        System.out.print(sb);
		
	}
	
	/**
     * 조합을 생성하는 재귀 함수
     * @param m     현재까지 뽑은 숫자의 개수
     * @param start 다음 숫자를 뽑기 시작할 시작점
     */
    static void combination(int m, int start) {
        // 기저 조건: 원하는 개수 len만큼 숫자를 다 뽑았을 때
        if (m == len) {
            for (int val : result) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        // start부터 Num까지의 숫자만 탐색 (이전 숫자보다 무조건 큰 숫자를 뽑게끔!)
        for (int n = start; n <= Num; n++) {
            result[m] = n;
            combination(m + 1, n + 1);
        }
    }

}
