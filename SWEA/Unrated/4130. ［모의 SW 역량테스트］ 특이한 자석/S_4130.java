import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author seonyu
 *
 * 0. 시계방향이 1, 반시계방향이 -1 / N극이 0, S극이 1
 *
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 테스트케이스 개수 testcase
 * 		1-2. 자석을 회전시키는 횟수 rotateNum
 * 		1-3. 자석 자성 정보 magnets[][] 배열에 저장, 현재 화살표 위치 currPosition[]
 * 		1-4. 자석 회전 정보 (회전시키는 자석번호, 회전방향) = (magnatNum, rotationD)
 *
 * @see #rotateMagnet(int, int, int[][], int[])
 * 2. 현재 입력으로 들어온 자석 회전시키기
 * 		2-1. 타겟 자석을 기준으로 왼쪽으로 퍼져가며 자석의 회전방향 결정
 * 		2-2. 타겟 자석을 기준으로 오른쪽으로 퍼져가며 자석의 회전방향 결정
 * 		2-3. 방향이 정해졌다면 한번에 바꿔주기 = currPosition 위치를 방향대로 바꿔주기
 *
 * @see #getLeftIdx(int, int[])
 * 3. 현재 위치에서 왼쪽(9시) 방향에 있는 날 인덱스 찾기
 * 		3-1. 현재 위치에 6을 더하고 8로 나눈 나머지를 반환하기
 *
 * @see #getRightIdx(int, int[])
 * 4. 현재 위치에서 오른쪽(3시) 방향에 있는 날 인덱스 찾기
 * 		4-1. 현재 위치에 2를 더하고 8로 나눈 나머지를 반환하기
 *
 *
 * @see #main(String[])
 * 5. s극일 때만 점수 계산하기
 *
 */

public class S_4130 {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int testcase = Integer.parseInt(br.readLine().trim());

		for(int t=1; t<=testcase; t++) {
			// 회전정보 읽기
			int rotateNum = Integer.parseInt(br.readLine());

			int[][] magnets = new int[4][8]; // 4개의 자석, 각 8개의 날
			int[] currPosition = new int[4];

			for (int i = 0; i < 4; i++) {
			    StringTokenizer st = new StringTokenizer(br.readLine());
			    for (int j = 0; j < 8; j++) {
			        magnets[i][j] = Integer.parseInt(st.nextToken());
			    }
			    currPosition[i] = 0;
			}

			//System.out.println("회전정보 받아오기");
			for (int r=0; r<rotateNum; r++) {
				StringTokenizer st = new StringTokenizer(br.readLine().trim());
				int rotateMagNum = Integer.parseInt(st.nextToken());
				int rotateD = Integer.parseInt(st.nextToken());

				System.out.println("\n[회전 명령 " + (r + 1) + "]: " + rotateMagNum + "번 자석을 " + (rotateD == 1 ? "시계" : "반시계") + "로 회전");
				rotateMagnet(rotateMagNum-1, rotateD, magnets, currPosition);
			}

			// 마지막 점수 계산
			int totalScore = 0;
			System.out.print("\n[최종 12시 방향 자성]: ");
			for(int i=0; i<4; i++) {
				int topVal = magnets[i][currPosition[i]];
				System.out.print(i + "번:" + (topVal == 0 ? "N" : "S") + " ");
				if (topVal == 1) {
					totalScore += (1 << i);
				}
			}

			System.out.println("#"+t+" "+totalScore);
		}

	}

	public static void rotateMagnet(int targetMag, int targetDir, int[][] magnets, int[] currPosition) {
		// 1. 회전 명령이 들어오면
		int[] rotateDirections = new int[4]; // 각 자석이 돌 방향 저장 (0은 정지)
		rotateDirections[targetMag] = targetDir;

		// 2. 타겟 기준 왼쪽으로 퍼지며 확인
		for (int i = targetMag; i > 0; i--) {
		    if (magnets[i][getLeftIdx(i, currPosition)] != magnets[i-1][getRightIdx(i-1, currPosition)]) {
		        rotateDirections[i-1] = -rotateDirections[i]; // 현재 타겟 자석의 반대 방향으로 회전
		    } else break; // 같으면 그 이후는 안 돌아감
		}

		// 3. 타겟 기준 오른쪽으로 퍼지며 확인
		for (int i = targetMag; i < 3; i++) {
			System.out.println("현재 "+(i+1)+"자석의 화살표가 가리키는 인덱스: "+currPosition[i+1]);
			System.out.println(i+"번쨰 자석의 오른쪽 자성: "+ magnets[i][getRightIdx(i, currPosition)]+"  "+(i+1)+"번째 자석의 왼쪽 자성: "+ magnets[i+1][getLeftIdx(i+1, currPosition)]);
		    if (magnets[i][getRightIdx(i, currPosition)] != magnets[i+1][getLeftIdx(i+1, currPosition)]) {
		        rotateDirections[i+1] = -rotateDirections[i];
		    } else break;
		}

		// 4. 결정된 방향대로 currPosition 갱신

		System.out.print("결정된 회전 방향: ");
		for (int d : rotateDirections) System.out.print(d + " ");
		System.out.println();

		for (int i = 0; i < 4; i++) {
		    if (rotateDirections[i] == 1) { // 시계
		        currPosition[i] = (currPosition[i] + 7) % 8; // -1 효과
		    } else if (rotateDirections[i] == -1) { // 반시계
		        currPosition[i] = (currPosition[i] + 1) % 8;
		    }
			System.out.println("바뀐 " + i + "번 자석 화살표 위치: "+currPosition[i]);
		}
	}

	// i번째 자석의 오른쪽 날(3시 방향) 인덱스 가져오기
	public static int getRightIdx(int magnetIdx, int[] currPosition) {
		System.out.println("오른쪽 인덱스: "+ (currPosition[magnetIdx] + 2) % 8);
	    return (currPosition[magnetIdx] + 2) % 8;
	}

	// i번째 자석의 왼쪽 날(9시 방향) 인덱스 가져오기
	public static int getLeftIdx(int magnetIdx, int[] currPosition) {
		System.out.println("왼쪽 화살표 인덱스: "+ (currPosition[magnetIdx] + 6) % 8);
	    return (currPosition[magnetIdx] + 6) % 8;
	}

}

