package mm.model;

import java.util.ArrayList;

public class MasterMindAlgorithm implements MatchingAlgorithm {

	private static final long serialVersionUID = 1L;

	@Override
	public Risposta match(Codice segreto, Codice tentativo) {
		
		if (segreto.getCount() != tentativo.getCount())
			throw new IllegalArgumentException();
		
		int coincidenti = 0;
		
		ArrayList<Colore> coloriTentativo =  new ArrayList<Colore>();
		
		for (int i = 0; i < tentativo.getCount(); i++) {
			
			coloriTentativo.add(tentativo.getColore(i));
			
			if (tentativo.getColore(i) == segreto.getColore(i))
				coincidenti++;
			
		}
		//-----coincidenti trovato
		
		for (int i = 0; i < segreto.getCount(); i++) {
			
			coloriTentativo.remove(segreto.getColore(i));
			
		}	
		
		int presenti = Configuration.LunghezzaCodice - coloriTentativo.size();
		
		int coincidentiEsclusi = presenti - coincidenti;
		
		return new Risposta(coincidenti, coincidentiEsclusi);
		
	}

}