import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 17135. 캐슬 디펜스
 *
 * 1. 입력받기
 * 	1-1. 격자판 행의 수  rowNum, 열의 수 colNum, 궁수의 공격 거리 distance
 * 	1-2. map[rowNum][colNum] 에 격자판 상태 입력받기
 * 
 * 
 * @see #combination(int, int)
 * 2. 궁수 3명을 성에 배치하는 모든 경우의 수를 구하기
 * 	2-1. 기저조건: 궁수 3명을 모두 배치한 경우
 * 		2-1-1. 시뮬레이션 함수 호출
 * 		2-1-2. 최대 적 처치값을 갱신
 * 
 * 	2-2. 유도파트: 시작 위치부터 마지막 열까지 순회하며,
 * 		2-2-1. 현재 위치를 궁수 열 위치를 담는 배열에 저장
 * 		2-2-2. 재귀 호출
 * 
 * 
 * @see #simulation(int[])
 * 3. 그 경우의 수를 순회하며 시뮬레이션
 * 	3-1. 기존 map[][] 을 복사해서 사용 -> @see #copyMap(int[][])
 * 	3-2. 각 열에 위치한 궁수들의 타겟 선정하기
 * 		3-2-1. 쏠 적이 정해지면 3명의 모든 궁수가 화살을 쏘게 하기
 * 		3-2-2. 다 쏘고 나면 죽은 적을 제외
 * 	3-3. 적 이동(한칸씩 내리기)
 * 
 * 
 * @see #chooseTarget(int, int[][])
 * 4. 궁수 한명의 타겟선정하는 함수
 * 	4-1. 2중 for문으로 r은 rowNum-1 부터 c는 0부터 돌기
 * 		4-1-1. 적을 발견하면 거리를 계산하기
 * 		4-1-2. 공격 거리 안인지 확인하기
 * 		4-1-3. 안이라면, 현재 적이 지금까지 찾은 적보다 더 가까운지 확인
 * 			a. 가깝다면 무조건 갱신
 * 			b. 같다면 c가 지금까지 찾은 가장 적은 거리에 있던 적보다 왼쪽에 있는지 확인 -> 맞다면 갱신
 * 
 * 
 * @see #moveTargets(int[][])
 * 5. 적들을 이동시키는 함수
 * 	5-1. 가장 마지막 줄(rowNum-1)에 있는 적들은 성벽 밖으로 나감
 * 	5-2.  사이의 적들을 아래로 한 칸씩 이동
 * 	5-3. 맨 윗줄은 0으로 채우기
 * 
 * 
 */
public class Main {
	static int rowNum;
	static int colNum;
	static int distance;
	static int[][] map;
	static int[] archers = new int[3];
	static int targetCount;
	static int maxDeadCount;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		rowNum = Integer.parseInt(st.nextToken());
		colNum = Integer.parseInt(st.nextToken());
		distance = Integer.parseInt(st.nextToken());
		map = new int[rowNum][colNum];
		targetCount = 0;
		
		for(int r=0; r<rowNum; r++) {
			st = new StringTokenizer(br.readLine());
			
			for(int c=0; c<colNum; c++) {
				int value = Integer.parseInt(st.nextToken());
				map[r][c] = value;
				
				if(value == 1) {
					// 적이라면, 세어주기
					targetCount++;
				}
				
			}
		}
		
		combination(0, 0);
		
