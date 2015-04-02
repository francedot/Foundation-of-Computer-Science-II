package happybank.persistence;

import happybank.model.ContoCorrente;
import happybank.model.TesseraBancomat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MyTesseraBancomatReader implements TesseraBancomatReader {

	/**
	 * dichiara un metodo read a due argomenti, un Reader da cui leggere e un
	 * oggetto Map usato dall’algoritmo di caricamento per ottenere gli oggetti ContoCorrente a partire dai numeri di conto
	 * corrente.
	 */
	@Override
	public List<TesseraBancomat> read(Reader reader, Map<String, ContoCorrente> contoCorrenteMap) throws IOException, BadFileFormatException {
		
		if (reader == null)
			throw new IllegalArgumentException();
		
		BufferedReader br = new BufferedReader(reader);
		
		List<TesseraBancomat> tessereBancomatList = new ArrayList<TesseraBancomat>();
		TesseraBancomat tesseraBancomat;
		
		while ((tesseraBancomat = readTesseraBancomat(br, contoCorrenteMap)) != null) {
			
			tessereBancomatList.add(tesseraBancomat);
			
		}
		
		return tessereBancomatList;
		
	}

	private TesseraBancomat readTesseraBancomat(BufferedReader br, Map<String, ContoCorrente> contoCorrenteMap) throws IOException, BadFileFormatException {

		//1234567 02026 250
		//3214576 94715 400
		
		String line = br.readLine();
		
		if (line == null || line.trim().isEmpty())	//impostazione corretta! se metti l'stk prima alla fine hai nullpointerexception
			return null;
		
		StringTokenizer stk = new StringTokenizer(line, " ");
		
		TesseraBancomat tessera = null;
		
		try {
			
			String numeroCC = stk.nextToken();
			ContoCorrente contoCorrente = contoCorrenteMap.get(numeroCC);
			
			String pin = stk.nextToken().trim();
			
			int maxPrelevabile = Integer.parseInt(stk.nextToken().trim());
			
			tessera = new TesseraBancomat(pin, maxPrelevabile, contoCorrente);
	
			
		}
		catch (NumberFormatException | NoSuchElementException e) {
			
			throw new BadFileFormatException(e);
			
		}
		
		return tessera;
		
	}

}
