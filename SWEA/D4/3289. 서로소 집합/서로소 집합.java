import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 3289. 서로소 집합
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 T
 * 	1-2. 자연수 개수 num, 연산 개수 calNum
 * 	1-3. 연산의 개수 만큼 반복하며
 * 
 * 2. 자연수 개수만큼 자기 자신을 부모로 초기화시켜주기 parent[i] = i
 * 
 * 3. 0(합집합)연산이 들어오면
 * 	3-1. 각 숫자의 최고 부모를 구하기
 * 	3-2. 한쪽 부모 밑으로 다른쪽 부모를 넣기
 * 
 * 4. 1(같은 집합에 포함)연산이 들어오면
 * 	4-1. 각 숫자의 최고 부모를 구하기
 * 	4-2. 비교하기
 * 		4-2-1. 같은 집합에 속해있다면 1
 * 		4-2-2. 다른 집합이라면 0을 sb에 넣어주기
 *
 */
public class Solution {
	static int T;
	static int num;
	static int calNum;
	static int[] parent;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			StringBuilder sb = new StringBuilder();
			
			num = Integer.parseInt(st.nextToken());
			calNum = Integer.parseInt(st.nextToken());
			
			// 초기화
			parent = new int[num + 1];
			for(int i=1; i<=num; i++) {
				parent[i] = i;
			}
			
			for(int c=0; c<calNum; c++) {
				st = new StringTokenizer(br.readLine());
				
				int calculation = Integer.parseInt(st.nextToken());
				int num1 = Integer.parseInt(st.nextToken());
				int num2 = Integer.parseInt(st.nextToken());
				
				if(calculation == 0) {
					union(num1, num2);
				}else {
					int root1 = find(num1);
					int root2 = find(num2);
					
					if(root1 == root2) {
						sb.append(1);
					}else {
						sb.append(0);
					}
				}
			}
			System.out.println("#"+t+" "+sb);
		}
	}
	
	// 2. 부모를 계속 타고 올라가서 최종 부모를 찾기
	public static int find(int x) {
	    if (parent[x] == x) return x;
	    
	    return parent[x] = find(parent[x]);
	}

	// 3. 각 팀의 부모를 찾아 하나의 부모로 합치기
	public static void union(int x, int y) {
	    int rootX = find(x);
	    int rootY = find(y);
	    
	    if (rootX != rootY) {
	        parent[rootY] = rootX;
	    }
	}
}
