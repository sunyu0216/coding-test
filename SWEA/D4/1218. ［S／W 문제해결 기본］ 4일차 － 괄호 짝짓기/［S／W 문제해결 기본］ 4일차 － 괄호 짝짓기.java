import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 1218. 괄호 짝짓기
 * 
 * 1. stack을 만들어서 {, <, (, [ 가 들어오면 push
 * 2. 반대로 }, >, ), ] 가 들어오면 stack에서 pop을 하고 맞는 짝인지 비교
 * 3. 아니라면 바로 0 출력
 * 4. 맞다면 반복 -> 입력 끝까지 맞다면 1 출력
 *
 */
public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int t=1; t<=10; t++) {
	
			int len = Integer.parseInt(br.readLine().trim());
			
			Deque<Character> stack = new ArrayDeque<>();
			int isValid = 1;
			char[] input = br.readLine().toCharArray();
			for(char C: input) {
				if(C=='(' || C== '[' || C=='{' || C=='<') {
					stack.push(C);
				}else {
					if (stack.isEmpty()) { // 스택이 비어있는데 닫는 괄호가 오면 무효
	                    isValid = 0;
	                    break;
	                }
	                
	                char out = stack.pop();
	                
	                // 각 괄호의 짝이 맞지 않는 경우 체크
	                if (C == ')' && out != '(') { isValid = 0; break; }
	                if (C == ']' && out != '[') { isValid = 0; break; }
	                if (C == '}' && out != '{') { isValid = 0; break; }
	                if (C == '>' && out != '<') { isValid = 0; break; }
				}
			}
			// 모든 검사가 끝난 후 스택에 남은 괄호가 있다면 무효
	        if (!stack.isEmpty()) isValid = 0;

	        System.out.println("#" + t + " " + isValid);
		}
		
	}
}
