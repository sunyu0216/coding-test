import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author seonyu
 * BOJ 1099. 알 수 없는 문장
 * 
 * 1. 입력받기
 * 
 * 2. DP[idx] = 맨 앞부터 idx번째 인덱스까지 해석하기 위해 필요한 비용의 최솟값
 * 	2-1. DP[0] = 0, 나머지는 INF 값으로 초기화
 * 	2-2. 어디까지 만들지 반복문을 돌리기 j
 * 		2-2-1. 어디서부터 자를지 시작점 반복문 돌리기 i
 * 			2-2-1-1. i부터 j까지에 해당하는 문자열 추출하기
 * 			2-2-1-2. DP[i]가 완성되지 않았다면 pass
 * 			2-2-1-3. 주어진 모든 단어들을 순회 word
 * 				2-2-1-3-1. 만약 i부터 j까지에 해당되는 문자열과 word 길이가 다르다면 pass
 * 				2-2-1-3-2. 알파벳 구성이 같다면 비용구하는 함수 호출 / 구성이 다르면 비용은 -1 
 * 				2-2-1-3-3. 비용이 -1이 아닐때 DP[j] = Math.min(DP[j], DP[i]+cost)
 *
 */
public class Main {
	static String sentence;
	static List<String> words;
	static int wordNum;
	static int[] DP;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sentence = br.readLine();
		wordNum = Integer.parseInt(br.readLine());
		
		words = new ArrayList<>();
		for(int w=0; w<wordNum; w++) {
			words.add(br.readLine());
		}// 입력 끝
		
		DP = new int[sentence.length()+1];
		int INF = 987654321;
		Arrays.fill(DP, INF);
		DP[0] = 0;
		
		for(int j=1; j<=sentence.length(); j++) { // 마지막 단어까지 돌아야 하므로 부등호 주의
			for(int i=0; i<j; i++) {
				String currSentence = sentence.substring(i, j);
				
				if(DP[i] == INF) continue;
				
				for(String word: words) {
					if(word.length() != currSentence.length()) continue;
					
					int cost = getCost(currSentence, word);
					
					if(cost != -1) {
						DP[j] = Math.min(DP[j], DP[i] + cost); // 최적의 비용을 갱신
					}
				}
			}
		}
		
		System.out.println(DP[sentence.length()] == INF ? -1 : DP[sentence.length()]);
		
	}
	
	
	// 2가지를 검사해야함 
	// 1) 이미 길이 검사는 된거니까, 알파벳 구성이 똑같은지 확인해야 함.
	// -> int[26] apb
	// 2) 자리가 다른 알파벳을 세어야 함.
	public static int getCost(String sen, String word) {
		int[] apb = new int[26];
		
		for(int s=0; s<sen.length(); s++) {
			// sen 단어에 있는 알파벳은 +1을 해주고 word에 있는 알파벳은 -1을 해서 총 다 0이 나와야 함.
			apb[sen.charAt(s) - 'a']++;
			apb[word.charAt(s) - 'a']--;
			
		}
		
		for(int check: apb) {
			if(check != 0) return -1;
		}
		
		int cost = 0;
		for(int i=0; i<sen.length(); i++) {
			if(sen.charAt(i) != word.charAt(i)) {
				cost++;
			}
		}
		return cost;
		
	}

}
