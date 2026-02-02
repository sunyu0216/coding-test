import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * @see #main(String[])
 * 1. 원본 암호문 입력받기
 * 		1-1. int len으로 암호문 길이 입력받기
 * 		1-2. LinkedList<> password
 * 		1-3. StringTokenizer 로 저장하기
 * 2. 명령어 저장하기
 * 		2-1. 배열리스트 commands 생성
 * 
 * 3. commands 배열을 하나씩 돌면서 암호문 수정하기
 *
 */
public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int testcase=1; testcase<=10; testcase++) {
			// 1. 원본 암호문 입력받기
			int len = Integer.parseInt(br.readLine());
			List<Integer> password = new LinkedList<>();
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for(int l=0; l<len; l++) {
				password.add(Integer.parseInt(st.nextToken()));
			}
			
			
			// 2. 명령어대로 삽입하기
			int commandLen = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			
			while(st.hasMoreTokens()) {
				String cmd = st.nextToken(); // "I"를 읽음
				if(cmd.equals("I")) {
					int changePoint = Integer.parseInt(st.nextToken());
					int changeNum = Integer.parseInt(st.nextToken());
					//System.out.println("현재 포인트: "+changePoint+" 현재 바뀌어야하는 개수: "+ changeNum);
					
					for(int c=0; c<changeNum; c++) {
						password.add(changePoint+c, Integer.parseInt(st.nextToken()));
					}
				}
			}
			
			List<Integer> answer = password.subList(0, 11);
			System.out.print("#"+testcase+" ");
			for(int i=0; i<10; i++) {
				System.out.print(answer.get(i)+" ");
			}
			System.out.println("\n");
		}
		
	}
	
}
