package galliacapocciona.ui;

import galliacapocciona.model.CalcolatoreSeggi;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MyController extends Controller {

	public MyController(UserInteractor userInteractor) {
		super(userInteractor);
	
	}

	@Override
	public String[] getCalcolatoriSeggi() {
	
		//utilizzare metodo statico calcolatoreseggi
		
		return CalcolatoreSeggi.getCalcolatoriSeggi();
		
	}

	@Override
	public Map<String, Integer> calcola(String nomeCalcolatoreSeggi,
			int numeroSeggi) {

		//riassegnare i seggi sulla base dell'algoritmo selezionato
		//restituire una mappa nomePartitoToSeggi
		
		int numeroSeggiMin = getSeggiMinimi();
		if (numeroSeggi < numeroSeggiMin) {
			
			getUserInteractor().showMessage("numero di seggi disponibili minore di numero minimo seggi");
			return null;
		}
		
		CalcolatoreSeggi cs = null;
	
		try {
			
			cs = CalcolatoreSeggi.getInstance(nomeCalcolatoreSeggi);
			
		} catch (NoSuchAlgorithmException e) {
			
			getUserInteractor().showMessage("nome dell' algoritmo non valido");
			e.printStackTrace();
			return null;
			
		}
		
		try {
			
			return cs.assegnaSeggi(numeroSeggi, getListaCollegi());
			
		}
		catch(Exception e) {	//eccezzioni eventuali residue
			
			getUserInteractor().showMessage(e.getMessage());
			return null;
			
		}
		
	}

}
