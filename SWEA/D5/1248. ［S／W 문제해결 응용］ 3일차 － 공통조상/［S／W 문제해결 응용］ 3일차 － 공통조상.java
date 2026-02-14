import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * SWEA 공통조상
 * 
 * [플로우]
 * 1. 정점개수, 간선개수, 목표로 하는 정점 2개를 입력받기
 * 2. 부모를 인덱스로 갖고 자식정점을 저장하는 children[] 과 자식을 인덱스로 갖고 부모를 저장하는 parent[] 배열 생성
 * 3. 위 배열에 간선의 정보를 각각 저장한다.
 *  ex) 1 2 간선정보가 주어지면
 *       children[1].add(2)
 *       parent[2] = 1
 *  3-1. 이때 하나의 부모에 대한 자식은 여러개가 올 수 있으므로 children은 ArrayList배열로 만들어준다.
 *   
 * 4. 첫번째 노드를 기준으로 시작해
 *  4-1. parent[] 배열을 조회하며 첫번째 노드가 가진 부모를 다 확인
 *  4-2. 찾은 부모를 visited[부모] = true로 바꿔 방문처리
 *  
 * 5. 두번째 노드를 기준으로 시작해
 *  5-1. parent[] 배열을 조회하며 두번째 노드가 가진 부모를 확인하며,
 *  5-2. 이 부모가 visited[부모] = true 인지 확인하고,
 *      5-2-1. 맞다면 stop
 *      5-2-2. 아니라면 계속 찾기
 *  5-3. 이 과정을 반복해 공통 조상인 commonParent를 찾는다.
 *  
 * 
 * 6. 공통조상을 기준으로 시작해
 *  6-1. children[] 배열을 조회하며 각 부모에 있는 자식들의 개수를 다 더해준다.
 * 
 *   
 */
public class Solution {
    static int nodeNum;
    static int edgeNum;
    static int node1;
    static int node2;
    static int[] parent;
    static List<Integer>[] children;
    static boolean[] visited;
    static int commonParent;
    static int subtreeSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){
            // 정점개수, 간선개수, 목표 정점2개 입력받기
            st = new StringTokenizer(br.readLine());

            nodeNum = Integer.parseInt(st.nextToken());
            edgeNum = Integer.parseInt(st.nextToken());
            node1 = Integer.parseInt(st.nextToken());
            node2 = Integer.parseInt(st.nextToken());

            // 간선 정보 입력받기
            st = new StringTokenizer(br.readLine());
            parent = new int[nodeNum+1];
            children = new ArrayList[nodeNum + 1];
            visited = new boolean[nodeNum+1];
            for(int e=0; e<edgeNum; e++){
                int p = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                //System.out.println("부모: "+ p + "자식: "+ c);
                parent[c] = p;
                if(children[p] == null){
                    children[p] = new ArrayList<>();
                }
                children[p].add(c);
            }

            // 첫번째 노드의 부모 방문처리
            int current = node1;
            while(parent[current] != 0){
                int p = parent[current];
                visited[p] = true; // 방문처리
                current = p;
            }

            // 두번재 노드의 부모도 체크하며 같은 부모가 나올 경우 stop
            current = node2;
            while(parent[current] != 0){
                int p = parent[current];
                if(visited[p]){
                    commonParent = p;
                    break;
                }else{
                    current = p;
                }
            }

            // 공통 조상으로부터 다시 쭉 자식들을 조회하며 서브트리의 크기 구하기
            current = commonParent;
            subtreeSize = dfs(current);

            System.out.println("#"+t+" "+commonParent+" "+subtreeSize);

        }
    }
    static int dfs(int current){
        int cnt = 1;

        if (children[current] != null) {
            for (int child : children[current]) {
                cnt += dfs(child); // 자식들의 크기를 더함
            }
        }

        return cnt;

    }
}
