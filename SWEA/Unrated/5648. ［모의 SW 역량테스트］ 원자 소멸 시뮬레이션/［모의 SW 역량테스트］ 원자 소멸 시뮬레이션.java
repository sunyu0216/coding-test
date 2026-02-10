import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 5648. 원자 소멸 시뮬레이션
 * 
 * 0. 변수들
 * 	0-1. 원자 클래스
 * 		0-1-1. 원자의 좌표, 방향, 에너지 양, 소멸 여부
 * 	0-2. 원자들의 수 atomNum
 * 	0-3. 원자 객체를 리스트로 받는 atomList
 * 	0-4. map[4001][4001](충돌감지를 위해 좌표 * 2)
 * 	0-5. 상하좌우 이동을 위한 dx[], dy[]
 * 	0-6. 얻을 수 있는 전체 에너지 양 totalEnergy
 * 
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 개수 T 만큼 반복하며 
 * 		1-1-1. atomList 에 원자 객체를 생성해 의 좌표와 이동방향, 보유 에너지를 입력받기 
 * 		1-1-2. map에 원자의 에너지입력해두기 (빈 좌표는 0)
 * 		1-1-3. 원자의 좌표 x, y -> 2000-(y+1000), x+1000 으로 변환
 *
 *
 *@see #simulation()
 * 2. 매 초당 반복문을 순회하며,(맵의 크기가 4000이므로 4000초를 넘어가면 자동으로 종료)
 * 	2-0. 원자 리스트가 없다면 = 모두 충돌나서 소멸 된 것이므로 끝내기
 * 
 * 	2-1. 원자 리스트를 순회하며 기존 좌표의 맵 정보 지우기
 * 		2-1-1. 현재 위치의 맵 정보를 0으로 초기화 (이동 전 청소)
 * 		-> 비우기를 안해주면 이동하면서 아직 이동 전인 원자들을 만나도 충돌이라 판단할 수 있음
 * 
 * 	2-2. 원자 리스트를 순회하며 이동시키고 새로운 위치에 에너지 누적하기
 * 		2-2-1. 이동할 좌표값을 구하기
 * 		2-2-2. 맵 밖으로 벗어나지 않는지 체크
 * 			a. 벗어나지 않으면 좌표 갱신 후 새로운 위치에 에너지 누적시키기
 * 			b. 벗어나면 -> 원자 소멸 처리
 * 
 * 	2-3. 원자 리스트를 순회하며 충돌 판별 및 처리
 * 		2-3-1. 현재 원자가 충돌 상태인지 판별(맵을 벗어난 소멸)
 * 			a. 충돌한다면 맵을 벗어난 원자이므로 원자 리스트에서 삭제해주기
 *  		b. continue
 * 		2-3-2. 현재 원자가 다른 원자랑 충돌하는지 판별 -> 
 * 				현재 원자의 위치의 에너지 양이 원래 원자의 에너지양보다 작다면 = 원자 여러개가 모여있다는 뜻 = 충돌
 * 			a. totalEnergy에 현재 원자 에너지 양 더해주기
 * 			b. 원자 리스트에서 삭제하기 전 일단 충돌이라 표시
 * 
 * 
 * 	2-4. 충돌된 원자들의 좌표를 맵에서 지우고 리스트에서 최종 삭제
 * 		2-4-1. 맵 초기화 및 원자 리스트에서 삭제
 * 
 * 	2-5. 살아남은 원자가 있다면 맵 초기화해주기
 * 
 * 
 */


public class Solution {
	
	static class Atom {
	    int r, c, dir, energy;
	    boolean isDead = false;

	    public Atom(int r, int c, int dir, int energy) {
	        this.r = r;
	        this.c = c;
	        this.dir = dir;
	        this.energy = energy;
	    }
	}
	
	static int atomNum;
	static List<Atom> atomList;
	static int[][] map = new int[4001][4001];
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int totalEnergy;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			atomNum = Integer.parseInt(br.readLine());
			atomList = new ArrayList<>();
			
