import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 1765. 닭싸움 팀 정하기
 * 
 * 1. 입력받기
 * 	1-1. 학생의 수 studentNum
 * 	1-2. 인간관계 수 relationNum
 * 	1-3. 인간관계 정보 입력받기 -> 친구관계 배열 friends[] 원수관계 배열 fight[]
 * 
 * 2. 유니온 파인드 함수 정의
 * 	2-1. 친구(팀) 찾기 함수
 * 	2-2. 팀 합치기 함수
 * 
 * 3. 인간관계 정보를 입력받으면서,
 * 	3-1. 친구관계라면 바로 팀으로 만들어주기
 * 	3-2. 원소 관계라면 원수 관계 배열에 서로 입력해주기
 * 		3-2-1. 이때 이미 누군가가 상대방의 적대 배열에 있다면, 그 사람과 친구가 되어야 하므로 
 * 				-> 그 누군가와 팀 맺기 & 
 * 		3-2-2. 없다면 걍 입력
 *
 */
public class Main {
	static int studentNum;
	static int relationNum;
	static int[] friends;
	static int[] fight;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		studentNum = Integer.parseInt(br.readLine());
		relationNum = Integer.parseInt(br.readLine());
		friends = new int[studentNum+1];
		fight = new int[studentNum+1];
		
		for(int s=1; s<studentNum+1; s++) {
			friends[s] = s;
		}// 친구 관계 초기화
		
		for(int r=0; r<relationNum; r++) {
			st = new StringTokenizer(br.readLine());
			String rr = st.nextToken();
			int f1 = Integer.parseInt(st.nextToken());
			int f2 = Integer.parseInt(st.nextToken());
			
			if(rr.equals("F")) {
				// 친구관계라면
				unionTeam(f1, f2);
			}else {
				// 원수관계라면
				if(fight[f1] != 0) {
					// 상대방에게 이미 원수가 있다면 그는 내 친구가 되어야 함.
					int myFriend = fight[f1];
					unionTeam(myFriend, f2);
				}else {
					// 상대방에게 아직 원소가 없다면 일단 내가 그의 원수가 되기
					fight[f1] = f2;
				}
				
				if(fight[f2] != 0) {
					int myFriend = fight[f2];
					unionTeam(myFriend, f1);
				}else {
					// 상대방에게 아직 원소가 없다면 일단 내가 그의 원수가 되기
					fight[f2] = f1;
				}
			}
		}
		
		
		int answer = 0;
		for(int stu=1; stu<studentNum+1; stu++) {
			if(friends[stu] == stu) {
				answer++;
			}
		}
		System.out.println(answer);
		
	}
	
	public static int findTeam(int x) {
		if(friends[x] == x) return x;
		return friends[x] = findTeam(friends[x]);
	}
	
	public static void unionTeam(int x, int y) {
		int teamX = findTeam(x);
		int teamY = findTeam(y);
		
		if(teamX != teamY) {
			friends[teamY] = teamX;
		}
	}

}
