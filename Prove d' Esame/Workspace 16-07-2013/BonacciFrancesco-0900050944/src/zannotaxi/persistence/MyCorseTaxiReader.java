package zannotaxi.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.SimpleTime;

public class MyCorseTaxiReader implements CorseTaxiReader {

	boolean datiPronti = false;
	List<CorsaTaxi> listaCorse;
	
	@Override
	public List<CorsaTaxi> getCorse() throws DataNotReadyException {

		if (!datiPronti)
			throw new DataNotReadyException();
		else
			return listaCorse;
		
	}

	@Override
	public void leggiCorse(InputStream stream) throws BadFileFormatException,
			IOException {
		
		listaCorse = new ArrayList<CorsaTaxi>();
		
		ObjectInputStream ois = new ObjectInputStream(stream);
		
		try {
			
			int numeroCorse = ois.readInt();
			
			for (int i =0; i < numeroCorse; i++) {
				
				int ore = ois.readInt();
				int min = ois.readInt();
				SimpleTime oraPartenza = new SimpleTime(ore, min);
				
				String descrizione = (String) ois.readObject();
				
				double[] listaRilevazioni = (double[]) ois.readObject();
				
				listaCorse.add(new CorsaTaxi(descrizione, oraPartenza, listaRilevazioni));
			}
			
		}		
		catch (Exception e) {	//op.logica -- se si incontra un qualunque eccezione i dati non sono pronti!
		
			datiPronti = false;
			throw new BadFileFormatException(e);
			
		}
		
		datiPronti = true;

	}

}
