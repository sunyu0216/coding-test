import java.util.Scanner;
import java.io.FileInputStream;


class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int testcase = sc.nextInt();

        for(int t=1; t<=testcase; t++){
        	sb.setLength(0); 
        	int cardsNum = sc.nextInt();
        	String[] firstCards;
        	String[] secondCards;
        	if(cardsNum%2 == 0) {
        		firstCards = new String[cardsNum/2];
        		secondCards = new String[cardsNum/2];
        		for(int i=0; i<cardsNum/2; i++) {
        			firstCards[i] = sc.next();
        		}
        		for(int i=0; i<cardsNum/2; i++) {
        			secondCards[i] = sc.next();
        		}
        	}else {
        		firstCards = new String[cardsNum/2 + 1];
        		secondCards = new String[cardsNum/2];
        		for(int i=0; i<cardsNum/2 +1; i++) {
        			firstCards[i] = sc.next();
        		}
        		for(int i=0; i<cardsNum/2; i++) {
        			secondCards[i] = sc.next();
        		}
        	}
        	
        	for(int p=0; p<cardsNum/2; p++) {
        		sb.append(firstCards[p]+" ");
        		sb.append(secondCards[p]+" ");
        	}
        	if(cardsNum%2 != 0) {
        		sb.append(firstCards[cardsNum/2]);
        	}
        	System.out.println("#"+t+" "+sb.toString());
        }
	}
}