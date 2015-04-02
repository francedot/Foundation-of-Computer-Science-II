import java.util.StringTokenizer;


public class EsempiToken {

	public static void main(String[] args) {

		String prova = "ciao mi chiamo francesco, tu come ti chiami?";
		
		//voglio tokenizzare le singole parole
		//divido prima la stringa separando per ','
		//divido poi i due token ottenuti per ' '
		
		String[] splits = prova.split(",");
		
		System.out.println(splits[0]);
		System.out.println(splits[1].trim());
		
		StringTokenizer stk1 = new StringTokenizer(splits[0], " ");
		StringTokenizer stk2 = new StringTokenizer(splits[1], " ?");
		
		System.out.println("\nSeguono le singole parole:\n");
		
		while (stk1.hasMoreTokens()) {
			
			System.out.println(stk1.nextToken().trim());
			
		}
		
		while (stk2.hasMoreTokens()) {
			
			System.out.println(stk2.nextToken().trim());
			
		}
		
		
	}

}
