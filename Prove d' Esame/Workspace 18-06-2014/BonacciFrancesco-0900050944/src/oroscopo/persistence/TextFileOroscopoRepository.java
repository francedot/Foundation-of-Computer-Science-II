package oroscopo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class TextFileOroscopoRepository implements OroscopoRepository {
	/**
	 * "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE"
	 */
	
	private final String endTag = "FINE";
	private HashMap<String, ArrayList<Previsione>> mappaSettoreToPrevisioni;
	
	@Override
	public List<Previsione> getPrevisioni(String settore) {
		
		return mappaSettoreToPrevisioni.get(settore);
		
	}
	
	@Override
	public Set<String> getSettori() {
		
		return mappaSettoreToPrevisioni.keySet();
		
	}

	public TextFileOroscopoRepository(Reader baseReader) throws IOException, BadFileFormatException {
		
		//"NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE"
		
		mappaSettoreToPrevisioni = new HashMap<String, ArrayList<Previsione>>();
		
		BufferedReader bufferedReader = new BufferedReader(baseReader);
		
		try {
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
			
				String nomeSettore = line.trim().toLowerCase(); //NOMESEZIONE
			
				ArrayList<Previsione> listaPrevisioniPerSettore = leggiPrevisioniPerSettore(bufferedReader); //till FINE
			
				mappaSettoreToPrevisioni.put(nomeSettore, listaPrevisioniPerSettore);
			
			}	
				
		} catch (Exception e) {
			
			throw new BadFileFormatException(e);
			
		}
		 
		
	}

	private ArrayList<Previsione> leggiPrevisioniPerSettore(
			BufferedReader bufferedReader) throws IOException, BadFileFormatException {

		String line;
		ArrayList<Previsione> listaPrevisioniPerSettore = new ArrayList<Previsione>();
		
		while (!(line = bufferedReader.readLine()).equals(endTag)) { //avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI
			
			if (line == null || line.isEmpty())
				break;
			
			StringTokenizer stk = new StringTokenizer(line, "\t");
			
			String descrizionePrevisione = stk.nextToken().trim(); //avrai la testa un po' altrove
			int valoreFortuna;
			try {
				
				valoreFortuna = Integer.parseInt(stk.nextToken().trim());//4
				
			} catch(NumberFormatException e) {
				
				throw new BadFileFormatException(e);
				
			}
			
			if (stk.hasMoreTokens()) {
			
				HashSet<SegnoZodiacale> segniPerPrevisione = leggiSegni(stk.nextToken().trim());//ARIETE,TORO,GEMELLI
			
				listaPrevisioniPerSettore.add(new Previsione(descrizionePrevisione, valoreFortuna, segniPerPrevisione));
			
			} else {
				
				listaPrevisioniPerSettore.add(new Previsione(descrizionePrevisione, valoreFortuna));
				
			}
		}
		
		return listaPrevisioniPerSettore;
	}

	private HashSet<SegnoZodiacale> leggiSegni(String stringaSegni) throws BadFileFormatException {
		// TODO ARIETE,TORO,GEMELLI
		
		StringTokenizer tok = new StringTokenizer(stringaSegni, ",");
		
		HashSet<SegnoZodiacale> segniPerPrevisione = new HashSet<SegnoZodiacale>();
		
		while (tok.hasMoreTokens()) {
			
			try {
			
				segniPerPrevisione.add(SegnoZodiacale.valueOf(tok.nextToken().trim()));
				
			} catch (IllegalArgumentException e) {
				
				throw new BadFileFormatException(e);
				
			}
		}
		
		return segniPerPrevisione;
		
	}

}
