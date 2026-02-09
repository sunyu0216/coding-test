import java.io.*;
import java.util.*;

/**
 * 
 * @author seonyu
 * BOJ 3078. 좋은 친구
 * 
 * 1. 입력받기
 * 	1-1. 학생 수 studentNum, 성적 차이 gradeDiffer
 * 	1-2. 학생 이름을 입력받으면서 길이대로 큐에 넣어주기 위해 queues[가능한 이름길이 21만큼] 생성,
 * 	1-3. 그리고, 하나의 queues 안에 ArrayDeque을 생성해 이름 길이대로 이름을 저장해주기
 * 	1-4. 좋은 친구의 쌍 개수를 저장할 totalPairs
 * 
 * 2. 학생이름 길이를 입력받으며 순회,
 * 	2-1. 만약 입력받은 학생 이름의 길이와 동일한 학생의 등수가 gradeDiffer 이하로 차이난다면 큐에서 제거
 * 	2-2. 차이가 안난다면 좋은 친구 조건을 만족하므로 totalParis 증가
 * 	2-3. 차이가 안난다면 또한 등수를 큐에 추가해서 다음에 올 학생과도 비교해주기
 *
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int studentNum = Integer.parseInt(st.nextToken());
        int gradeDiffer = Integer.parseInt(st.nextToken());

        Queue<Integer>[] queues = new Queue[21];
        for (int i = 0; i < 21; i++) {
            queues[i] = new ArrayDeque<>();
        }

        long totalPairs = 0; // 정답의 범위가 약 450억개까지 가능이므로

        for (int i = 0; i < studentNum; i++) {
            int len = br.readLine().trim().length();

            // 현재 이름 길이의 큐에서 등수 차이가 K를 초과하는 학생은 제거
            while (!queues[len].isEmpty() && i - queues[len].peek() > gradeDiffer) {
                queues[len].poll();
            }

            // 큐에 남아있는 학생들은 모두 '좋은 친구' 조건을 만족함
            totalPairs += queues[len].size();

            // 현재 학생의 등수를 큐에 추가
            queues[len].add(i);
        }

        System.out.println(totalPairs);
    }
}