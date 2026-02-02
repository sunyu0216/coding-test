import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * 		1-3. StringTokenizer 로 공백기준으로 쪼개서 password에 저장하기
 * 
 * 2. 명령어 수행하기
 * 		2-1. StringTokenizer로 받아서 .hasMoreTokens를 사용
 * 		2-2. I가 나올 때만 password 수정
 * 		2-3. D가 나오면 삭제! 
 * 
 * 3. .subList로 맨 앞 10개 자르기
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
			
			
			// 2. 명령어 수행하기
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
				}else if(cmd.equals("D")) {
					int deletePoint = Integer.parseInt(st.nextToken());
					int deleteNum = Integer.parseInt(st.nextToken());
					//System.out.println("현재 포인트: "+changePoint+" 현재 바뀌어야하는 개수: "+ changeNum);
					
					for(int c=0; c<deleteNum; c++) {
						password.remove(deletePoint); // 지우면 한칸씩 땡겨짐
					}
				}
			}
			
			List<Integer> answer = password.subList(0, 10);
			System.out.print("#"+testcase+" ");
			for(int i=0; i<10; i++) {
				System.out.print(answer.get(i)+" ");
			}
			System.out.println("\n");
		}
		
	}
}
