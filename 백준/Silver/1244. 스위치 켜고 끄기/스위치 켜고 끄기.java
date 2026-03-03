import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 1. 입력받기
 * 	1-1. LED 개수 입력받기
 * 	1-2. LED 상태 입력받기 int[]
 * 	1-3. 학생 수 입력받기 studentNum
 * 	1-4. 학생 성별과 그에 따른 LED 조작 입력받기
 * 
 * 
 * 2. 학생이 남학생일때 LED 조작하기
 * 	2-1. 학생이 받은 수를 기준으로 배수들을 찾아서 순회하기
 * 	2-2. 배수인 곳의 LED를 바꾸기
 * 
 * 
 * 3. 학생이 여학생일 때 LED 조작하기
 * 	3-1. 학생이 받은 수를 기준으로-n, +n 한 값이 대칭인지 보기
 *
 */
public class Main {
	static int LEDNum;
	static int[] LEDstatus;
	static int studentNum;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		LEDNum = Integer.parseInt(br.readLine());
		LEDstatus = new int[LEDNum];
		
		st = new StringTokenizer(br.readLine());
		for(int l=0; l<LEDNum; l++) {
			LEDstatus[l] = Integer.parseInt(st.nextToken());
		}
		
		studentNum = Integer.parseInt(br.readLine());
		
		for(int s=0; s<studentNum; s++) {
			st = new StringTokenizer(br.readLine());
			int stNum = Integer.parseInt(st.nextToken());
			int LEDid = Integer.parseInt(st.nextToken());
			
			if(stNum == 1) {
				// 남학생
				manStudent(LEDid);
			}else {
				// 여학생
				womanStudent(LEDid);
			}
		}
		
		
		int a = LEDNum/20;
		int b = LEDNum%20;
		
		for(int r=0; r<LEDNum/20; r++) {
			
			for(int c=r*20; c<r*20+20; c++) {
				System.out.print(LEDstatus[c]+" ");
			}
			System.out.println();
		}
		for(int remain=0; remain<b; remain++) {
			int idx = remain+LEDNum/20*20;
			System.out.print(LEDstatus[idx]+" ");
		}
		
	}
	
	public static void manStudent(int LEDid) {
		for(int i=LEDid; i<=LEDNum; i+=LEDid) {
			int value = LEDstatus[i-1];
			if(value == 0) {
				LEDstatus[i-1] = 1;
			}else {
				LEDstatus[i-1] = 0;
			}
		}
	}
	
	public static void womanStudent(int LEDid) {
		int left = LEDid-1;
		int right = LEDid-1;
		
		while(true) {
			if(left-1 >= 0 && right+1 < LEDNum) {
				if(LEDstatus[left-1] == LEDstatus[right+1]) {
					left = left -1;
					right = right +1;
				}else {
					//System.out.println("더이상 대칭이 아님");
					break;
				}
			}else {
				//System.out.println("범위를 벗어남");
				break;
			}		
		}
		
		for(int i=left; i<=right; i++) {
			int value = LEDstatus[i];
			if(value == 0) {
				LEDstatus[i] = 1;
			}else {
				LEDstatus[i] = 0;
			}
		}
	}

}