		System.out.println(maxDeadCount);
	}
	
	
	
	// 맵을 카피해주는 함수
	static int[][] copyMap(int[][] origin) {
	    int[][] temp = new int[rowNum][colNum];
	    for(int i=0; i<rowNum; i++) {
	        temp[i] = origin[i].clone();
	    }
	    return temp;
	}
	
	
	// 2. 궁수 3명을 성에 배치하는 모든 경우의 수를 구하기
	static void combination(int cnt, int start) {
		// 2-1. 기저조건: 궁수 3명을 모두 배치한 경우
		if(cnt == 3) {
			// 2-1-1. 시뮬레이션 함수 호출
			int currDeadCount = simulation(archers);
			
			// 2-1-2. 최대 적 처치값을 갱신
			maxDeadCount = Math.max(maxDeadCount, currDeadCount);
			
			return;
		}
		
		// 2-2. 유도파트: 시작 위치부터 마지막 열까지 순회하며,
		for(int i=start; i<colNum; i++) {
			// 2-2-1. 현재 위치를 궁수 열 위치를 담는 배열에 저장
			archers[cnt] = i;
			
			combination(cnt+1, i+1);
		}
	}
	
	
	// 3. 그 경우의 수를 순회하며 시뮬레이션
	static int simulation(int[] archers) {
		// 3-1. 기존 map[][] 을 복사해서 사용
		int[][] tempMap = copyMap(map);
		
		// 지역변수로 사용할 전체 적들의 수, 처치한 적의 수
		int tempTargetCount = targetCount;
		int deadTarget = 0;
					
		while(tempTargetCount > 0) {
			int[][] targets = new int[3][2];
			
			// 3-2. 각 열에 위치한 궁수들의 타겟 선정하기
			for(int i=0; i<archers.length; i++) {
				targets[i] = chooseTarget(archers[i], tempMap);
			}
			
			// 3-2-2. 다 쏘고 나면 죽은 적을 제외
			for(int[] pos: targets) {
				int tarX = pos[0];
				int tarY = pos[1];
				
				if(tarX == -1 || tarY == -1) {
					// 사거리 내 타겟 선정에 실패한 것이므로 pass
					continue;
				}
				
				if(tempMap[tarX][tarY] == 1) {
					// 살아있는 적이면
					tempTargetCount--;
					deadTarget++;
				}
				tempMap[tarX][tarY] = 0;
			}
			
			// 3-3. 적 이동(한칸씩 내리기)
			tempTargetCount -= moveTargets(tempMap);
		}
		
		return deadTarget;
		
		
	}
	
	// 4. 궁수 한명의 타겟선정하는 함수
	static int[] chooseTarget(int archerCol, int[][] map) {
		int minDist = Integer.MAX_VALUE;
		int targetX = -1;
		int targetY = -1;
		
		// 4-1. 2중 for문으로 r은 rowNum-1 부터 c는 0부터 돌기
		for(int r=rowNum-1; r>=0; r--) {
			for(int c=0; c<colNum; c++) {
				// 4-1-1. 적을 발견하면 거리를 계산하기
				if(map[r][c] == 1) {
					int currDist = Math.abs(rowNum - r) + Math.abs(archerCol - c);
					
					// 4-1-2. 공격 거리 안인지 확인하기
					if(currDist <= distance) {
						// 4-1-3. 안이라면, 현재 적이 지금까지 찾은 적보다 더 가까운지 확인
						if(currDist < minDist) {
							// a. 가깝다면 무조건 갱신
							minDist = currDist;
							targetX = r;
							targetY = c;
						}else if(currDist == minDist) {
							// b. 같다면 c가 지금까지 찾은 가장 적은 거리에 있던 적보다 왼쪽에 있는지 확인 -> 맞다면 갱신
							if(c<targetY) {
								targetX = r;
								targetY = c;
							}
						}
					}
				}
			}
		}
		
		return new int[] {targetX, targetY};
		
	}
	
	// 5. 적들 이동시키는 함수
	static int moveTargets(int[][] map){
		int outCount = 0;
	    
	    // 5-1. 가장 마지막 줄(rowNum-1)에 있는 적들은 성벽 밖으로 나감
	    for (int c = 0; c < colNum; c++) {
	        if (map[rowNum - 1][c] == 1) outCount++;
	    }
	    
	    // 5-2.  사이의 적들을 아래로 한 칸씩 이동
	    for (int r = rowNum - 1; r > 0; r--) {
	        for (int c = 0; c < colNum; c++) {
	            map[r][c] = map[r - 1][c];
	        }
	    }
	    
	    // 5-3. 맨 윗줄은 0으로 채우기
	    for (int c = 0; c < colNum; c++) map[0][c] = 0;
	    
	    return outCount;
						
	}
	

}
