package dentorestaurant.model;

import java.util.HashMap;
import java.util.Map;

public class Ordine {

	private HashMap<Categoria, Portata> mappaCategoriaToPortata;
	
	private Menu menu;
	private String nomeCliente;
	private double prezzoTotale;
	
	public void aggiungiPortata(Portata portata) {
		
		if (mappaCategoriaToPortata.get(portata.getCategoria()) != null) {
			
			throw new IllegalArgumentException("portata già presete per categoria " + portata.getCategoria().toString());
			
		}
		
		mappaCategoriaToPortata.put(portata.getCategoria(), portata);
		
	}
	
	public Map<Categoria, Portata> getElencoPortate() {
		
		return mappaCategoriaToPortata;
	
	}
	
	public Menu getMenu() {
		
		return menu;
		
	}
	
	public String getNomeCliente() {
		
		return nomeCliente;
		
	}

	public double getPrezzoTotale() {
		
		double prezzo = 0.0;
		
		for (Portata portata : mappaCategoriaToPortata.values()) {
			
			prezzo += portata.getPrezzo();
			
		}
		
		return prezzo;
		
	}
	
	public boolean isValid() {
		
		/**
		 * verificare che l' ordine contiene una portata per ogni categoria
		 */
		
		//quindi che il numero di portate sia della lunghezza dell' enum
		
		return mappaCategoriaToPortata.keySet().size() == Categoria.values().length;
		
		
	}
	
	public Ordine(Menu menu, String nomeCliente) {
		
		if (menu == null) {
			
			throw new IllegalArgumentException("menu = null");
			
		}
		
		if (nomeCliente == null) {
			
			throw new IllegalArgumentException("nome cliente = null");
			
		} else if (nomeCliente.trim().isEmpty()) {
			
			throw new IllegalArgumentException("nome cliente = " +
													"striga vuota");
			
		}
		
		mappaCategoriaToPortata = new HashMap<Categoria, Portata>();
		
		this.menu = menu;
		this.nomeCliente = nomeCliente;
		
	}
	
	public void sostituisciPortata(Portata corrente, Portata futuro) {
		
		/*
		 * cambia una portata (primo argomento) con un’altra (secondo argomento) della stessa categoria
		 */
		
		/*
		 * verificare che portata da sostituire sia presente
		 * le categorie delle due siano uguali
		 * che la nuova portata sia presente nel menu di riferimento
		 */
		
		if (!mappaCategoriaToPortata.get(corrente.getCategoria()).equals(corrente)) {
			
			throw new IllegalArgumentException("portata indicata come corrente non presente nell 'ordine");
			
		}
		
		if (!corrente.getCategoria().equals(futuro.getCategoria())) {
			
			throw new IllegalArgumentException("le due portate non appartengono alla stessa categoria");
			
		}
		
		if (!menu.getPortate(futuro.getCategoria()).contains(futuro)) {
			
			throw new IllegalArgumentException("portata da sostituire non presente nel menu di riferimento");
			
		}
		
		/**
		 * prendo la portata per quella categoria, la elimino e al suo posto metto la nuova portata
		 * 
		 */
		
		mappaCategoriaToPortata.remove(corrente.getCategoria());
		
		mappaCategoriaToPortata.put(futuro.getCategoria(), futuro);
		
	}
	
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.toString());
		
		for (Portata portata : mappaCategoriaToPortata.values()) {
			
			sb.append(portata.toString() + System.lineSeparator());
			
		}
		
		return sb.toString();
		
	}
	
	public String toString() {
		
		return "Nome del Cliente: " + nomeCliente + 
					System.lineSeparator() + "Prezzo: "
						+ prezzoTotale + System.lineSeparator();
				
	}
	
	
	
}
