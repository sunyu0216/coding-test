import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * SWEA 7465. 창용 마을 무리의 개수
 * 
 * 1. 입력받기
 * 	1-1. 테스트케이스 T
 * 	1-2. 마을 사람의 수 peopleNum, 관계 개수  relationNum
 * 	1-3. 관계 개수만큼 반복하며
 * 		1-3-1. 서로 알고잇는 두 사람의 부모를 더 작은 사람 번호로 해주기
 * 
 * 2. 사람 수만큼 자기 자신을 부모로 초기화시켜주기 parent[i] = i
 * 
 * 3. 집합의 개수를 세어서 반환
 *
 */
public class Solution {
	static int T;
	static int peopleNum;
	static int relationNum;
	static int[] parent;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			peopleNum = Integer.parseInt(st.nextToken());
			relationNum = Integer.parseInt(st.nextToken());
			
			parent = new int[peopleNum+1];
			for(int i=0; i<peopleNum+1; i++) {
				parent[i] = i;
			}
			
			for(int r=0; r<relationNum; r++) {
				st = new StringTokenizer(br.readLine());
				
				int p1 = Integer.parseInt(st.nextToken());
				int p2 = Integer.parseInt(st.nextToken());
				
				union(p1, p2);
			}
			
			int count = 0;
			for (int i = 1; i <= peopleNum; i++) {
			    if (parent[i] == i) count++;
			}
			
			System.out.println("#"+t+" "+count);
		}
	}
	
	// 2. 부모를 계속 타고 올라가서 최종 부모를 찾기
	public static int find(int x) {
	    if (parent[x] == x) return x;
	    
	    return parent[x] = find(parent[x]);
	}
	
	
	// 3. 무리 중 대장을 찾기
	public static void union(int x, int y) {
	    int rootX = find(x);
	    int rootY = find(y);
	    
	    // 대장이 서로 다르다면 한쪽으로 합병!
	    if (rootX != rootY) {
	        if (rootX < rootY) parent[rootY] = rootX;
	        else parent[rootX] = rootY;
	    }
	}

}
