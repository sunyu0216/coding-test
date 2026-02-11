import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author seonyu
 * BOJ 2839. 설탕 배달
 * 
 * 1. 배달해야 하는 설탕의 킬로그램 deliveredWeight
 * 
 * 2. deliveredWeight이 0이 될때까지
 * 	2-1. deliveredWeight가 5로 나누어 떨어지면 -> 5로 나누고 break
 * 	2-2. 5로 안나누어 떨어지면 5로 나누어 떨어질 때까지 
 * 		2-2-1. 만약 deliveredWeight가 3보다 작아져 버리면 break
 * 		2-2-2. 3을 빼기
 *
 */
public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int deliveredWeight = Integer.parseInt(br.readLine());
		int totalNum = 0;
		
		// 2. deliveredWeight이 0이 될때까지
		while(deliveredWeight > 0) {
			// 2-1. deliveredWeight가 5로 나누어 떨어지면 -> 5로 나누고 break
			if((deliveredWeight % 5) == 0) {
				totalNum += deliveredWeight / 5;
				break;
			}
			
			// 2-2. 5로 안나누어 떨어지면 5로 나누어 떨어질 때까지 
			if(deliveredWeight-3 < 0) { // 2-2-1. 만약 deliveredWeight가 3보다 작아져 버리면 break
				totalNum = -1;
				break;
			}else { // 2-2-2. 3을 빼기
				deliveredWeight -= 3;
				totalNum++;
			}
		}
		
		System.out.println(totalNum);
	}

}
