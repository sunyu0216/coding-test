import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * BOJ 1654. 랜선 자르기
 *
 * 1. 입력받기
 *  1-1. 오영식이 이미 가지고 있는 랜선의 개수 ownedNum, 필요한 랜선의 개수 requiredNum
 *  1-2. 이미 가지고 있는 랜선의 길이 (<= 2^31 -1) -> int lans[]
 *
 * 2. 이분탐색
 *  구해야하는 것: 랜선의 최대 길이 -> 이분탐색이 되는 대상도 랜선의 길이로!
 *  각 랜선의 최대 길이는 약 21억이므로 low=1, mid=(low+high)/2, high=입력받은 랜선 길이 중 가장 긴 길이
 *  2-1. lans를 순회하며,
 *      2-1-1. 현재 기준값으로 잘랐을 때 총 몇개의 랜선이 나오는지 판단
 *      2-1-2. 만들어야 하는 랜선 개수와 비교 후,
 *          a. 더 작다면 기준값 줄이기
 *          b. 더 크거나 같다면 최고 개수 갱신 후 기준값 늘리기
 *          
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 1. 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1-1. 오영식이 이미 가지고 있는 랜선의 개수 ownedNum, 필요한 랜선의 개수 requiredNum
        int ownedNum = Integer.parseInt(st.nextToken());
        int requiredNum = Integer.parseInt(st.nextToken());

        // 1-2. 이미 가지고 있는 랜선의 길이 (<= 2^31 -1) -> int lans[]
        long high=0; // 이분탐색 용
        int[] lans = new int[ownedNum];
        for(int i=0; i<ownedNum; i++){
            int len = Integer.parseInt(br.readLine());
            lans[i] = len;
            high = Math.max(high, len);
        }

        // 2. 이분탐색
        long low=1; long mid=(low+high)/2;
        long ans = 0;

        while(low<=high){
            // 2-1. lans를 순회하며,
            // 2-1-1. 현재 기준값으로 잘랐을 때 총 몇개의 랜선이 나오는지 판단
            long count = 0;
            for(int i=0; i<ownedNum; i++){
                count += lans[i]/mid;
            }

            // 2-1-2. 지금까지의 잘린 랜선 개수와 비교 후,
            if(requiredNum > count){
                // a. 더 작다면 기준값 줄이기
                high = mid-1;
            }else{
                // b. 더 크거나 같다면 최고 개수 갱신 후 기준값 늘리기
                ans = mid;
                low = mid+1;
            }
            mid = (low+high)/2;

        }

        System.out.println(ans);

    }
}
