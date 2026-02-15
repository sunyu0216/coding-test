import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author seonyu
 * BOJ 10816. 숫자 카드2
 *
 * 1. 입력받기
 *  1-1. 상근이가 가진 숫자카드 개수 cardsNum
 *  1-2. 숫자카드에 적혀 있는 정수들 입력받기 cards
 *  1-3. 개수를 구해야 하는 카드 개수 targetCardsNum
 *  1-4. 개수를 구해야 하는 정수들 입력받기 targetCards
 *
 * 2. 숫자 카드에 적혀 있는 정수를 입력받을 때 정수를 키값으로 한 해시맵에 입력받기
 * 3. 개수를 구해야 하는 정수를 키값으로 빈도를 조회해 출력하기
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int cardsNum = Integer.parseInt(br.readLine());
        HashMap<Integer, Integer> cards = new HashMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<cardsNum; i++){
            int value = Integer.parseInt(st.nextToken());
            cards.put(value, cards.getOrDefault(value, 0)+1);
        }

        int targetCardsNum = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int ti=0; ti<targetCardsNum; ti++){
            int value = Integer.parseInt(st.nextToken());
            sb.append(cards.getOrDefault(value, 0)).append(" ");
        }

        System.out.println(sb.toString());

    }
}
