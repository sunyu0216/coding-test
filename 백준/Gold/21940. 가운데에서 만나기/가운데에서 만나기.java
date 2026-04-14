
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 21940. 가운데에서 만나기
 * 
 * 1. 입력받기
 * 	1-1. cityNum, roadNum
 * 	1-2. roads[][] 
 * 	1-3. friendsNum
 * 	1-4. friendsCity[friendsNum]
 * 
 * *최대가 최소가 되는 거리를 선택해야 하므로 최대 거리를 기록해야 함.
 * -> 아님. 저 최대는 걍 친구들의 왕복거리의 최대였고,,, 최소 거리를 다 구한 다음에 최대거리를 구해주면 됨.
 * 
 * <최소거리 구해주기>
 * 2. 중간에 거칠 도시를 도시개수만큼 반복하며 k
 * 	2-1. 시작할 도시(친구들의 도시)를 반복하며 i
 * 		2-1-1. 만날 도시를 cityNum만큼 반복하며 j
 * 			2-1-1-1. if(roads[i][k]+roads[k][j] < roads[i][j])라면 roads[i][j] = k를 거쳐가는거리로 수정
 * 			2-1-1-2. if(돌아오는 길도 점검) roads[j][i] = k를 거쳐가는 거리로 수정
 * 
 * <친구들 왕복거리를 비교하기>
 * 3. 중간에 만날 도시를 찾기 위해 다시 도시개수만큼 반복하며
 * 	3-1. 이 도시일때 각 시작도시에서부터 이 도시까지의 왕복거리를 비교하기 -> 가장 큰 왕복거리만 남기기
 * 	3-2. 이 최대 왕복거리가 기존 최종 거리보다 작다면 도시 갱신
 * 	3-3. 같다면 도시 추가
 *
 */
public class Main {
	static int cityNum;
	static int roadNum;
	static int[][] roads;
	static int friendsNum;
	static int[] friendsCity;
	static int totalTime;
	static List<Integer> totalCity;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		cityNum = Integer.parseInt(st.nextToken());
		roadNum = Integer.parseInt(st.nextToken());
		roads = new int[cityNum+1][cityNum+1];
		for(int i=0; i<cityNum+1; i++) {
			Arrays.fill(roads[i], 987654321);
			roads[i][i] = 0;
		}
		
		for(int r=0; r<roadNum; r++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int time = Integer.parseInt(st.nextToken());
			
			roads[start][end] = time;
		} // 도로 입력 끝
		
		friendsNum = Integer.parseInt(br.readLine());
		friendsCity = new int[friendsNum];
		st = new StringTokenizer(br.readLine());
		for(int f=0; f<friendsNum; f++) {
			friendsCity[f] = Integer.parseInt(st.nextToken());
		}// 친구 입력 끝
		
		
		for(int k=1; k<cityNum+1; k++) {
			//System.out.println("중간에 "+k+" 를 들리는 경우");
			for(int i=1; i<cityNum+1; i++) {
				for(int j=1; j<cityNum+1; j++) {
					if(roads[i][k] + roads[k][j] < roads[i][j]) {
						roads[i][j] = roads[i][k] + roads[k][j];
					}
					if(roads[j][k] + roads[k][i] < roads[j][i]) {
						roads[j][i] = roads[j][k] + roads[k][i];
					}
					//System.out.println(i+" 이고 "+j+" 일때"+ roads[i][j] + " "+roads[j][i]);
				}
			}
		}
		
		totalTime = Integer.MAX_VALUE;
		totalCity = new ArrayList<>();
		boolean flag = false;
		
		for(int c=1; c<cityNum+1; c++) {
			int currTime = 0;
			flag = false;
			for(int i: friendsCity) {
				if(roads[i][c]==-1 || roads[c][i]==-1) {
					flag = true;
					break;
				}else {
					currTime = Math.max(currTime, roads[i][c] + roads[c][i]); // 친구들 중 가장 오래 걸리는 시간 선택
				}
			}
			if(flag) {
				continue;
			}
			//System.out.println("현재 "+ c+" 도시에서 모이는 친구들의 시간은 "+currTime+"만큼 걸림");
			if(currTime < totalTime) {
				totalCity.clear();
				totalCity.add(c);
				totalTime = currTime;
			}else if(currTime == totalTime) {
				totalCity.add(c);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		Collections.sort(totalCity);
		for (int i = 0; i < totalCity.size(); i++) {
		    sb.append(totalCity.get(i));
		    if (i != totalCity.size() - 1) { // 마지막 원소가 아닐 때만 공백 추가
		        sb.append(" ");
		    }
		}
		System.out.println(sb.toString());
		
	}

}
