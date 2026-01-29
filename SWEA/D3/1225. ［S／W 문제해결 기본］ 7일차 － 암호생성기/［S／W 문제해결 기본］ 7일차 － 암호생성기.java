import java.util.Scanner;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 
 * @author seonyu
 *
 * 입력
 * 반복문 10번 -> 
 * 			테스트케이스번호-testcase, 
 * 			8개의 데이터-ArrayList<> password
 * 
 * 반복문1 {이번에 뒤로가는 숫자가 0이 되거나 0보다 작아질 경우 프로그램 종료
 * 	     뒤로가는 숫자-lastNumber
 *     반복문2 {사이클 담당 반복문, 총 5번 반복}
 * }
 * 
 * 출력
 * 공백을 기준으로 출력하기 위해 stream 활용 -> int Arraylist를 String으로 변환
 * stream().map(숫자를 문자열로 변환).collect(중간에 공백을 넣어 하나의 문자여롤 합치기)
 */

class Solution
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		for(int t=0; t<10; t++) {
			
			int testcase = sc.nextInt();
			ArrayList<Integer> password = new ArrayList<>();
			
			for(int p=0; p<8; p++) {
				password.add(sc.nextInt());
			}
			
			int lastNumber = password.get(7);
			while (lastNumber > 0) {
				for(int cycle=1; cycle<=5; cycle++) {
					lastNumber = password.get(0) - cycle;
					password.remove(0);
					if(lastNumber<=0) {
						password.add(0);
						break;
					}else {
						password.add(lastNumber);
					}
				}
			}
			String resultList = password.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(" "));
			
			System.out.println("#"+testcase+" "+resultList);
		}
	}
}