package galliacapocciona.persistence;

import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class MyCollegiReader implements CollegiReader {
	
	@Override
	public List<Collegio> caricaElementi(Reader reader) throws IOException, BadFileFormatException {
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Collegio> collegioList = new ArrayList<Collegio>();
		
		String line = null;
		line = bufferedReader.readLine();
		if(line.trim().isEmpty() || line == null) 
			return null;
		
		StringTokenizer stk = new StringTokenizer(line, " \t\n\r");
		int numPartiti = stk.countTokens();
		
		if (numPartiti < 2)
			throw new BadFileFormatException("non ci sono almeno due partiti");
		
		String[] nomiPartiti = new String[numPartiti];
		
		for (int i = 0; i < numPartiti; i++) {	//se devi ciclare con unico indice in più collezioni usa array!
			
			String nomePartito = stk.nextToken().trim();
			nomiPartiti[i] = nomePartito;
			
		}
		
		Collegio collegio;
		
		while ((collegio = readCollegio(bufferedReader, numPartiti, nomiPartiti)) != null) {
			
			collegioList.add(collegio);
			
		}
		
		return collegioList;
		
	}

	private Collegio readCollegio(BufferedReader bufferedReader, int numPartiti, String[] nomiPartiti) throws IOException, BadFileFormatException {

		String line = bufferedReader.readLine();
		
		if (line == null || line.trim().isEmpty())	//impostazione corretta! se metti l'stk prima alla fine hai nullpointerexception
			return null;
		
		StringTokenizer stk = new StringTokenizer(line);
		
		String denCollegio = stk.nextToken().trim();
		
		int numCampi = stk.countTokens();
		
		if (numPartiti != numCampi)
			throw new BadFileFormatException("numero di campi numerici diverso da numero partiti");
		
		int[] votiPartitiPerCollegio = new int[numPartiti];
		
		try {
			
			for (int i = 0; i < numPartiti; i++) {
				
				int votiPartito = Integer.parseInt(stk.nextToken());
				votiPartitiPerCollegio[i] = votiPartito;
				
			}
			
		}
		catch (NumberFormatException e) {
			
			throw new BadFileFormatException("campi numerici non sono numeri", e);
			
		}
		
		SortedSet<Partito> setPartiti = new TreeSet<Partito>();
		
		for (int i = 0; i< numPartiti; i++)
			setPartiti.add(new Partito(nomiPartiti[i], votiPartitiPerCollegio[i]));
		
		Collegio col = new Collegio(denCollegio, setPartiti);
		
		return col;
		
	}

}
