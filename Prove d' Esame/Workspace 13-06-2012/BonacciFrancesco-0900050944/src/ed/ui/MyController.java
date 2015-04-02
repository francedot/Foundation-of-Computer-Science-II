package ed.ui;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ed.model.Bolletta;
import ed.model.Tariffa;
import ed.model.Utente;
import ed.persistence.BadFileFormatException;
import ed.persistence.TariffaReader;

public class MyController implements Controller {
	
	private TariffaReader tariffaReader;
	private MessageManager messageManager;
	
	HashMap<String, Tariffa> mappaNomiToTariffe;
	
	@Override
	public Bolletta creaBolletta(String nomeTariffa, Utente utente, int mese,
			int anno, double consumo) {

		Tariffa tarCorrente = mappaNomiToTariffe.get(nomeTariffa);
		
		Bolletta bolletta = tarCorrente.creaBolletta(utente, mese, anno, consumo);
		
		return bolletta;
		
	}

	@Override
	public Collection<String> getNomiTariffe() {

		ArrayList<String> nomiTariffe = new ArrayList<String>();
		
		for (String nome : mappaNomiToTariffe.keySet()) {
				
			nomiTariffe.add(nome);

		}
		
		return nomiTariffe;
		
	}

	public void readTariffe(Reader reader) {
		
		Collection<Tariffa> tariffeLette = null;
		
		try {
			
			tariffeLette = tariffaReader.leggiTariffe(reader);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			messageManager.showMessage("errore nell' apertura del file");
			return;
			
		} catch (BadFileFormatException e) {
			
			e.printStackTrace();
			messageManager.showMessage("errore nella formattazione del file");
			return;
		}
		
		for (Tariffa t : tariffeLette) {
			
			mappaNomiToTariffe.put(t.getNome(), t);
			
		}
		
		
	}
	
	public MyController(TariffaReader tariffaReader,
			MessageManager messageManager) {

		this.tariffaReader = tariffaReader;
		this.messageManager = messageManager;
		
		this.mappaNomiToTariffe = new HashMap<String, Tariffa>();
		
	}

	
}
