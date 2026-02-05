import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 1873. 상호의 배틀필드
 * 
 * 0. static 변수들
 * 	a. 탱크 좌표 tankX, tankY / 탱크 방향 tankD
 *  b. 전체 맵의 가로 세로 Row, Col
 *  c. 전체 게임 맵 gameMap
 *  d. 명령어 개수 commandNum / 명령어 배열 commands
 * 
 * @see #main(String[])
 * 1. 입력받기
 * 		1-1. 테스트케이스 입력받기 T
 * 		1-2. 게임 맵의 높이와 너비 입력받기 Row, Col
 * 		1-3. 게임 맵 현황 입력받기 gameMap
 * 			a. 입력받으면서 현재 전차의 위치 tankX, tankY
 * 			b. 현재 전차의 방향 입력받기 tankD
 * 		1-4. 명령어 개수 입력받기 commandNum
 * 		1-5. 명령어 입력받기  commands
 * 
 * 2. 명령어 배열에서 반복문
 * 		2-1. 명령어의 종류에 따라 그에 맞게 전차를 이동 *이때 전차를 명령에 맞게 이동시키든 아니든 방향은 바뀌어야 함
 * 		2-2. 포탄 명령어라면 launchBomb 함수 호출
 * 
 * @see #launchBomb()
 * 3. 폭탄 발사 함수
 * 		3-1. 현재 전차의 방향에 따라 dx, dy로 폭탄이 날아갈 방향을 지정
 * 		3-2. while 문을 통해 맵 밖으로 폭탄이 나갈 때까지 반복
 * 		3-3. 중간에 벽을 만나면 stop
 * 		
 * 		
 */
public class Solution {
	static int tankX;
	static int tankY;
	static char tankD;
	static int Row;
	static int Col;
	static char[][] gameMap;
	static int commandNum;
	static char[] commands;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			// 1. 입력받기
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			Row = Integer.parseInt(st.nextToken());
			Col = Integer.parseInt(st.nextToken());
			
			gameMap = new char[Row][Col];
			
			for (int r = 0; r < Row; r++) {
			    String line = br.readLine();
			    
			    for (int c = 0; c < Col; c++) {
			        char val = line.charAt(c);
			        
			        if(val=='.' | val=='*' | val=='#' | val=='-') {
			        	//System.out.println("평지나, 벽, 물");
			        }else {
			        	tankX = r;
			        	tankY = c;
			        	tankD = val;
			        }
			        
			        gameMap[r][c] = val; // 배열에 대입
			    }
			}
			
			commandNum = Integer.parseInt(br.readLine());
			commands = br.readLine().toCharArray();
			
			
			// 2. 명령에 따라 탱크 움직이기
			for (char C : commands) {
			    if (C == 'U') {
			        tankD = '^';
			        gameMap[tankX][tankY] = tankD;
			        int nx = tankX - 1; 
			        
			        if (nx >= 0 && gameMap[nx][tankY] == '.') {
			            gameMap[tankX][tankY] = '.';
			            tankX = nx;
			            gameMap[tankX][tankY] = tankD;
			        }
			    } else if (C == 'D') {
			        tankD = 'v';
			        gameMap[tankX][tankY] = tankD;
			        int nx = tankX + 1;
			        
			        if (nx < Row && gameMap[nx][tankY] == '.') {
			            gameMap[tankX][tankY] = '.';
			            tankX = nx;
			            gameMap[tankX][tankY] = tankD;
			        }
			    } else if (C == 'L') {
			        tankD = '<';
			        gameMap[tankX][tankY] = tankD;
			        int ny = tankY - 1;
			        
			        if (ny >= 0 && gameMap[tankX][ny] == '.') {
			            gameMap[tankX][tankY] = '.';
			            tankY = ny;
			            gameMap[tankX][tankY] = tankD;
			        }
			    } else if (C == 'R') {
			        tankD = '>';
			        gameMap[tankX][tankY] = tankD;
			        int ny = tankY + 1;
			        
			        if (ny < Col && gameMap[tankX][ny] == '.') {
			            gameMap[tankX][tankY] = '.';
			            tankY = ny;
			            gameMap[tankX][tankY] = tankD;
			        }
			    } else if (C == 'S') {
			        launchBomb();
			    }
			}
			
			// 3. 출력하기
			System.out.print("#"+t+" ");
			for(int i=0; i<Row; i++) {
				for(int j=0; j<Col; j++) {
					System.out.print(gameMap[i][j]);
				}
				System.out.println();
			}
		}
	}
	
	static void launchBomb() {
		int dx = 0;
		int dy = 0;
		
		if (tankD == '^') dx = -1;
	    else if (tankD == 'v') dx = 1;
	    else if (tankD == '<') dy = -1;
	    else if (tankD == '>') dy = 1;
		
		int cx = tankX;
		int cy = tankY;
		
		while (true) {
	        cx += dx;
	        cy += dy;

	        // 맵 밖으로 나가면 그냥 소멸 (에러 방지)
	        if (cx < 0 || cx >= Row || cy < 0 || cy >= Col) break;

	        // 벽돌벽(*)이면 평지로 만들고 소멸
	        if (gameMap[cx][cy] == '*') {
	            gameMap[cx][cy] = '.';
	            break;
	        }
	        // 강철벽(#)이면 그냥 소멸
	        if (gameMap[cx][cy] == '#') break;
	        
	        // 평지(.)나 물(-)이면 계속 전진
	    }
	}

}
