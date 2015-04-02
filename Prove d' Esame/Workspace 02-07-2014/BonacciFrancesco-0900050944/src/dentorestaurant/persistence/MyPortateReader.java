package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;

public class MyPortateReader implements PortateReader {

	private BufferedReader bufferedReader;
	
	@Override
	public Map<Categoria, List<Portata>> caricaPortate()
			throws MalformedFileException, IOException {
		
		/*A01, Prosciutto e melone, ANTIPASTO, 9.00\n
		* A02, Antipasto di mare, ANTIPASTO, 8.00\n
		* P01, Trenette al pesto, PRIMO, 6.50\n
		*/
		
		HashMap<Categoria, List<Portata>> mappaCategoriaToPortate = new HashMap<Categoria, List<Portata>>();
		
		//ArrayList<Portata> listaPortate = new ArrayList<Portata>();
		
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			
			StringTokenizer stk = new StringTokenizer(line, ",");
			
			String codice = stk.nextToken().trim();
			
			String nome = stk.nextToken().trim();
			
			Categoria categoria = leggiCategoria(stk.nextToken().trim());
			
			double prezzo = leggiPrezzo(stk.nextToken().trim());
			
			Portata portataCorrente = new Portata(codice, nome, categoria, prezzo);
			
			if (mappaCategoriaToPortate.get(categoria) == null) {
				
				//mi creo una nuova lista per quella portata -- solo ora
				ArrayList<Portata> listaPortatePerCategoria = new ArrayList<Portata>();
				
				listaPortatePerCategoria.add(portataCorrente);
				
				mappaCategoriaToPortate.put(categoria, listaPortatePerCategoria);
				
			} else {
				
				//mi ritrovo la lista per quella categoria
				
				mappaCategoriaToPortate.get(categoria).add(portataCorrente);
				
			}
			
		}
		
		if (mappaCategoriaToPortate.size() != 4) {
			
			/**
			 * mi trovo quali sono le categorie non presenti -- le aggiungo
			 * 
			 */
			
			for (Categoria categoria : Categoria.values()) {
				
				if (!mappaCategoriaToPortate.containsKey(categoria)) {
					
					mappaCategoriaToPortate.put(categoria, new ArrayList<Portata>());
					
				}
				
			}
			
			
		}
		
		return mappaCategoriaToPortate;
	}

	private double leggiPrezzo(String stringaPrezzo) throws MalformedFileException {

		double prezzo;
		
		try {
			
			prezzo = Double.parseDouble(stringaPrezzo);
			
		} catch (NumberFormatException e) {
			
			throw new MalformedFileException("impossibile convertire prezzo", e);
			
		}
		
		return prezzo;
	}

	private Categoria leggiCategoria(String stringaCategoria) throws MalformedFileException {
		
		try {
			
			return Categoria.valueOf(stringaCategoria);
			
		} catch (IllegalArgumentException e) {
			
			throw new MalformedFileException("Categoria non valida", e);
			
		}
		
	}

	@Override
	public void close() {
		
		try {
			
			bufferedReader.close();
				
		} catch (Exception e) {
				
		}

	}

	public MyPortateReader(Reader reader) {

		if (reader == null)
			throw new IllegalArgumentException();
		
		this.bufferedReader = new BufferedReader(reader);
		
	}

	
	
}
