import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author seonyu
 * 
 * 1. 해당 학생번호가 사진틀에 있는지 알 수 있어야 함
 * 2. 추천번호가 낮은 순대로 정렬되어야 함
 * 3. 추천번호가 같다면 오래된 순서대로 정렬되어야 함
 * 4. 마지막 정답 출력은 학생 번호 오름차순으로 출력해야 함
 * 
 * @see #Student 클래스
 * 1. 필드 구성: 학생 번호(id), 추천수(count), 게시 시간(time)
 * 2. Comparable 구현: 삭제 우선순위(추천수 오름차순 -> 시간 오름차순) 정의
 * 
 * @see #main(String[])
 * 1. Student 객체를 배열로 가지는 ArrayList 생성
 * 
 * 2. 사진틀 개수와 전체 추천받은 횟수 입력받기
 * 
 * 3. for(추천받은 학생들 번호)
 * 		3-1. 이미 사진틀에 있는 학생인지 확인 -> 있으면 추천받은 횟수 증가
 * 		3-2. 사진틀에 없다면 새로 입력할 자리가 있는지 확인 -> 있으면 추가
 * 		3-3. 자리 없으면 0번째 인덱스에 있는 학생 객체 삭제 후 추가
 * 
 * 4. Collections.sort()로 학생번호 오름차순대로 정렬 후 출력
 * 
 */

public class Main {
	static class Student implements Comparable<Student>{
		int id, count, time;

	    public Student(int id, int count, int time) {
	        this.id = id;
	        this.count = count;
	        this.time = time;
	    }
	    
	    @Override
	    public int compareTo(Student o) {
	        if (this.count == o.count) {
	            return this.time - o.time; // 시간 오름차순
	        }
	        return this.count - o.count; // 추천수 오름차순
	    }
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		List<Student> students = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int photoNum = Integer.parseInt(br.readLine());
		int recommendedNum = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int r=0; r<recommendedNum; r++) {
			int currentId = Integer.parseInt(st.nextToken());
            boolean exists = false;

            // 1. 이미 사진틀에 있는지 확인
            for (Student s : students) {
                if (s.id == currentId) {
                    s.count++;
                    exists = true;
                    break;
                }
            }
			
            // 2. 사진틀에 없다면 새로 추가
            if (!exists) {
                if (students.size() >= photoNum) {
                    // 자리가 없으면 정렬 후 가장 우선순위 낮은 학생(0번) 삭제
                    Collections.sort(students); 
                    students.remove(0);
                }
                // r을 '들어온 시간'으로 활용 (항상 증가하므로 안전함)
                students.add(new Student(currentId, 1, r));
            }
		}
		
		// 3. 사진틀에 있는 학생들을 학생 번호 순으로 오름차순 출력
		Collections.sort(students, (a,b) -> {
			return a.id-b.id;
		});
		
		for (Student s : students) {
            System.out.print(s.id + " ");
        }
	}

}
