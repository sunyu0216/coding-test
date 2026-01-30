import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * 
 * @see #main(String[])
 * 1. 탑의 개수인 N 입력받기, 탐들의 높이를 currentHeight로 차례대로 받기
 * 
 * 2. 스택을 활용해 차례대로 크기를 비교
 *      @see #while(!isEmpty())
 *      2-1. 현재의 높이보다 스택에서 꺼낸 높이가 낮다면 신호를 받을 수 없다고 판단 -> 스택에서 삭제 후 다시 비교
 *      2-2. 현재의 높이보다 스택에서 꺼낸 높이가 같거나 높다면 신호를 받을 수 있다고 판단 -> 정답 출력문에 해당 탑의 인덱스 추가
 *      2-3. 스택에 아무것도 없다면 신호를 받을 수 있는 탑이 없으므로 0을 출력문에 추가
 *      2-4. 마지막엔 무조건 {현재 탑의 인덱스, 현재 탑의 높이} 추가
 * 
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Deque<int[]> stack = new ArrayDeque<>();

        int N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine()); 
        
        StringBuilder sb = new StringBuilder();

        int currentHeight = 0;
        for (int i = 1; i <= N; i++) {
            currentHeight = Integer.parseInt(st.nextToken());

            while(!stack.isEmpty()){
                int leftTowerHeight = stack.peek()[1];

                if(leftTowerHeight >= currentHeight){ // 왼쪽 탑의 높이가 현재 탑의 높이보다 같거나 높을 때
                    // -> 그대로 쌓기
                    sb.append(stack.peek()[0]).append(" ");
                    break;
                }else{
                    // -> 왼쪽 탑 삭제시키고 다시 비교
                    stack.pop();
                }

            }

            if(stack.isEmpty()){
                // 아무것도 없다면 신호를 받을 수 없다는 것이므로 0을 출력
                sb.append(0).append(" ");
            }

            stack.push(new int[] {i, currentHeight});
        }

        System.out.println(sb.toString().trim());

    }
}
