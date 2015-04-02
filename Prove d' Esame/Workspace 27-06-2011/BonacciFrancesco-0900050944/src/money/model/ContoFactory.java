package money.model;

public class ContoFactory {

	private ContoFactory() {
		
		
		
	}

	public static String[] getNames() {
		
		return new String[] { "cartacredito", "contocorrente", "liquidi" };
		
	}
	
	public static Conto create(String tipo, String nome, double param) {
		
		if (tipo.equalsIgnoreCase("cartadicredito")) {
			
			return new CartaCredito(nome, param);
			
		} else if (tipo.equalsIgnoreCase("contocorrente")) {
			
			return new ContoCorrente(nome, param);
			
		} else if (tipo.equalsIgnoreCase("liquidi")) {
			
			return new Liquidi(nome);
			
		}
		
		throw new IllegalArgumentException();
	}
	
}
