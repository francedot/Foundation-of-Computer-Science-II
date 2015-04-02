package galliacapocciona.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalcolatoreSeggiMisto extends CalcolatoreSeggi {

	private Map<String, Integer> nomePartitoToSeggi;
	
	public CalcolatoreSeggiMisto() {

		nomePartitoToSeggi = new HashMap<String, Integer>();
		
	}

	@Override
	public Map<String, Integer> assegnaSeggi(int seggiDaAssegnare,
			List<Collegio> listaCollegi) {
		
		int numCollegi = listaCollegi.size();
		int seggiRimanenti = seggiDaAssegnare - numCollegi;
		
		if (seggiDaAssegnare < numCollegi)
			throw new IllegalArgumentException("numero di seggi da assegnare minore di numero di collegi");
		
		//seggiDaAssegnare = n + m dove n = numCollegi e m sono i rimanenti seggi da assegnare con metodo proporzionale
		//assegno i primi (n = numCollegi) seggi con il metodo maggioritario --
		//
		
		if (seggiDaAssegnare == numCollegi) {
			
			CalcolatoreSeggiMaggioritario cM = new CalcolatoreSeggiMaggioritario();
			Map<String, Integer> nomePartitoToSeggiMag = cM.assegnaSeggi(numCollegi, listaCollegi);
			nomePartitoToSeggi = nomePartitoToSeggiMag;
			
			return nomePartitoToSeggiMag;

		}
		else {
		
			CalcolatoreSeggiMaggioritario cM = new CalcolatoreSeggiMaggioritario();
			Map<String, Integer> nomePartitoToSeggiMag = cM.assegnaSeggi(numCollegi, listaCollegi);
		
			CalcolatoreSeggiProporzionale cP = new CalcolatoreSeggiProporzionale();
			Map<String, Integer> nomePartitoToSeggiProp = cP.assegnaSeggi(seggiRimanenti, listaCollegi);
		
			Set<String> elencoNomiPartiti = nomePartitoToSeggiMag.keySet();
		
			for (String nomeP : elencoNomiPartiti) {
			
				int votiM = nomePartitoToSeggiMag.get(nomeP);
				int votiP = nomePartitoToSeggiProp.get(nomeP);
				int votiTot = votiM + votiP;
			
				nomePartitoToSeggi.put(nomeP, votiTot);
					
			}
			
			return nomePartitoToSeggi;
			
		}
		
	}

}
