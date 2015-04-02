package teethcollege.pianidistudi.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.PianiReader;

public class MyController implements Controller {
	
	private Map<Long, Insegnamento> mappaInsegnamenti;
	private List<PianoDiStudi> listaPianiDiStudio;
	
	public MyController(InsegnamentiReader insegnamentiReader,
			PianiReader pianiReader, UserInteractor userInteractor) {
		
		try {
			
			mappaInsegnamenti = insegnamentiReader.caricaInsegnamenti();
			insegnamentiReader.close();
			//dovresti anche chiuderli altrimenti il test verify non porta...
			
			listaPianiDiStudio = pianiReader.caricaPianiDiStudi(mappaInsegnamenti);
			pianiReader.close();
			//...
			
		} catch (IOException e) {
			
			userInteractor.showMessage(e.getMessage());
			userInteractor.shutDownApplication();
		}
		
		//Popolare le strutture dati necessarie -- caricare dati OK
		//una volta popolate verificare che i piani siano validi e scartare gli altri notificando utente
		//e terminando app
		
		/*
		ArrayList<PianoDiStudi> pianiScartati = new ArrayList<PianoDiStudi>();
		for (PianoDiStudi c : listaPianiDiStudio) {
			if (!c.isValid()) {
				pianiScartati.add(c);
			}
		}
		for (PianoDiStudi c : pianiScartati) {
			listaPianiDiStudio.remove(c);
		}
		if (pianiScartati.size() > 0) {
			userInteractor.showMessage("Piani di studio scartati: " + pianiScartati);
		}
		*/
		
		
		List<PianoDiStudi> listaPianiScartati = new ArrayList<PianoDiStudi>();
		
		boolean pianiScartati = false;
		
		for (PianoDiStudi plan : listaPianiDiStudio) {
			
			if(!plan.isValid()) {
				
				pianiScartati = true;
				
				//userInteractor.showMessage(plan + " non valido");
				
				int index = listaPianiDiStudio.indexOf(plan);
				
				PianoDiStudi removedPlan = listaPianiDiStudio.get(index);
				listaPianiDiStudio.remove(index);
				
				listaPianiScartati.add(removedPlan);
				
			}		
		}
		
		if (pianiScartati) {
			userInteractor.showMessage("Piani di studio scartati: " + listaPianiScartati);
		}
		
		
	}

	/**
	 * Il metodo sostituisci effettua la sostituzione, nel
	PianoDiStudi passato come primo argomento, dell’Insegnamento passato come secondo argomento con quello
	passato come terzo argomento; tale metodo restituisce una string che in caso di successo è null, mentre in caso
	d’errore contiene il messaggio d’errore relativo ottenuto catturando le opportune eccezioni (si veda
	l’implementazione del metodo sostituisciInsegnamento nel PianoDiStudi).
	 */
	@Override
	public String sostituisci(PianoDiStudi pianoDiStudi,
			Insegnamento daTogliere, Insegnamento daMettere) {
		
		try {
			pianoDiStudi.sostituisciInsegnamento(daTogliere, daMettere);
		}
		catch(IllegalArgumentException e) {
			return (e.getMessage());
		}
		
		return null;
	}

	@Override
	public Collection<PianoDiStudi> getPianiDiStudi() {
		
		return listaPianiDiStudio;
	}

	@Override
	public Collection<Insegnamento> getInsegnamenti() {
	
		return mappaInsegnamenti.values();
		
	}

}
