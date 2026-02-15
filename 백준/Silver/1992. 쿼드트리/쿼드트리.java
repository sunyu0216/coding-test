import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author seonyu
 * BOJ 1992. 쿼드트리
 *
 * 1. 입력받기
 *  1-1. 영상의 크기 입력받기 vedioSize
 *  1-2. 영상의 점들 입력받기 vedio[][]
 *
 *
 * @see #compress(int, int, int) 
 * 2. 재귀함수
 *  2-1. 기준값을 통해 현재 영역(half * half)이 모두 0인지 1인지 확인하기
 *      2-1-1. 모두 0 또는 1이라면 그 숫자를 출력하고 return
 *      2-1-2. 하나라도 다르다면,
 *          a. 괄호를 열기
 *          b. 영역을 4개로 쪼개서 재귀 호출하기
 *          c. 괄호 닫기
 *
 * 
 * @see #isCheck(int, int, int) 
 * 3. 영역 내 모든 원소가 일치하는지 확인하는 함수
 *  3-1. 전달받은 시작 위치로부터 기준값까지 반복문을 돌면서 확인
 *  3-2. 모두 일치한다면 true, 아니라면 false를 반환
 *
 */
public class Main {
    static int videoSize;
    static int[][] video;
    static BufferedReader br;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        videoSize = Integer.parseInt(br.readLine());
        video = new int[videoSize][videoSize];


        for (int i=0; i<videoSize; i++){
            char[] value = br.readLine().toCharArray();
            for(int j=0; j<videoSize; j++){
                video[i][j] = value[j] - '0';
            }
        }

        sb = new StringBuilder();
        compress(0, 0, videoSize);
        System.out.println(sb);

    }

    // 2. 재귀함수
    static void compress(int r, int c, int currentSize){
        // 2-1. 기준값을 통해 현재 영역(half * half)이 모두 0인지 1인지 확인하기
        if(isCheck(r, c, currentSize)){
            // 2-1-1. 모두 0 또는 1이라면 그 숫자를 출력하고 return
            sb.append(video[r][c]);
            return;
        }else{
            // 2-1-2. 하나라도 다르다면,
            // a. 괄호를 열기
            sb.append("(");

            // b. 영역을 4개로 쪼개서 재귀 호출하기
            int half = currentSize/2;
            compress(r, c, half); // 왼쪽상단
            compress(r, c+half, half); // 오른쪽 상단
            compress(r+half, c, half); // 왼쪽 하단
            compress(r+half, c+half, half); // 오른쪽 하단

            // c. 괄호 닫기
            sb.append(")");
        }
    }


    // 3. 영역 내 모든 원소가 일치하는지 확인하는 함수
    static boolean isCheck(int r, int c, int currentSize){
        // 3-1. 전달받은 시작 위치로부터 기준값까지 반복문을 돌면서 확인
        int value = video[r][c]; // 첫 번째 칸의 값을 기준으로 삼음

        // 3-2. 모두 일치한다면 true, 아니라면 false를 반환
        for(int i=r; i<r+currentSize; i++){
            for(int j=c; j<c+currentSize; j++){
                if(video[i][j] != value){
                    return false;
                }
            }
        }
        return true;
    }
}
