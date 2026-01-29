import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author seonyu
 * 
 * @see #main(String[])
 * 1. Nth(몇번째인가)를 입력받는다.
 * 
 * 2. 가장 큰 감소하는 수 9,876,543,210의 순서보다 크다면 무조건 -1을 반환
 * 
 * @see #findNth()
 * 3. 감소하는 수가 자리수 별로 몇개 존재하는지 계산하기
 *      3-1. 자리수가 1일 때
 *      3-2. 자리수가 2일 때
 *      3-3. ...
 *      3-4. 자리수가 10일 때
 *      3-5. 각 자리수마다 감소하는 수의 개수를 담은 배열 반환하기
 * 
 * 4. 입력받은 Nth의 자리수를 파악
 * 
 * @see #findNumber(int 자리수)
 * 5. 해당 자리수 내에서 입력받은 순서에 오는 수 찾기
 *      5-1. 큐를 사용해 작은 수부터 넣어두기
 *      5-2. 꺼내서 -> 꺼낸 수보다 더 작은 숫자를 뒤에 붙여두기
 *      5-3. 꺼낸 수가 자리수를 만족한다면 순번 카운트해주기
 *      5-4. 순번이 Nth와 일치해진다면 stop!!
 *      5-5. 해당 수 반환
 * 
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Nth = sc.nextInt();

        int[] digits = findNth();
        //System.out.println(digits[0]);
        if(digits[0] < Nth){
            System.out.println(-1);
        }else if(digits[0] == Nth){
            System.out.println(9876543210L);
        }else{
            long answer = findNumber(Nth);
            System.out.println(answer);
        }
    }

    public static int[] findNth(){
        int[] digits = new int[11];
        int[][] combination = new int[11][11];

        for(int n=1; n<11; n++){
            for(int r=0; r<11; r++){
                if(r==0|n==r){
                    combination[n][r] = 1;
                }else{
                    combination[n][r] = combination[n-1][r-1] + combination[n-1][r];
                }
            }
        }

        int max_th = -1;
        for(int d=1; d<11; d++){
            max_th += combination[10][d];
            digits[d] = combination[10][d];
        }
        digits[0] = max_th;

        return digits;
    }

    public static long findNumber(int th_num){
        Queue<Long> q = new ArrayDeque<>();
        int count = -1;
        
        for(long i=0; i<10; i++){
            q.add(i);
            count++;
            if(count == th_num){
                //System.out.println(i);
                return (long)i;
            }
        }

        while(!q.isEmpty()){
            long curr = q.poll();
            long lastNum = curr%10;

            for(long c=0; c<=lastNum-1; c++){
                long nextNum = curr * 10 + c;
                q.add(nextNum);
                count++;

                if(count == th_num){
                    //System.out.println(nextNum);
                    return nextNum;
                }

            }
        }
        return -1;
    }
}
