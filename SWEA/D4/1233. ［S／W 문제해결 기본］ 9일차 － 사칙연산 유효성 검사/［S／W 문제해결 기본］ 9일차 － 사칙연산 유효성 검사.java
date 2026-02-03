import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 1. 입력받기
 * 		1-1. 트리가 갖는 정점의 수 nodeNum
 * 		1-2. 정점 정보 한 줄씩 읽기
 * 
 * 2. StringTokenizer 기준으로 토큰이 4개라면 = 자식노드가 있는 정점
 * 							토큰이 2개라면 = 리프노드
 * 
 * 3. 자식노드가 있는데 두번재 토큰이 숫자 | 리프노드인데 두번째 토큰이 연산자 이면 0 출력
 * 4. 아니라면 1 출력
 *
 */
public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		for(int testcase=1; testcase<=10; testcase++) {
			int nodeNum = Integer.parseInt(br.readLine());
			int answer = 1;
			
			for(int n=0; n<nodeNum; n++) {
				st = new StringTokenizer(br.readLine());
				
				if(st.countTokens()==4) {
					//System.out.println("토큰이 4개임");
					st.nextToken();
					if(Character.isDigit(st.nextToken().charAt(0))) {
						answer = 0;
					}
				}else {
					//System.out.println("토큰이 2개임");
					st.nextToken();
					if(!Character.isDigit(st.nextToken().charAt(0))) {
						answer = 0;
					}
				}
			}
			System.out.println("#"+testcase+ " "+answer);
		}
	}
}
