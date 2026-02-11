import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 14510. 나무 높이
 * 
 * [플로우]
 * 1. 가장 큰 나무 높이 구하기
 * 2. 나무 배열을 순회하며 각 나무와 가장 큰 나무 높이 차이를 구하기
 * 3. 1일 부터 세며,
 * 	3-1. 홀수날이라면,
 * 		3-1-1. 나무 높이 차이가 홀수인 나무 중 하나에 물을 주기
 * 		3-1-2. (홀수인 나무가 없다면) 짝수인 나무 중 2보다 큰 나무 중 하나에 물을 주기
 * 		3-1-3. (위의 경우가 모두 없다면) 아무것도 안하고 다음 날로 넘기기
 * 
 * 	3-2. 짝수 날이라면,
 * 		3-2-1. 2 이상 남은 나무가 있다면 바로 물을 주고 종료
 * 		3-2-2. (위의 경우가 없다면) 아무것도 안하고 다음날로 넘기기
 * 	
 * 	3-3. 모든 나무 높이 차이가 0이 되는 순간 빠져나오기
 * 
 *
 */
public class Solution {
	
	static int treeNum;
	static int[] treeHeight;
	static int totalTreeDiff;
	static int totalDays;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			treeNum = Integer.parseInt(br.readLine());
			treeHeight = new int[treeNum];
			st = new StringTokenizer(br.readLine());
			
			// 1. 나무 높이를 저장하고, 가장 큰 나무 높이를 구하기
			int maxTree = 0;
			for(int tree=0; tree<treeNum; tree++) {
				int value = Integer.parseInt(st.nextToken());
				treeHeight[tree] = value;
				maxTree = Math.max(maxTree, value);
			}
			
			totalTreeDiff = 0;
			// 2. 나무 배열을 순회하며 각 나무와 가장 큰 나무 높이 차이를 구하기
			for(int tree=0; tree<treeNum; tree++) {
				int value = maxTree - treeHeight[tree];
				treeHeight[tree] = value;
				totalTreeDiff += value;
			}
			
			totalDays = 0;
			// 3. 1일 부터 세며, (아예 물을 안줘도 될수도 있음)
			while(totalTreeDiff>0) {
				totalDays++;
				
				// 3-1. 홀수날이라면,
				if((totalDays%2) == 1) {
					boolean watered = false;
					
					// 3-1-1. 나무 높이 차이가 홀수인 나무 중 하나에 물을 주기
					for (int tree = 0; tree < treeNum; tree++) {
				        if (treeHeight[tree] % 2 == 1) {
				            treeHeight[tree] -= 1;
				            totalTreeDiff -= 1;
				            watered = true;
				            break; // 찾았으면 오늘 종료
				        }
				    }

				    // 3-1-2. (홀수인 나무가 없다면) 짝수인 나무 중 2보다 큰 나무 중 하나에 물을 주기
				    if (!watered) {
				        for (int tree = 0; tree < treeNum; tree++) {
				            if (treeHeight[tree] > 2 && treeHeight[tree] % 2 == 0) {
				                treeHeight[tree] -= 1;
				                totalTreeDiff -= 1;
				                watered = true;
				                break; // 찾았으면 오늘 종료
				            }
				        }
				    }
				    // 3-1-3. (위의 경우가 모두 없다면) 아무것도 안하고 다음 날로 넘기기
				}
				
				// 3-2. 짝수 날이라면,
				if((totalDays%2) == 0) {
					
					for(int tree=0; tree<treeNum; tree++) {
						// 3-2-1. 2 이상 남은 나무가 있다면 바로 물을 주고 종료
				        if (treeHeight[tree] >= 2) {
				            treeHeight[tree] -= 2;
				            totalTreeDiff -= 2;
				            break;
				        }
						
						// 3-1-2. (위의 경우가 모두 없다면) 아무것도 안하고 다음 날로 넘기기
					}
				}

			}
			System.out.println("#"+t+" "+totalDays);
		}
	}

}
