import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * SWEA 5644. 무선충전
 *
 *
 * 0. 메인함수 플로우
 *  0-1. 입력받기
 *  0-2. 맵에 충전기 번호 표시 함수 호출 checkChargingRange()
 *  0-3. 정답 변수 초기화 후,
 *  0-4. 사용자 이동 함수 호출 moveuser()
 *  0-5. 정답 출력
 *
 *
 * 1. 입력받기
 *  1-1. 테스트케이스 개수 T
 *  1-2. 총 이동 시간 totalMoveTime, 충전기의 개수 chargerNum
 *  1-3. 사용자 A의 이동 정보 userA[]
 *  1-4. 사용자 B의 이동 정보 userB[]
 *  1-5. 각 충전기의 좌표(r, c), 충전 범위 range, 충전량 amount
 *  1-6. 전체 맵은 ArrayList[][]
 *
 *
 * @see #checkChargingRange()
 * 2. 전체 맵에 충전 범위에 따라 충전기 인덱스 번호를 표시해주기
 *
 * @see #moveUser()
 * 3. 전체 이동시간 동안 반복하며, 사용자를 해당 방향에 맞게 이동시켜주기
 *  3-1. 이동 전 시작 위치에서도 충전 가능한지 체크
 *  3-2. 사용자를 초당 이동시키면서, 이번 시간대에 얻은 충전량 더해주기
 *
 * @see #getCharge()
 * 4. 충전시키는 함수
 *  4-1. 해당 좌표에 충전기가 있는지 조회
 *      4-1-1. 없다면 오류 방지와 반복문 편의성을 위해 빈 리스트 할당
 *      4-1-2. 있다면 pass
 *  4-2. 이중 반복문을 돌며, A의 이용가능한 충전기와 B의 이용가능한 충전기의 조합을 검토
 *      4-2-1. 둘다 같은 충전기를 사용할 땐 충전량을 나눠서
 *      4-2-2. 다른 충전기를 사용할 땐 각자 충전량을 계산
 *      4-2-3. 이후 최대값을 갱신해주기 => 해당 초에 대한 충전량으로 결정
 *
 * @see #getAmount(int) 
 * 5. 해당 충전기에서 충전량을 계산해주는 함수
 *
 */
public class Solution {
    static int totalMoveTime;
    static int chargerNum;
    static ArrayList<Integer>[][] map = new ArrayList[11][11];
    static Charger[] chargerList;
    static int answer;

    // 충전기 클래스
    static class Charger{
        int r;
        int c;
        int range;
        int amount;
        int id;

        Charger(int r, int c, int range, int amount, int id){
            this.r = r;
            this.c = c;
            this.range = range;
            this.amount = amount;
            this.id = id;
        }
    }

    // 사용자 클래스
    static class User{
        int r;
        int c;
        int[] moveInfo;

        public User(int r, int c, int[] moveInfo) {
            this.r = r;
            this.c = c;
            this.moveInfo = moveInfo;
        }
    }
    static User userA;
    static User userB;

    // 0. 메인함수 플로우
    public static void main(String[] args) throws IOException {
        // 0-1. 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){
            // 0-1. 입력받기 및 초기화
            init(br);

            // 0-2. 맵에 충전기 번호 표시 함수 호출
            checkChargingRange();

            // 0-3. 정답 변수 초기화 후,
            answer = 0;

            // 0-4. 사용자 이동 함수 호출
            moveUser();

            // 0-5. 정답 출력
            System.out.println("#"+t+" "+answer);
        }

    }

    // 1. 입력받기 및 초기화 함수
    static void init(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        totalMoveTime = Integer.parseInt(st.nextToken());
        chargerNum = Integer.parseInt(st.nextToken());
        answer = 0;

        // [중요] 맵 초기화: 이전 테스트 케이스의 충전기 정보 삭제
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                map[i][j] = null;
            }
        }

        // User A 이동 정보
        int[] moveA = new int[totalMoveTime];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < totalMoveTime; i++) moveA[i] = Integer.parseInt(st.nextToken());
        userA = new User(1, 1, moveA);

        // User B 이동 정보
        int[] moveB = new int[totalMoveTime];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < totalMoveTime; i++) moveB[i] = Integer.parseInt(st.nextToken());
        userB = new User(10, 10, moveB);

        // 충전기 정보
        chargerList = new Charger[chargerNum];
        for (int i = 0; i < chargerNum; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken()); // X
            int r = Integer.parseInt(st.nextToken()); // Y
            int range = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());
            chargerList[i] = new Charger(r, c, range, amount, i + 1);
        }
    }

    // 2. 전체 맵에 충전 범위에 따라 충전기 인덱스 번호를 표시해주기
    static void checkChargingRange(){
        for(Charger C: chargerList){
            for(int i=1; i<=10; i++){
                for(int j=1; j<=10; j++){
                    int distance = Math.abs(i-C.r) + Math.abs(j-C.c);
                    if(distance<=C.range){
                        // 충전가능
                        if(map[i][j]==null){
                            map[i][j] = new ArrayList<>();
                        }
                        map[i][j].add(C.id);
                    }
                }
            }
        }
    }

    // 3. 전체 이동시간 동안 반복하며, 사용자 이동시켜주는 함수
    static void moveUser(){
        int[] dx = {0, -1, 0, 1, 0};
        int[] dy = {0, 0, 1, 0, -1};

        // 3-1. 이동 전 시작 위치에서도 충전 가능한지 체크
        answer += getCharge();

        // 3-2. 사용자를 초당 이동시키면서,
        for(int time=0; time<totalMoveTime; time++){
            int directionA = userA.moveInfo[time];
            int directionB = userB.moveInfo[time];

            userA.r = userA.r + dx[directionA];
            userA.c = userA.c + dy[directionA];

            userB.r = userB.r + dx[directionB];
            userB.c = userB.c + dy[directionB];

            // 이번 시간대에 얻은 충전량 더해주기
            answer += getCharge();
        }
    }

    // 4. 충전시키는 함수
    static int getCharge(){
        int maxChargeForThisTime = 0;
        
        // 4-1. 해당 좌표에 충전기가 있는지 조회
        ArrayList<Integer> listA = map[userA.r][userA.c];
        ArrayList<Integer> listB = map[userB.r][userB.c];

        // 4-1-1. 없다면 오류 방지와 반복문 편의성을 위해 빈 리스트 할당
        if (listA == null) { listA = new ArrayList<>(); listA.add(0); }
        if (listB == null) { listB = new ArrayList<>(); listB.add(0); }

        // 4-2. 이중 반복문을 돌며, A의 이용가능한 충전기와 B의 이용가능한 충전기의 조합을 검토
        for (int aBC : listA) {
            for (int bBC : listB) {
                int currentSum = 0;

                if (aBC == bBC) {
                    // 4-2-1. 둘다 같은 충전기를 사용할 땐 충전량을 나눠서 = 결국 한 번 더하기
                    currentSum = getAmount(aBC);
                } else {
                    // 4-2-2. 다른 충전기를 사용할 땐 각자 충전량을 계산 = 각 충전량 더해주기
                    currentSum = getAmount(aBC) + getAmount(bBC);
                }

                // 4-2-3. 이후 최대값을 갱신해주기 => 해당 초에 대한 충전량으로 결정
                maxChargeForThisTime = Math.max(maxChargeForThisTime, currentSum);
            }
        }
        return maxChargeForThisTime;
    }

    // 5. 충전량 구해주는 함수
    static int getAmount(int BCid){
        if(BCid == 0) return 0;
        return chargerList[BCid - 1].amount;
    }
}
