package teethcollege.esami.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import teethcollege.model.Carriera;
import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.EsamiManager;

public class MyController extends Controller {

	private EsamiManager esamiManager;

	
	public MyController(String nomeFileInsegnamenti, String nomeFileCarriere,
			UserInteractor userInteractor, EsamiManager esamiManager) {
		super(nomeFileInsegnamenti, nomeFileCarriere, userInteractor);

		this.esamiManager = esamiManager;
		
	}

	@Override
	public Carriera aggiungiEsame(Insegnamento corso, String voto, String data,
			PianoDiStudi prescelto) {
		
		String matricola = prescelto.getMatricola();
		
		Esame nuovoEsame = getNuovoEsame(corso, voto, data);
			
		List<Esame> esamiSostenuti = getEsami(matricola);
		
		Carriera car = new Carriera(prescelto, esamiSostenuti);
		
		try {
			
			car.addEsame(nuovoEsame);
						
		}
		catch (IllegalArgumentException e) {
			
			getUserInteractor().showMessage("Impossibile aggiungere esame alla carriera");
			return car;
		}
		//successo
		
		salvaEsami(matricola, esamiSostenuti);
		
		return car;
		
	}

	@Override
	public List<Esame> getEsami(String matricola) {
		
		Reader reader = null;
		
		try {
			reader = new FileReader(matricola);
		} catch (FileNotFoundException e) {
			
			getUserInteractor().showMessage("Impossibile aprire file" + matricola);
			e.printStackTrace();
			return null;
		}
		
		List<Esame> esamiCaricati;
		
		try {
			esamiCaricati = esamiManager.caricaEsami(reader, getMappaInsegnamenti());
		} catch (IOException e) {
			
			getUserInteractor().showMessage("Impossibile caricare esami");
			e.printStackTrace();
			return null;
		}
		
		return esamiCaricati;
		
	}

	@Override
	protected void salvaEsami(String matricola, List<Esame> esami) {

		Writer writer;
		
		try {
			
			writer = new FileWriter(new File(matricola));
			esamiManager.salvaEsami(writer, esami);
			
		} catch (IOException e) {
			
			getUserInteractor().showMessage("Impossibile salvare su file txt");
			e.printStackTrace();

		}
	
	}
	
	public Esame getNuovoEsame(Insegnamento corso, String votoScelto, String dataScelta) {
		
		int voto = votoScelto.equals("30L") ? 30 : Integer.parseInt(votoScelto);
		if (voto < 1 || voto > 30) {
			getUserInteractor().showMessage("Voto fuori Range");
			return null;
		}
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		Date data = null;
		try {
			data = df.parse(dataScelta);
		} catch (ParseException e) {
			getUserInteractor().showMessage("Data errata o voto fuori Range");
			e.printStackTrace();
			return null;
		}
		
		return new Esame(corso, voto, data);
		
	}
	
}
