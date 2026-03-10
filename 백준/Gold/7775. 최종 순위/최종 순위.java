import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * BOJ 7775. 최종 순위
 * 
 * 1. 입력받기
 * 	1-1. 총 학생은 studentNum, 모든 점수의 합 totalScore, 상위 학생의 수 k, 점수 종류의 개수 d
 * 	1-2. 점수를 저장할 배열 score[]
 * 
 * 2. 초기 점수를 가장 적게 부여한다 가정하고,
 * 	2-1. 인덱스 0 ~ k-d 만큼 score에 d-1을 부여
 * 	2-2. 인덱스 k-d+1 ~ k-1 만큼 score에 d-2부터 0까지 차례로 부여
 * 	2-3. 인덱스 k ~ studentNum-1 만큼 score에 모두 0 부여
 * 
 * 3. 지금까지 나눠준 점수의 합 구하기, 총 점수에서 남는 점수 remainScore 구하기
 * 4. 남은 점수 배분하기
 * 	4-1. remainScore / k 만큼 상위 k명에게 더해주기
 * 	4-2. remainScore % k
 * 		4-2-1. 나머지가 있다면,
 * 			4-2-1-1. 최고점 k-d+1명에게 나눠줄 수 있다면 배분
 * 			4-2-1-2. 하위그룹에게 똑같이 배분
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int studentNum = Integer.parseInt(st.nextToken());
		int totalScore = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		
		int[] score = new int[studentNum];
		
		
		// 3. 지금까지 나눠준 점수의 합 구하기, 총 점수에서 남는 점수 remainScore 구하기
		int minSum = (k-d+1)*(d-1) + (d-1)*(d-2)/2;
		if(totalScore<minSum) {
			System.out.println("Wrong information");
            return;
		}
		
		int remainScore = totalScore - minSum;
		
		// 4. 남은 점수 배분하기
		int baseScore = remainScore/k;
		int R = remainScore%k;
		
		// 4-1. remainScore / k 만큼 상위 k명에게 더해주기
		for(int i=0; i<=k-d; i++) {
			score[i] = (d-1) + baseScore;
		}
		for(int i=k-d+1; i<=k-1; i++) {
			score[i] = (k-1-i) + baseScore;
		}
		
		// 4-2. remainScore % k 처리
        if (R > 0) {
            int topGroupSize = k - d + 1;
            int addEach = R / topGroupSize;
            
            // 4-2-1-1. 최고점 k-d+1명에게 나눠줄 수 있다면 배분
            for (int i = 0; i < topGroupSize; i++) {
                score[i] += addEach;
            }
            R %= topGroupSize;
            
            // 그래도 남은 나머지 R은 하위 그룹에게 짬 처리
            if (R > 0) {
                if (studentNum == k) {
                    System.out.println("Wrong information");
                    return;
                }
                score[k] = R; 
            }
        }
        
        // 5. 최종 검증: 내림차순(등수)이 깨졌는지 확인
        for (int i = 0; i < studentNum - 1; i++) {
            if (score[i] < score[i + 1]) {
                System.out.println("Wrong information");
                return;
            }
        }
	        
        // 5. 결과 출력
        for (int i = 0; i < studentNum; i++) {
            sb.append(score[i]).append("\n");
        }
        System.out.print(sb);
		
		
		
	}

}

/*
 * 총 학생은 studentNum, 모든 점수의 합 totalScore
 * 상위 k명의 점수 중 서로 다른 점수의 수는 d개
 * 상위 k명의 점수는 나머지 studentNum-k명의 점수보다 크거나 같아야 함

 * [흐름]
 * 상위 k명의 점수 중 서로 다른 점수의 수는 d개
 * 상위 k명의 점수는 나머지 studentNum-k명의 점수보다 크거나 같아야 함
 * 
 * <점수를 가장 아껴서 부여해보면>
 * ex) 상위 5명인데 서로 다른 점수의 개수는 2개
 * - 상위 4명(5-2+1)에게는 모두 같으면서 가장 높은 점수를 부여 -> 점수는 0부터 시작하기에 최고점은 d-1점이 됨.
 * - 상위 중 나머지 1명(d-1)에게는 서로 점수가 달라야 하므로 1씩 차이나는 점수 d-2를 부여(d-2, d-3, ,,, 0)
 * - 나머지 하위 studentNum-k명에게는 모두 0점을 부여
 * - 전체 점수의 합에서 현재까지의 합을 빼보기 -> 남는 점수 
 * - 이 남는 점수를...
 * - 1) 상위 k명에게 공평하게 나눠주기
 * - 2) 남은 점수가 k명에게 나눠주지 못한다면 하위 학생들에게 상위 마지막 학생의 점수를 넘지 않도록 주의하면서 점수 배분
 * - 3) 만약 하위 학생들 집합이 없다면 Wrong Information출력
 * 
 * */