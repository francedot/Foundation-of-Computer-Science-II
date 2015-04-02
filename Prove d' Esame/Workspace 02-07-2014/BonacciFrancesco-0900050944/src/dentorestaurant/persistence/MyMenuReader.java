package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Portata;

public class MyMenuReader implements MenuReader {

	private BufferedReader bufferedReader;
	private final String startTag = "MENU";
	private final String endTag = "END MENU";
	
	@Override
	public List<Menu> caricaMenu(Map<Categoria, List<Portata>> mappaPortate)
			throws IOException, MalformedFileException {
		
		ArrayList<Menu> listaMenu = new ArrayList<Menu>();
		
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			
			
			if (!line.split(" ")[0].equals(startTag)) {
				
				throw new MalformedFileException("manca tag menu");
				
			}
			
			String nomeMenu = line.split(" ")[1];
			
			Menu menu = leggiMenu(nomeMenu, mappaPortate);
			
			listaMenu.add(menu);
			
		}
		
		return listaMenu;
	}

	private Menu leggiMenu(String nomeMenu, Map<Categoria, List<Portata>> mappaPortate) throws MalformedFileException, IOException {
		
		Menu menu = new Menu(nomeMenu);
		String line;
		while (!(line = bufferedReader.readLine()).contains(endTag)) {
			
			StringTokenizer stk = new StringTokenizer(line, ":");
		
			Categoria categoria = leggiCategoria(stk.nextToken().trim());
			
			List<Portata> portatePerCategoria = menu.getPortate(categoria);
			
			String stringaCodiciPortate = stk.nextToken().trim();
			
			stk = new StringTokenizer(stringaCodiciPortate, ",");
			
			while (stk.hasMoreElements()) {
				
				Portata p = getPortataFromCodice(stk.nextToken().trim(), mappaPortate, categoria);
				
				portatePerCategoria.add(p);
				
			}
			
		}
		
		return menu;
	}

	private Portata getPortataFromCodice(String codicePortata,
			Map<Categoria, List<Portata>> mappaPortate, Categoria categoria) throws MalformedFileException {
		
		Portata portata = null;
		
		for (Portata p : mappaPortate.get(categoria)) {
			
			if (p.getCodice().equals(codicePortata)) {
				
				portata = p;
				
			}
			
		}
		
		if (portata == null) {
			
			throw new MalformedFileException("portata mancante in mappa");
			
		} else {
			
			return portata;
			
		}
		
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

	public MyMenuReader(Reader reader) {
		
		if (reader == null)
			throw new IllegalArgumentException();
		
		this.bufferedReader = new BufferedReader(reader);
		
	}

	
	
}
