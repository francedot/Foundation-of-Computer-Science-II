package zannonia.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import zannonia.model.Casello;
import zannonia.model.Tratta;

public class MyTratteReader implements TratteReader {

	/**
	 * pc 11 0.60 Piacenza nord, Piacenza sud   id- lunghezza - pedaggio lista caselli
	 * pc-pr 53 3.50 Fiorenzuola, Fidenza, Parma
	 * pr-mo 48 3.10 Canossa, Reggio Emilia, Modena Nord
	 * mo 2 0.00
	 * mo-bo 31 2.40 Modena Sud, Crespellano, Bologna Ovest
	 */
	@Override
	public Map<String, Tratta> readTratte(Reader tratteReader) throws IOException, MalformedFileException {
		
		HashMap<String, Tratta> mappaNomeTratta = new HashMap<String, Tratta>();
		
		BufferedReader br = new BufferedReader(tratteReader);
		try {
			//pr-mo	48	3.10	Canossa\nmo	 	2	0.00\nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n
			String line = null;
			while ((line = br.readLine()) != null) {
				
				if(line.trim().isEmpty())
					throw new MalformedFileException("linea vuota - manca tutto!");
				
				StringTokenizer stk = new StringTokenizer(line, " \t\n\r,");

				String id = stk.nextToken().trim();
				
				double lunghezza = Double.parseDouble(stk.nextToken());
				
				double pedaggio = Double.parseDouble(stk.nextToken());
				
				Tratta corrente = new Tratta(id, pedaggio, lunghezza);
				
				while(stk.hasMoreElements()) {
					
					String nomeCasello = stk.nextToken(",\t\r\n").trim();
					corrente.addCasello(new Casello(nomeCasello));	//n.b controlla allocazioni nuove memorie!!!
					
				}
				
				mappaNomeTratta.put(id.toUpperCase(), corrente);
				
			}

			return mappaNomeTratta;

		} catch (NumberFormatException | NoSuchElementException e ) {
	
			throw new MalformedFileException(e);
			
		}
		finally {
			
			br.close();
			
		}
	}

}