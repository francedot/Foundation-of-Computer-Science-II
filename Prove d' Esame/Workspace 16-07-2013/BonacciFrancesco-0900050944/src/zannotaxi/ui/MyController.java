package zannotaxi.ui;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.Tassametro;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.CorseTaxiReader;
import zannotaxi.persistence.DataNotReadyException;
import zannotaxi.persistence.TariffaTaxiReader;

public class MyController extends AbstractController implements Controller {
	
	public MyController(TariffaTaxiReader tariffaReader,
			CorseTaxiReader corseReader, InputStream inputStream) throws BadFileFormatException, IOException {
		
		super(tariffaReader, corseReader);
	
		corseReader.leggiCorse(inputStream);

	}
	
	//costruttore accessorio da non modificare
	public MyController(CorseTaxiReader corseReader, Tassametro tassametro)
			throws BadFileFormatException {
		super(corseReader, tassametro);

		
	}

	//restituisce l’elenco delle descrizioni delle corse taxi caricate
	@Override
	public String[] getDescrizioniCorse() {
		
		List<CorsaTaxi> listaCorse = null;
		
		try {
			
			listaCorse = corseReader.getCorse();
			
		}
		catch (DataNotReadyException e) {
			return null;
		}
		
		String[] descrizioniCorse = new String[listaCorse.size()];
		
		int i = 0;
		for (CorsaTaxi c : listaCorse) {
			
			descrizioniCorse[i] = c.getDettagliCorsa();
			i++;
		}
		
		return descrizioniCorse;
		
	}

	@Override
	public CorsaTaxi getCorsaPerDescrizione(String descrizioneCorsa) {
		
		List<CorsaTaxi> listaCorse = null;
		
		try {
			
			listaCorse = corseReader.getCorse();
			
		}
		catch (DataNotReadyException e) {
			
			return null;
			
		}
		
		for (CorsaTaxi c : listaCorse) {
			
			if (c.getDettagliCorsa().equals(descrizioneCorsa))
				return c;
			
		}

		return null;
		
		
	}

	
	//una matrice di stringhe (ossia una sorta di tabella a due
	//colonne di stringhe) in cui la prima colonna contiene una descrizione e 
	//l'altra il relativo valore
	@Override
	public String[][] getLineeDiCosto(CorsaTaxi corsa) {
		
		NumberFormat nF = NumberFormat.getNumberInstance();
		nF.setMaximumFractionDigits(2);
		nF.setMinimumFractionDigits(2);
		
		String distanzaPercorsa = nF.format((corsa.getRilevazioniDistanze()[corsa.getRilevazioniDistanze().length-1] / 1000));
		
		String costo = nF.format(tassametro.calcolaCosto(corsa));
		
		String[][] tabella = new String[][]{{"Corsa", corsa.getDettagliCorsa()},
				{"Orario", corsa.getOraPartenza().toString()}, 
					{"Distanza Km", distanzaPercorsa}, {"Costo", costo}};
		
		return tabella;
		
	}

}
