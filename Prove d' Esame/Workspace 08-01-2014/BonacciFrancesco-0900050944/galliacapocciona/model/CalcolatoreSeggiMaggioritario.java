package galliacapocciona.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcolatoreSeggiMaggioritario extends CalcolatoreSeggi {
	
	private Map<String, Integer> nomePartitoToSeggi;
	
	public CalcolatoreSeggiMaggioritario() {

		nomePartitoToSeggi = new HashMap<String, Integer>();
		
	}

	@Override
	public Map<String, Integer> assegnaSeggi(int seggiDaAssegnare,
			List<Collegio> listaCollegi) {
		
		int numCollegi = listaCollegi.size(); //num collegi = num seggi da assegnare
		
		if (seggiDaAssegnare != numCollegi)
			throw new IllegalArgumentException("numero di seggi da assegnare diverso da numero di collegi");
		
		List<Partito> totPartiti = Collegio.generaListaPartiti(listaCollegi);
		
		List<String> elencoNomiPartiti = new ArrayList<String>();
		
		for(Partito p : totPartiti)	
			elencoNomiPartiti.add(p.getNome());

		//inizializzare la mappa -- all' inizio 0 seggi per tutti partiti -- OK
		//poi aggiungi 1 al numero di seggi per quel partito quando compare come vincitore
		//nel collegio
		
		for (String nomeP: elencoNomiPartiti)
			nomePartitoToSeggi.put(nomeP, 0);

		for (Collegio c : listaCollegi) {
			
			for (String nomeP: elencoNomiPartiti) {
				
				if (nomeP.equals(c.getVincitore().getNome())) {	//occhio a equals tra oggetti!!!! deve essre tra nomi!!!!
					
					//prendo il valore che c' era già per quella chiave -- cioè i voti per quel nomePartito
					int currentVal = nomePartitoToSeggi.get(nomeP);

					nomePartitoToSeggi.put(nomeP, currentVal + 1);
					
				}
				
			}
			
		}
		
		//vedo quanti sono i seggi -- vedo quali sono i vincitori per ogni collegio -- 
		
		
		//n.b non limitarti a ritornare!!
		return nomePartitoToSeggi;
	}

}
