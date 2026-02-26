import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 21608. 상어 초등학교
 * 
 * 1. 입력받기
 * 	1-1. 교실의 크기 classroomSize
 * 	1-2. 학생 번호, 이 학생이 좋아하는 다른 학생들 4명 studentsPrefer -> HashMap<Integer, Set<Integer>>으로 구현
 * 		입력받은 학생 번호 순서대로 studentOrder[] 에 저장
 * 	1-3. 전체 교실 classroom[][]
 * 
 * 
 * 2. studentOrder[] 을 순회하며,
 * 	2-1. 교실 전체를 순회
 * 		2-1-1. 빈칸일 경우에만 다음의 정보를 확인
 * 			2-1-1-1. 인접한 좋아하는 학생들의 수 likeCount
 * 			2-1-1-2. 인접한 빈칸의 수 emptyCount
 * 		
 * 		2-1-2. 현재 칸의 정보를 기존에 찾은 bestSeat 정보와 비교하여 갱신
 * 			2-1-2-1. likeCount가 큰 칸 우선
 * 			2-1-2-2. emptyCount가 큰 칸 우선
 * 			2-1-2-3. 행 번호가 작은 칸 우선
 * 			2-1-2-4. 열 번호가 작은 칸 우선
 * 
 * 	2-2. 모든 칸을 확인 후 최종 결정된 bestSeat에 현재 학생 번호를 저장해주기
 * 
 * 
 * 3. 모든 학생들의 배치가 끝났다면
 * 	3-1. 다시 교실 전체를 순회,
 * 	3-2. 점수 계산 후 출력
 * 
 * 
 */
public class Main {
	
	static int classroomSize;
	static HashMap<Integer, int[]> studentsPrefer;
	static int[] studentOrder;
	static int[][] classroom;
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		classroomSize = Integer.parseInt(br.readLine());
		classroom = new int[classroomSize][classroomSize];
		
		int studentsNum = classroomSize * classroomSize;
		studentsPrefer = new HashMap<>();
		studentOrder = new int[studentsNum];
		
		// 1-2. 학생 번호, 이 학생이 좋아하는 다른 학생들 4명 studentsPrefer -> HashMap<Integer, Set<Integer>>으로 구현
		// 		입력받은 학생 번호 순서대로 studentOrder[] 에 저장
		StringTokenizer st;
		for(int s=0; s<studentsNum; s++) {
			st = new StringTokenizer(br.readLine());
			int studentNum = Integer.parseInt(st.nextToken());
			int[] preferred = new int[4];
			for(int i=0; i<4; i++) {
				preferred[i] = Integer.parseInt(st.nextToken());
			}
			
			studentsPrefer.put(studentNum, preferred);
			studentOrder[s] = studentNum;
		}
		
		
		// 2. studentOrder[] 을 순회하며,
		for(int s=0; s<studentsNum; s++) {
			int[] bestSeat = new int[4]; // 차례대로 likeCount, emptyCount, r, c 저장
			Arrays.fill(bestSeat, -1);
			
			// 2-1. 교실 전체를 순회
			for(int r=0; r<classroomSize; r++) {
				for(int c=0; c<classroomSize; c++) {
					int likeCount = 0;
					int emptyCount = 0;
					
					// 2-1-1. 빈칸일 경우에만 다음의 정보를 확인
					if(classroom[r][c] == 0) {
						// 2-1-1-1. 인접한 좋아하는 학생들의 수 likeCount
						// 2-1-1-2. 인접한 빈칸의 수 emptyCount
						int[] result = findStudents(r, c, studentOrder[s]);
						likeCount = result[0];
						emptyCount = result[1];
						
						
						// 2-1-2. 현재 칸의 정보를 기존에 찾은 bestSeat 정보와 비교하여 갱신
						if(bestSeat[0] < likeCount) {
							// 2-1-2-1. likeCount가 큰 칸 우선
							bestSeat = new int[] {likeCount, emptyCount, r, c};
							
						}else if(bestSeat[0] == likeCount && bestSeat[1] < emptyCount) {
							// 2-1-2-2. emptyCount가 큰 칸 우선
							bestSeat = new int[] {likeCount, emptyCount, r, c};
							
						}else if(bestSeat[0] == likeCount && bestSeat[1] == emptyCount && bestSeat[2] > r) {
							// 2-1-2-3. 행 번호가 작은 칸 우선
							bestSeat = new int[] {likeCount, emptyCount, r, c};
							
						}else if(bestSeat[0] == likeCount && bestSeat[1] == emptyCount && bestSeat[2] == r && bestSeat[3] > c) {
							// 2-1-2-4. 열 번호가 작은 칸 우선
							bestSeat = new int[] {likeCount, emptyCount, r, c};
							
						}
					}
				}
			}
			
			// 2-2. 모든 칸을 확인 후 최종 결정된 bestSeat에 현재 학생 번호를 저장해주기
			int finalR = bestSeat[2];
			int finalC = bestSeat[3];
			classroom[finalR][finalC] = studentOrder[s];
		}
		
		// 3. 모든 학생들의 배치가 끝났다면
		// 	3-1. 다시 교실 전체를 순회,
		int answer = 0;
		for(int r=0; r<classroomSize; r++) {
			for(int c=0; c<classroomSize; c++) {
				int currStudentNum = classroom[r][c];
				int currScore = 0;
				
				// 인접한 칸들 탐색
				for(int d=0; d<4; d++) {
					int nx = r + dx[d];
					int ny = c + dy[d];
					
					if((0<=nx && nx<classroomSize) && (0<=ny && ny<classroomSize)) {
						for(int preferred: studentsPrefer.get(currStudentNum)) {
							// 해당 인접한 칸에서 좋아하는 학생이 있는지 판단
							if(classroom[nx][ny] == preferred) {
								currScore++;
							}
						}
					}
				}
				
				if(currScore == 2) {
					currScore = 10;
				}else if(currScore == 3) {
					currScore = 100;
				}else if(currScore == 4) {
					currScore = 1000;
				}
				answer += currScore;
			}
		}
		
		System.out.println(answer);
		
		
	}
	
	// 좋아하는 학생들이 주어진 좌표로부터 인접한 칸에 몇명있는지, 빈칸은 몇개인지 반환하는 함수
	public static int[] findStudents(int r, int c, int studentNum) {
		int likeCnt = 0;
		int emptyCnt = 0;
		
		// 인접한 칸들 탐색
		for(int d=0; d<4; d++) {
			int nx = r + dx[d];
			int ny = c + dy[d];
			
			if((0<=nx && nx<classroomSize) && (0<=ny && ny<classroomSize)) {
				for(int preferred: studentsPrefer.get(studentNum)) {
					// 해당 인접한 칸에서 좋아하는 학생이 있는지 판단
					if(classroom[nx][ny] == preferred) {
						likeCnt++;
					}
				}
				if(classroom[nx][ny] == 0) {
					// 해당 인접한 칸에서 빈칸이 있는지 판단
					emptyCnt++;
				}
			}
		}
		return new int[] {likeCnt, emptyCnt};
	}

}
