package gattovolpe.holidays.persistence;

import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;
import gattovolpe.utils.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class MyPackageReader implements PackageReader {

	private final String startTag = "STARTPACKAGE";
	private final String endTag = "ENDPACKAGE";
	
	private BufferedReader innerReader;
	
	public MyPackageReader(Reader reader) {
		
		if (reader == null)
			throw new IllegalArgumentException("reader = null");
		
		innerReader = new BufferedReader(reader);
		
	}

	@Override
	public List<Pacchetto> readPackages() throws InvalidPackageFormatException {

		ArrayList<Pacchetto> pacchettiList = new ArrayList<Pacchetto>();
		
		Pacchetto pacchetto;
		
		while ((pacchetto = readPacchetto()) != null)
		{
			pacchettiList.add(pacchetto);
		}
		return pacchettiList;
		
	}

	private Pacchetto readPacchetto() throws InvalidPackageFormatException {

	/** STARTPACKAGE ANTIGUA0 SOLOVOLO
		ANTIGUA, n/a
		01/08/2012, 0 giorni
		EUR 1417
		ANTIGUA  VOLO DA MXP +  JOLLY BEACH RESORT & SPA ***S + AI + TRF (CAMERA STANDARD VISTA MARE)
		ENDPACKAGE
		STARTPACKAGE ANTIGUA1 VOLOHOTEL
		ANTIGUA, n/a
		01/08/2012, 9 giorni
		EUR 1357
		ANTIGUA 9 GIORNI VOLO DA MXP + HOTEL JOLLY BEACH RESORT & SPA ***S + AI + TRF (CAMERA SUPERSAVER)
		ENDPACKAGE */
		
		try {
			
			String line = innerReader.readLine();
			
			if (line == null || line.isEmpty())
				return null;
			
			StringTokenizer stk = new StringTokenizer(line, " ");
			
			if (!stk.nextToken().trim().equals(startTag))
				throw new InvalidPackageFormatException("manca tag inizio");
			
			String id = stk.nextToken();
			
			if (!stk.hasMoreTokens())
				throw new InvalidPackageFormatException("manca tipo");
			
			TipoPacchetto tipo = readTipo(stk.nextToken());
			
			line = innerReader.readLine();
			
			Destinazione dest = readDestinazione(line);
			
			line = innerReader.readLine();
			
			stk = new StringTokenizer(line, ",");
			
			Date dataInizio = readData(stk.nextToken());
			
			int durata = readDurata(stk.nextToken());
			
			line = innerReader.readLine();
			
			double costo = readCosto(line);
			
			StringBuilder sb = new StringBuilder();
			
			while (!(line = innerReader.readLine()).contains(endTag)) {
				
				sb.append(line.trim() + "\n");
				
			}
			
			String descrizione = sb.toString();	
			
			Pacchetto pacchetto = new Pacchetto();
			pacchetto.setNome(id);
			pacchetto.setTipo(tipo);
			pacchetto.setDestinazione(dest);
			pacchetto.setDataInizio(dataInizio);
			pacchetto.setDurataGiorni(durata);
			pacchetto.setCosto(costo);
			pacchetto.setDescrizione(descrizione);
			
			return pacchetto;
			
			
		} catch (IOException e) {
			
			throw new InvalidPackageFormatException("errore di I/O");
			
		}
		
	}

	private double readCosto(String line) throws InvalidPackageFormatException {

		//EUR 1357
		StringTokenizer tok = new StringTokenizer(line, " ");
		
		if (!tok.nextToken().trim().equals("EUR"))
			throw new InvalidPackageFormatException("manca tag 'EUR");
		
		double costo = 0;
		try {
			
			costo = Double.parseDouble(tok.nextToken().trim());
			
		} catch(NumberFormatException e) {
			
			throw new InvalidPackageFormatException("errore conversione numero");
			
		}
		
		return costo;
		
	}

	private int readDurata(String dur) throws InvalidPackageFormatException {
		//  0 giorni
		
		StringTokenizer tok = new StringTokenizer(dur, " ");
		
		int durata = 0;
		try {
			
			durata = Integer.parseInt(tok.nextToken().trim());
			
		} catch(NumberFormatException e) {
			
			throw new InvalidPackageFormatException("errore conversione numero");
			
		}
		
		if (!tok.nextToken().trim().equals("giorni"))
			throw new InvalidPackageFormatException("manca tag giorni");
		
		return durata;
		
	}

	private Date readData(String data) throws InvalidPackageFormatException {
		// 01/08/2012
		
		return DateUtil.parseDate(data.trim());
		
	}

	private Destinazione readDestinazione(String line) throws InvalidPackageFormatException {
		// ANTIGUA, n/a
		
		String[] dest = line.split(",");
		
		if (dest.length != 2)
			throw new InvalidPackageFormatException("errore formattazione destinazione");
		
		Destinazione destinazione = new Destinazione();
		
		destinazione.setStato(dest[0].trim());
		
		destinazione.setLuogo(dest[1].trim());
		
		return destinazione;
		
	}

	private TipoPacchetto readTipo(String tipo) throws InvalidPackageFormatException {

		for (TipoPacchetto t : TipoPacchetto.values()) {
			
			if (t.toString().equals(tipo.trim()))
				return t;
			
		}
		
		throw new InvalidPackageFormatException("errore tipo");
		
	}

}
