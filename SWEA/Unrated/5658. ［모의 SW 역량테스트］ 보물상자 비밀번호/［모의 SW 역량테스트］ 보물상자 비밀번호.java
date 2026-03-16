import java.util.*;
import java.io.*;

/**
 * @author seonyu
 * SWEA 5658. 보물상자 비밀번호
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T
 * 	1-2. 숫자의 개수 N, 크기 순서 K
 * 	1-3. N개의 16진수 문자열 str
 *
 * 2. 비밀번호 생성 로직
 * 	2-1. 한 변의 길이(len) 계산: N / 4
 * 	2-2. 중복 제거 및 내림차순 정렬을 위한 TreeSet<Long> 생성 (Collections.reverseOrder())
 * 	2-3. 회전 시뮬레이션 (총 len번 회전하면 처음과 같아짐)
 * 		2-3-1. 각 회전마다 문자열을 len 길이만큼 4등분하여 자르기
 * 		2-3-2. 자른 16진수 문자열을 10진수(Long)로 변환하여 TreeSet에 삽입
 * 		2-3-3. 문자열을 한 칸씩 회전 (맨 뒷글자를 앞으로 가져오기)
 *
 * 3. K번째 수 찾기
 * 	3-1. TreeSet의 요소를 순회하며 K번째로 큰 수를 추출
 * 
 * 4. 결과 출력
 * 	4-1. 각 테스트케이스마다 #(TC)와 함께 결과값 출력
 */
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            String str = br.readLine();

            int len = N / 4; // 한 변의 길이
            
            // 내림차순 정렬 + 중복 제거를 자동으로 해주는 TreeSet
            TreeSet<Long> set = new TreeSet<>(Collections.reverseOrder());

            // 회전은 len번만 하면 모든 경우의 수가 다 나옴
            for (int r = 0; r < len; r++) {
                // 1. 네 변의 숫자를 추출
                for (int i = 0; i < 4; i++) {
                    String hex = str.substring(i * len, (i + 1) * len);
                    set.add(Long.parseLong(hex, 16)); // 16진수 -> 10진수 변환 후 삽입
                }
                
                // 2. 문자열 회전 (맨 뒤 문자를 앞으로 보내기)
                str = str.charAt(N - 1) + str.substring(0, N - 1);
            }

            // 3. K번째 숫자 뽑기
            int count = 0;
            long result = 0;
            for (long num : set) {
                count++;
                if (count == K) {
                    result = num;
                    break;
                }
            }

            System.out.println("#" + t + " " + result);
        }
    }
}