import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 
 * @author seonyu
 * 
 * 1. List의 add, remove 메서드를 사용하다보면 index를 찾느라 시간초과가 발생
 * -> 삭제하거나 추가할 때 시간초과가 나지 않게 해야함 = 인덱스를 찾을 필요 없이 만들어야 함 => 커서의 위치를 기준으로 스택을 2개 사용하자!!
 * 
 * 2. leftCursor는 커서의 왼쪽에 있는 문자를 담고, rightCursor는 커서의 오른쪽에 있는 문자를 담는다.
 * 		2-1. pop() 하기전에는 항상 비어있는지 검사
 * 		2-2. push()와 pop()은 시간복잡도가 O(1)
 * 
 * 3. 출력도 StringBuilder를 써 시간 줄이기
 *
 */

public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int testcase = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=testcase; tc++) {
			char[] allInput = br.readLine().toCharArray();
			
			Deque<Character> leftCursor= new ArrayDeque<>();
			Deque<Character> rightCursor = new ArrayDeque<>();
			
			for(char c: allInput) {
				if(c == '<') {
					if(!leftCursor.isEmpty()) rightCursor.push(leftCursor.pop());
					
				}else if(c == '>') {
					if(!rightCursor.isEmpty()) leftCursor.push(rightCursor.pop());
					
				}else if(c == '-') {
					if(!leftCursor.isEmpty()) leftCursor.pop();
					
				}else {
					leftCursor.push(c);
				}
			}
			
			// 결과 출력 최적화
            StringBuilder sb = new StringBuilder();
            
            while (!leftCursor.isEmpty()) {
                rightCursor.push(leftCursor.pop());
            }
            // 이제 right 스택에 결과가 역순으로 담겨있으므로 하나씩 pop
            while (!rightCursor.isEmpty()) {
                sb.append(rightCursor.pop());
            }
            System.out.println(sb.toString());
			
		}
	}
}
