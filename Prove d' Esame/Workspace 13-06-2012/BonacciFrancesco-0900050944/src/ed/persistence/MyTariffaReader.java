package ed.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import ed.model.Tariffa;
import ed.model.TariffaAConsumo;
import ed.model.TariffaFlat;

public class MyTariffaReader implements TariffaReader {

	private final String tagFLAT = "FLAT";
	private final String tagCONS = "A CONSUMO";
	
	@Override
	public Collection<Tariffa> leggiTariffe(Reader reader) throws IOException,
			BadFileFormatException {

		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Tariffa> tariffeList = new ArrayList<Tariffa>();
		Tariffa tariffa;
		
		while ((tariffa = readTariffa(bufferedReader)) != null) {
			
			tariffeList.add(tariffa);
			
		}
		
		return tariffeList;
		
	}
	
	/**
	 * FLAT / CASA MINI    / SOGLIA 150 / PREZZO 29.00 / EXTRA 25 
	 * FLAT / CASA CLASSIC / SOGLIA 250 / PREZZO 39.00 / EXTRA 25
	 * A CONSUMO / MONORARIA 1 / PREZZO 14.00
	 *  
	 */
	private Tariffa readTariffa(BufferedReader bufferedReader) throws BadFileFormatException, IOException{

		Tariffa tar = null;
		
		String line = bufferedReader.readLine();
			
		if (line == null || line.trim().isEmpty())
			return null;
		
		StringTokenizer stk = new StringTokenizer(line, "/");
		
		String tipoTar = stk.nextToken().trim();
		
		if (tipoTar.equals(tagFLAT)) {
			//CASA MINI    / SOGLIA 150 / PREZZO 29.00 / EXTRA 25 
			//FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00\n
			String nome = stk.nextToken().trim();
			
			double soglia = leggiSoglia(stk.nextToken().trim());
			
			double prezzoBase = leggiPrezzoBase(stk.nextToken().trim());
			
			if (!stk.hasMoreTokens())
				throw new BadFileFormatException("manca token extra!");
			
			double prezzoExtra = leggiPrezzoExtra(stk.nextToken().trim()) / 100;
			
			tar = new TariffaFlat(nome, prezzoBase, soglia, prezzoExtra);
			
			
		} else if (tipoTar.equals(tagCONS)) {
			//MONORARIA 1 / PREZZO 14.00
			//MONORARIA 2 / PREZZO 11.90 / 16.20 OLTRE 150
			
			String nome = stk.nextToken().trim();
			
			double prezzoBase = leggiPrezzoBase(stk.nextToken().trim()) / 100;
			
			if (stk.hasMoreTokens()) {
				
				String lastToken = stk.nextToken();
				
				StringTokenizer tok = new StringTokenizer(lastToken);
				
				double prezzoExtra;
				double soglia;
				
				try {
					
					prezzoExtra = Double.parseDouble(tok.nextToken().trim()) / 100;
					
					if (!tok.nextToken().equals("OLTRE")) {
						
						throw new BadFileFormatException("manca tag oltre");
						
					}
					
					soglia = Double.parseDouble(tok.nextToken().trim());
					
				} catch (NumberFormatException e) {
					
					throw new BadFileFormatException("errore conversione prezzo extra e/o soglia");
					
				}
		
				tar = new TariffaAConsumo(nome, prezzoBase, soglia, prezzoExtra);
	
			} else if (!stk.hasMoreTokens()) {
				
				tar = new TariffaAConsumo(nome, prezzoBase);
				
			}
			
			
		} else {
			
			throw new BadFileFormatException("manca tag tipo tariffa");
			
		}
		
		return tar;
	}

	private double leggiPrezzoExtra(String tokenPrezzoExtra) throws BadFileFormatException {
		// TODO EXTRA 25
		
		StringTokenizer tok = new StringTokenizer(tokenPrezzoExtra);
		
		if (tok.countTokens() != 2)
			throw new BadFileFormatException("numero errato token per prezzo extra");
		
		if (!tok.nextToken().equals("EXTRA"))
			throw new BadFileFormatException("Manca tag extra");
		
		try {
			
			return Double.parseDouble(tok.nextToken());
			
		} catch (NumberFormatException e) {
			
			throw new BadFileFormatException("errore conversione prezzo extra");
			
		}
		
	}

	private double leggiPrezzoBase(String tokenPrezzoBase) throws BadFileFormatException {
		// TODO PREZZO 29.00
		
		StringTokenizer tok = new StringTokenizer(tokenPrezzoBase);
		
		if (tok.countTokens() != 2)
			throw new BadFileFormatException("numero errato token per prezzo base");
		
		if (!tok.nextToken().equals("PREZZO"))
			throw new BadFileFormatException("Manca tag prezzo");
		
		try {
			
			return Double.parseDouble(tok.nextToken());
			
		} catch (NumberFormatException e) {
			
			throw new BadFileFormatException("errore conversione prezzo base");
			
		}
		
	}

	private double leggiSoglia(String tokenSoglia) throws BadFileFormatException {
		// SOGLIA 150
		
		StringTokenizer tok = new StringTokenizer(tokenSoglia);
		
		if (tok.countTokens() != 2)
			throw new BadFileFormatException("numero errato token per soglia");
		
		if (!tok.nextToken().equals("SOGLIA"))
			throw new BadFileFormatException("Manca tag soglia");
		
		try {
			
			return Double.parseDouble(tok.nextToken());
			
		} catch (NumberFormatException e) {
			
			throw new BadFileFormatException("errore conversione soglia");
			
		}
		
		
		
	}

}