			for(int aIdx=0; aIdx<atomNum; aIdx++) {
				st = new StringTokenizer(br.readLine());
				
				int tempX = Integer.parseInt(st.nextToken());
				int tempY = Integer.parseInt(st.nextToken());
				int direction = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());
				
				int nx = 4000-(tempY*2+2000);
				int ny = tempX*2+2000;
				
				// 좌표를 배열에 맞게 변환 및 저장
				atomList.add(new Atom(nx, ny, direction, energy));
				map[nx][ny] = energy;
			}
			
			totalEnergy = simulation();

			System.out.println("#"+t+" "+totalEnergy);
		}
	}
	
	static int simulation() {
		totalEnergy = 0;
		// 2. 매 초당 반복문을 순회하며,
		for(int second=1; second<=4000; second++) {
			// 2-0. 원자 리스트가 없다면 = 모두 충돌나서 소멸 된 것이므로 끝내기
			if(atomList.size() <= 0) {
				return totalEnergy;
			}
			
			// 2-1. 원자 리스트를 순회하며 기존 좌표의 맵 정보 지우기
			for(Atom atom : atomList) {
			    // 2-1-1. 현재 위치의 맵 정보를 0으로 초기화 (이동 전 청소)
			    map[atom.r][atom.c] = 0;
			}

			// 2-2. 원자 리스트를 순회하며 이동시키고 새로운 위치에 에너지 누적하기
			for(Atom atom : atomList) {
			    // 2-2-1. 이동할 좌표값을 구하기
			    int nr = atom.r + dx[atom.dir];
			    int nc = atom.c + dy[atom.dir];

			    // 2-2-2. 맵 밖으로 벗어나지 않는지 체크
			    if(0 <= nr && nr <= 4000 && 0 <= nc && nc <= 4000) {
			        // a. 벗어나지 않으면 좌표 갱신 후 새로운 위치에 에너지 누적시키기
			        atom.r = nr;
			        atom.c = nc;
			        map[atom.r][atom.c] += atom.energy;
			    } else {
			        // b. 벗어나면 -> 원자 소멸 처리
			        atom.isDead = true;
			    }
			}
			
			// 2-3. 원자 리스트를 순회하며 충돌 판별 및 처리
			for(int i=atomList.size()-1; i>=0; i--) {
				Atom currAtom = atomList.get(i);
				
				// 2-3-1. 현재 원자가 충돌 상태인지 판별(맵을 벗어난 소멸)
				if(currAtom.isDead) {
					// a. 충돌한다면 맵을 벗어난 원자이므로 원자 리스트에서 삭제해주기
					atomList.remove(i);
					continue;
				}
				
				// 2-3-2. 현재 원자가 다른 원자랑 충돌하는지 판별 -> 
				// 			현재 원자의 위치의 에너지 양이 원래 원자의 에너지양보다 작다면 = 원자 여러개가 모여있다는 뜻 = 충돌
				if(map[currAtom.r][currAtom.c] > currAtom.energy) {
					// a. 에너지 총합에 더해주기
					totalEnergy += currAtom.energy;
					
					// b. 원자 리스트에서 삭제하기 전 일단 충돌이라 표시
					currAtom.isDead = true;
				}
			}

			// 2-4. 충돌된 원자들의 좌표를 맵에서 지우고 리스트에서 최종 삭제
			for(int i = atomList.size() - 1; i >= 0; i--) {
				Atom curr = atomList.get(i);
				if(curr.isDead) {
					// 2-4-1. 맵 초기화 및 원자 리스트에서 삭제
					map[curr.r][curr.c] = 0; 
					atomList.remove(i);
				}
			}
		}
		
		// 2-5. 살아남은 원자가 있다면 맵 초기화해주기
		for (Atom atom : atomList) {
	        map[atom.r][atom.c] = 0;
	    }
		
		return totalEnergy;
	}

}
