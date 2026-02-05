import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 2115. 벌꿀채취
 * 
 * @see #main(String[])
 * 0. 플로우
 * - 전체 벌꿀집 중 선택할 수 있는 honeyLen만큼 반복문을 돌림 
 * - 여기서 부분집합 함수같이 뭘 선택하고 뭘 선택하지 않아야 최적의 수익이 되는지 계산해서 최적 수익 배열에 저장
 * - 완성된 최적 수익 배열을 순회하면서 두명의 일꾼이 각자 영역을 선택하게 함..
 * - 여기서 두개의 영역이 안겹치게 주의할것.
 * 
 * 1. 변수 & 입력받기
 * 		1-1. 전체 테스트케이스 개수 T
 * 		1-2. static 변수들
 * 			a. 벌꿀집의 개수 mapSize, 선택할 수 있는 벌통의 개수 honeyLen, 채취할 수 있는 꿀의 최대 양 maxCapacity
 * 			b. 벌꿀의 양 honeyMap[][], honeyLen만큼 선택했을때의 최고 수익을 저장한 배열 bestSalesMap
 * 		1-3. 벌통에서 채취할 수 있는 꿀 양 정보 입력받기 honeyMap[][]
 * 
 * 2. honeyLen만큼 선택했을때의 최고 수익을 저장한 배열 bestSalesMap 채우기
 * 
 * 3. bestSalesMap에서 겹치지 않게 2개 좌표 선택하기
 * 
 * 4. 더해서 출력
 * 	
 * 
 * @see #makeBestSalesMap()
 * honeyLen 만큼 가로로 영역을 만들었을 때 채취할 수 있는 양을 넘기지 않으면서 낼 수 있는 최고 수익을 전처리하는 함수
 * 		1. 모든 행과 열을 순회하며 findBestProfits를 호출하여 각 시작점의 최고 수익을 기록
 * 
 * @see #findBestProfits(int, int, int, int)
 * honeyLen 만큼의 영역에서 최고 수익을 만들 수 있는 부분집합 조합을 재귀로 찾는 함수
 * 		1. 선택한 벌통들의 합이 maxCapacity를 넘는지 체크 -> 넘으면 이 조합은 실패
 * 		2. 각 벌통을 포함하는 경우와 포함하지 않는 경우 중 더 큰 제곱 합을 반환하도록 설계
 * 
 * @see #collectTwoHoney()
 * 최고 수익 배열에서 2개 좌표를 중복되지 않고 겹치지 않게 골라주는 함수
 * 		1. 두 영역이 같은 행에 있을 경우, 열 인덱스 차이가 honeyLen 이상인지 확인하여 실제 벌꿀지도에서 겹침 방지 
 * 
 */
public class Solution {
	static int mapSize;
	static int honeyLen;
	static int maxCapacity;
	static int[][] honeyMap;
	static int[][] bestSalesMap;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			// 1. 입력받기
			StringTokenizer st = new StringTokenizer(br.readLine());
			mapSize = Integer.parseInt(st.nextToken());
			honeyLen = Integer.parseInt(st.nextToken());
			maxCapacity = Integer.parseInt(st.nextToken());
			
			honeyMap = new int[mapSize][mapSize];
			for(int i=0; i<mapSize; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<mapSize; j++) {
					honeyMap[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			
			// 2. 각 위치에서 m개를 선택했을 때 최고 수익 구하기
			bestSalesMap = new int[mapSize][mapSize-honeyLen+1];
			makeBestSalesMap();
			
			
			
			// 3. 최고의 수익 맵에서 겹치지 않게 2개 선택하기
			int answer = collectTwoHoney();
			
			System.out.println("#"+t+" "+answer);
		}
		
	}
	
	// 최고의 수익만을 보여주는 맵을 만드는 함수
	static void makeBestSalesMap() {
		
		for(int i=0; i<mapSize; i++) {
			for(int j=0; j<mapSize-honeyLen+1; j++) {
					bestSalesMap[i][j] = findBestProfits(0, 0, i, j);
					//System.out.print(bestSalesMap[i][j]);
			}
			//System.out.println();
		}
	}
	
	// 선택한 구간에서 최고의 수익을 얻는 조합을 선택해주는 함수
	// subsetProfits: 각 조합에서 얻는 이익, maxProfits: 얻을 수 있는 최대 이익, sum: 현재 선택한 꿀의 양, idx: m개 골라야되는 것 중 몇번째인지
	static int findBestProfits(int sum, int idx, int r, int c) {
		// 기저조건1: 채취할 수 있는 양을 넘김 - 이 경로는 무효
		if(sum > maxCapacity) {
			return Integer.MIN_VALUE;
		}
		
		// 기저조건2: 채취할 수 있는 가로길이 끝남 - 여기부터 다시 거슬러올라가며 계산 시작
		if(idx == honeyLen) {
			return 0;
		}
		
		int currentHoney = honeyMap[r][c+idx]; // 현재 꿀의 양
		
		int takeCurrent = (currentHoney * currentHoney) + findBestProfits(sum+currentHoney, idx+1, r, c);
		int skipCurrent = findBestProfits(sum, idx+1, r, c);
		
		return Math.max(takeCurrent, skipCurrent);
		
	}
	
	// 최고의 수익 구간 2개를 겹치지 않게 선택해주는 함수
	static int collectTwoHoney() {
		int finalMaxProfits = 0;
		
		for(int r1=0; r1<mapSize; r1++) {
			for(int c1=0; c1<mapSize-honeyLen+1; c1++) {
				//System.out.println("첫번째 일꾼이 고른 좌표: "+r1+" "+c1);
				
				for(int r2=r1; r2<mapSize; r2++) {
					for(int c2=0; c2<mapSize-honeyLen+1; c2++) {
						//System.out.println("두번째 일꾼이 고른 좌표: "+r2+" "+c2);
						
						// 겹치는지 확인하기
						if(r1==r2 && c1+honeyLen-1 >= c2) continue;
						else {
							finalMaxProfits = Math.max(finalMaxProfits, bestSalesMap[r1][c1]+bestSalesMap[r2][c2]);
						}
					}
				}
			}
		}
		return finalMaxProfits;
	}

}
