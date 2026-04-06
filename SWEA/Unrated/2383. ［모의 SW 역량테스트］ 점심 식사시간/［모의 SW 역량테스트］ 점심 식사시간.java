import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author seon-yu
 * SWEA 2383. 점심 식사시간
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 개수 T
 *  1-2. 방 길이 roomSize
 *  1-3. 지도 정보 room[][]
 *      1-3-1. 사람 수 세기 peopleNum
 *      1-3-2. 사람 위치 기록하기 People
 *
 * [흐름]
 * 사람이 최대 10명 밖에 안되고 계단의 수는 무조건 2이므로 조합 2의 10승의 가짓수마다
 * 걸리는 시간을 구하고 그때마다 최초시간 갱신하기
 *
 *
 * 2. 조합(idx)
 *
 * 3. 시간 구하는 함수
 *
 * 4. 전체 시간과 totalTime중 작은걸로 갱신
 *
 */
public class Solution {
    static int T;
    static int roomSize;
    static int peopleNum;
    static List<People> people;
    static int[][] stairs = new int[2][3];
    static int mainTotalTime;

    static class People{
        int idx;
        int x;
        int y;
        int oneDis;
        int twoDis;

        public People(int idx, int x, int y, int oneDis, int twoDis) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.oneDis = oneDis;
            this.twoDis = twoDis;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++){
            mainTotalTime = Integer.MAX_VALUE;

            peopleNum = 0;
            people = new ArrayList<>();

            roomSize = Integer.parseInt(br.readLine());

            int stairIdx = 0;
            for(int i=0; i<roomSize; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j=0; j<roomSize; j++){
                    int value = Integer.parseInt(st.nextToken());
                    if(value == 1){
                        peopleNum++;
                        people.add(new People(peopleNum, i, j, 0, 0));
                    }else if(value > 1){
                        stairs[stairIdx][0] = i;
                        stairs[stairIdx][1] = j;
                        stairs[stairIdx][2] = value;
                        stairIdx++;
                    }
                }
            } // 입력 끝

            // 사람과 계단 사이의 거리 구해주기
            for(People p: people) {
                p.oneDis = Math.abs(stairs[0][0] - p.x) + Math.abs(stairs[0][1] - p.y);
                p.twoDis = Math.abs(stairs[1][0] - p.x) + Math.abs(stairs[1][1] - p.y);
            }

            selected = new boolean[peopleNum+1]; // 사람 1번부터!!
            combination(1);

            System.out.println("#"+t+" "+mainTotalTime);
        }
    }

    /**
     * @param idx
     * 2. 조합(idx)
     *  *  2-1. 기저조건: 모든 사람들이 어떤 계단으로 갈지 결정했다면
     *  *      2-1-1. 해당 사람들의 계단 할당 정보를 시간 구하는 함수로 넘기기
     *  *      2-1-2. return
     *  *  2-2. idx부터 마지막 원소까지 반복
     *  *      2-2-1. 현재 사람을 첫번째 계단에 포함시킬 것인지를 판단 -> selected[idx] = true
     *  *      2-2-2. 재귀호출(idx+1)
     */
    static boolean[] selected;
    public static void combination(int idx){
        if(idx > peopleNum){
            int currTime = findTime();
            mainTotalTime = Math.min(mainTotalTime, currTime);
            return;
        }

        // 1번 계단 선택 (true)
        selected[idx] = true;
        combination(idx + 1);

        // 2번 계단 선택 (false)
        selected[idx] = false;
        combination(idx + 1);
    }

    /**
     * @return
     * 3. 시간 구하는 함수
     *  *  3-1. 1번계단 우선순위큐, 2번계단 우선순위큐
     *  *  3-2. 사람들을 순회하며
     *  *      3-2-1. selected[사람번호] = true이면
     *  *          3-2-1-1. 1번계단 큐에 (1번계단까지의 거리, idx) 넣어주기
     *  *      3-2-2. false이면
     *  *          3-2-2-1. 2번계단 큐에 (2번계단까지의 거리, idx) 넣어주기
     *  *
     *  *  1번계단 큐, 2번계단 큐, time을 만들기
     *  *  3-3. 1번계단 우선순위큐가 빌 때까지
     *  *      3-3-1. arriveTime = 하나 꺼내서 +1 한 값
     *  *      3-3-2. 1번계단 큐의 사이즈가 3이라면
     *  *          3-3-1-1. 큐에서 poll()한 값 outTime 이 arriveTime 보다 더 크다면(기다려야 함)
     *  *              3-3-1-1-1. arriveTime = outTime
     *  *      3-3-2. arriveTime + 계단높이 값을 큐에 추가
     *  *      3-3-3. 1번계단Time을 arriveTime + 계단높이 로 갱신
     *  *  3-4. 2번계단에 대해서도 똑같이 진행 -> 2번계단 Time
     *  *  3-5. 1번계단Time, 2번계단Time 중 더 큰 걸 고르기 totalTime
     */
    public static int findTime(){
        int totalTime = 0;

        PriorityQueue<Integer> oneQ = new PriorityQueue<>();
        PriorityQueue<Integer> twoQ = new PriorityQueue<>();

        // 3-2. 사람들을 순회하며 각 계단까지의 거리 순대로 넣어주기
        for(People p: people){
            if(selected[p.idx]){
                oneQ.offer(p.oneDis);
            }else{
                twoQ.offer(p.twoDis);
            }
        }

        Queue<Integer> oneStairQ = new ArrayDeque<>();
        Queue<Integer> twoStairQ = new ArrayDeque<>();
        int oneTime = 0;
        int twoTime = 0;

        // 3-3. 1번계단 우선순위큐가 빌 때까지
        while(!oneQ.isEmpty()){
            int arriveTime = oneQ.poll() + 1;

            if(oneStairQ.size() == 3){
                int outTime = oneStairQ.poll();
                if(outTime > arriveTime){
                    arriveTime = outTime;
                }
            }
            oneStairQ.offer(arriveTime + stairs[0][2]);
            oneTime = arriveTime + stairs[0][2];
        }

        // 3-4. 2번계단 우선순위큐가 빌 때까지
        while(!twoQ.isEmpty()){
            int arriveTime = twoQ.poll() + 1;

            if(twoStairQ.size() == 3){
                int outTime = twoStairQ.poll();
                if(outTime > arriveTime){
                    arriveTime = outTime;
                }
            }
            twoStairQ.offer(arriveTime + stairs[1][2]);
            twoTime = arriveTime + stairs[1][2];
        }

        // 3-5. 1번계단Time, 2번계단Time 중 더 큰 걸 고르기 totalTime
        totalTime = Math.max(oneTime, twoTime);
        return totalTime;
    }
}
