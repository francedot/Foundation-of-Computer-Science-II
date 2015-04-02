package money.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import money.model.Conto;
import money.model.ContoFactory;

public class MyContiReader implements ContiReader {

	@Override
	public Collection<Conto> readAll(Reader reader) throws IOException,
			InvalidDataException {
		
		//#define ccTopolino \t\t contocorrente 2000\n" + "#define\tvisaPaperino\t\t cartadicredito \t 1500\n" + "#define \t  borsaMinnie	 liquidi
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Conto> ContoList = new ArrayList<Conto>();
		Conto Conto;
		
		while ((Conto = readConto(bufferedReader)) != null) {
			
			ContoList.add(Conto);
			
		}
		
		bufferedReader.close();
		return ContoList;
		
	}

	private Conto readConto(BufferedReader bufferedReader) throws IOException, InvalidDataException {
		// TODO #define ccTopolino \t\t contocorrente 2000\n#define\tvisaPaperino\t\t cartadicredito \t 1500\n
		Conto target = null;
		
		try {
		
			String line = bufferedReader.readLine();		
			if (line == null || line.trim().isEmpty())
				return null;

			StringTokenizer stk = new StringTokenizer(line);
		
			if (stk.countTokens() < 3 ) {
			
				throw new InvalidDataException("numero tokens minore di 3");
			
			}
		
			if (stk.countTokens() > 4 ) {
				
				throw new InvalidDataException("numero tokens maggiore di 4");
			
			}
			
			if (!stk.nextToken().trim().equals("#define")) {
			
				throw new InvalidDataException("manca tag '#define'");
			
			}
		
			String nomeConto = stk.nextToken().trim();
		
			if (stk.countTokens() == 1) {
			
				String tipo = stk.nextToken().trim();
			
				target = ContoFactory.create(tipo, nomeConto, 0);
			
			} else if (stk.countTokens() == 2) {
			
				String tipo = stk.nextToken().trim();
			
				double param = 0;
				
				try {
				
					param = Double.parseDouble(stk.nextToken());
				
				} catch (NumberFormatException e) {
				
					throw new InvalidDataException("errore conversione parametro", e);
				
				}
				
				target = ContoFactory.create(tipo, nomeConto, param);
			
			}
		
		} catch (IllegalArgumentException e) {
			
			throw new InvalidDataException(e);
			
		} catch (NoSuchElementException e) {
			
			throw new InvalidDataException(e);
			
		}
		
		return target;
	}

}
