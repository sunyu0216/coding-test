import java.math.BigInteger;
import java.util.Scanner;

/**
 * 
 * @author seonyu
 *
 * 입력
 * 1. 테스트케이스 입력받기 - testcase
 * 2. 두개의 양의 정수 입력받기 - A, B   
 * 	  *10의 100승까지 있음 = 19자리 이상 => long도 안되고 BigInteger 사용해야함!!
 * 
 * 출력
 * BigInteger.add() 를 활용해 더한 값 출력
 * 
 */
public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int testcase = sc.nextInt();
		
		for(int tc=1; tc<=testcase; tc++) {

			BigInteger A = sc.nextBigInteger();
			BigInteger B = sc.nextBigInteger();
			
			BigInteger answer = A.add(B);
			
			System.out.println("#"+tc+" "+answer);
		}
		
	}

}
