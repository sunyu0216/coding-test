import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 2382. 미생물 격리
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스개수 T
 * 	1-2. 맵의 크기 size, 격리시간 totalTime, 군집의 개수 associationNum
 * 	1-3. 각 군집의 정보를 클래스 리스트로 받기 microList
 * 
 * 2. Micro 클래스
 * 	2-1. 현재 위치 x, y
 * 	2-2. 현재 방향
 * 	2-3. 현재 군집 내 미생물 개수
 * 	2-4. 죽었는지 여부
 * 
 * <매번 초기화 되어야하는 변수들>
 * sumMicro[][] = 해당 칸에 모인 군집의 미생물이 얼마나 누적되었는지를 나타냄
 * maxMicro[][] = 해당 칸에 모인 군집 중 가장 큰 미생물을 나타냄
 * leadMicroNum[][] = 해당 칸에 모인 군집 중 가장 큰 미생물 크기를 보유한 군집의 번호를 나타냄
 * 
 * 3. totalTime 만큼 반복하며,
 * 	3-0. 기존 좌표의 맵 정보 지우기
 * 
 * 	3-1. 각 군집들을 순회하며, 이동 처리
 * 		* 죽음 처리된 군집은 pass
 * 		3-1-1. 군집의 새 위치 계산
 * 		3-1-2. 군집의 새 위치가 약품이 발라져 있는 곳인지 체크
 * 			3-1-2-1. 맞다면, 줄어든 미생물 개수 구하고 갱신 or 죽음처리
 * 		3-1-3. 약품이 발라져있지 않다면 새로운 위치가 0인지 체크
 * 			3-1-3-1. 0 이상이라면 이미 다른 군집이 있다는 뜻이므로, sumMicro에 미생물 크기 누적시키기
 * 				3-1-3-1-1. maxMicro와 비교했을 때 더 크다면, 이전 leadMicroNum번호를 갖는 군집을 죽음 처리 하고, maxMicro, leadMicroNum를 갱신
 * 			3-1-3-2. 0 이라면 처음 위치하는 군집이므로 sumMicro, maxMicro에 미생물 크기 저장, leadMicroNum에 현재 군집 번호 저장
 * 
 * 지금 문제가 1. 충돌이 난 군집을 어떻게 표시할건지, 2. 충돌이 났을때 살아남는 방향은 무엇인지 어떻게 표시할건지
 * 			
 *
 */
public class Solution {
	static int T;
	static int size; 
	static int totalTime;
	static int associationNum;
	static Micro[] microList; // 미생물 군집 리스트
	
	static Micro[][] map; // 미생물의 이동을 기록
	
	
	// 상: 1, 하: 2, 좌: 3, 우: 4
	static int[] dx = {0, -1, 1, 0, 0};
	static int[] dy = {0, 0, 0, -1, 1};
	
	static int answer;
	
	static class Micro{
		int x;
		int y;
		int dir;
		int microNum;
		int totalMicroNum;
		boolean isDead;
		
		public Micro(int x, int y, int dir, int microNum, int totalMicroNum, boolean isDead) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.microNum = microNum;
			this.totalMicroNum = totalMicroNum;
			this.isDead = isDead;
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			size = Integer.parseInt(st.nextToken());
			totalTime = Integer.parseInt(st.nextToken());
			associationNum = Integer.parseInt(st.nextToken());
			
			microList = new Micro[associationNum]; // 미생물 군집 리스트
			map = new Micro[size][size]; // 미생물의 시간에 따른 이동 관리
			
			for(int i=0; i<associationNum; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int microNum = Integer.parseInt(st.nextToken());
				int direction = Integer.parseInt(st.nextToken());
				
				microList[i] = new Micro(x, y, direction,  microNum,  microNum, false);
			}// 군집 정보 입력 끝
			
			answer = solve();
			
			System.out.println("#"+t+" "+answer);
		}
	}
	
	private static int solve() { // totalTime 후 살아 있는 미생물 수 총합 리턴
		int time = totalTime, nr, nc, remainCnt = 0;
		
		// totalTime 동안 반복
		while(--time>=0) {
			for(Micro m: microList){
				if(m.isDead) continue; // 소멸된 군집이면 다음으로..
				
				// 군집 다음 좌표로 좌표 업데이트
				nr = m.x += dx[m.dir];
				nc = m.y += dy[m.dir];
				
				// 가장자리 셀인 경우에는 크기 1/2, 방향 반대로
				if(nr==0 || nr==size-1 || nc==0 || nc==size-1) {
					// total에도 적용해주기
					m.totalMicroNum = m.microNum = m.microNum/2;
					if(m.microNum == 0) {
						m.isDead = true;
						continue; 
					}
					
					if(m.dir % 2 == 1) {
						// 상과 좌 방향일 때
						m.dir++;
					}else {
						m.dir--;
					}
				}
				
				// 업데이트 된 좌표로 이동하며 병합
				if(map[nr][nc] == null) { // 이동한 위치에 아무것도 없음 = 자신이 처음 도착함
					map[nr][nc] = m;
				}else { // 이동한 위치에 군집이 있다면 크기 비교 후 병합하기
					if(map[nr][nc].microNum > m.microNum) {
						map[nr][nc].totalMicroNum += m.microNum;
						m.microNum = 0;
						m.isDead = true;
					}else {
						m.totalMicroNum += map[nr][nc].totalMicroNum;
						map[nr][nc].microNum = 0;
						map[nr][nc].isDead = true;
						map[nr][nc] = m;
					}
				}
			}
			remainCnt = reset();
		}
		
		return remainCnt;
	}
	
	static int reset() {
		int result = 0;
		for(Micro m: microList) {
			if(m.isDead) continue;
			
			if(map[m.x][m.y] == m) {
				m.microNum = m.totalMicroNum;
				map[m.x][m.y] = null;
			}
			result += m.microNum;
		}
		return result;
	}

}
