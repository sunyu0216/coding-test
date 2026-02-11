import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.sound.midi.Soundbank;

/**
 * 
 * @author seonyu
 * BOJ 1931. 회의실 배정
 * 
 * 1. 입력받기
 * 	1-1. 회의의 개수 meetingNum
 * 	1-2. 회의의 시작, 끝 정보 meetingTimes[]
 * 
 * 2. meetingTimes를 끝나는 시간, 시작하는 시간 순으로 오름차순 시키기
 * 
 * 3. meetingTimes를 끝까지 순회하며,
 * 	3-1. 회의를 하나씩 꺼내 시작 시간을 이전 회의의 끝나는 시간과 비교
 * 		3-1-1. 이전 회의 끝나는 시간이 시작 시간보다 더 크다면 -> continue
 * 		3-1-2. 시작시간보다 끝나는 시간이 같거나 크다면 -> 회의개수 추가해주고, 이전 회의 끝나는 시간 업데이트
 *
 */

public class Main {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int meetingNum = Integer.parseInt(br.readLine());
		int[][] meetingTimes = new int[meetingNum][2];
		
		for(int m=0; m<meetingNum; m++) {
			st = new StringTokenizer(br.readLine());
			meetingTimes[m][0] = Integer.parseInt(st.nextToken());
			meetingTimes[m][1] = Integer.parseInt(st.nextToken());
		}
		
		// 2. meetingTimes를 끝나는 시간, 시작하는 시간 순으로 오름차순 시키기
		Arrays.sort(meetingTimes, (a, b) -> {
			if(a[1] == b[1]) {
				return Integer.compare(a[0], b[0]);
			}
			return Integer.compare(a[1], b[1]);
		});
		
		// 초기값 설정
		int prevEnd = 0; // 회의는 0시 이후에 시작하므로
		int totalMeetingNum = 0;
		
		
		// 3. meetingTimes를 끝까지 순회하며,
		for(int[] meetingInfo: meetingTimes) {
			// 3-1. 회의를 하나씩 꺼내 시작 시간을 이전 회의의 끝나는 시간과 비교
			int start = meetingInfo[0];
			int end = meetingInfo[1];
			
			if(start < prevEnd) { // 3-1-1. 이전 회의 끝나는 시간이 시작 시간보다 더 크다면 -> continue
				continue;
			}else { // 3-1-2. 시작시간보다 끝나는 시간이 같거나 크다면 -> 회의개수 추가해주고, 이전 회의 끝나는 시간 업데이트
				totalMeetingNum++;
				prevEnd = end;
			}
		}
		
		System.out.println(totalMeetingNum);
	}

}
