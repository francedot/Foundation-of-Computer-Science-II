package raptor.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import raptor.model.Gate;
import raptor.model.Transit;

public class MyTransitReader implements TransitReader {

	@Override
	public List<Transit> readTransits(Reader reader) throws IOException,
			MalformedFileException {
		// A14-BO1-N IN ED999AP 10/01/2012 12.34.56
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Transit> transitList = new ArrayList<Transit>();
		Transit transit;
		
		while ((transit = readTransit(bufferedReader)) != null) {
			
			transitList.add(transit);
			
		}
		 
		return transitList;
	}

	private Transit readTransit(BufferedReader bufferedReader) throws IOException, MalformedFileException {
		// TODO A14-BO1-N IN ED999AP 10/01/2012 12.34.56
		
		String line = bufferedReader.readLine();		
		if (line == null || line.trim().isEmpty())
			return null;
		
		StringTokenizer stk = new StringTokenizer(line, " ");
		
		if (stk.countTokens() != 5) {
			
			throw new MalformedFileException("numero token errato");
			
		}
		
		String section = stk.nextToken().trim();
		
		Gate gate = readGate(stk.nextToken().trim());
		
		String plate = stk.nextToken().trim();
		
		Date data = readData(stk.nextToken().trim(), stk.nextToken().trim());
		
		return new Transit(section, gate, data, plate);

	}

	private Date readData(String giornoMeseAnno, String oraMinutiSecondi) throws MalformedFileException {
		
		try {
		
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		
			Date rawDate = dateFormat.parse(giornoMeseAnno);
		
			GregorianCalendar cal = new GregorianCalendar();
		
			cal.setTime(rawDate);
		
			StringTokenizer tok = new StringTokenizer(oraMinutiSecondi, ".");
		
			cal.add(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(tok.nextToken().trim()));
			cal.add(GregorianCalendar.MINUTE, Integer.parseInt(tok.nextToken().trim()));
			cal.add(GregorianCalendar.SECOND, Integer.parseInt(tok.nextToken().trim()));
			
			return cal.getTime();
			
			
		} catch (ParseException pE) {
			
			throw new MalformedFileException("errore parsing data", pE);
			
		} catch (NumberFormatException nFE) {
			
			throw new MalformedFileException("errore conversione campo OraMinutiSecondi", nFE);
			
		} catch (NoSuchElementException nSEE) {
			
			throw new MalformedFileException("errore token non trovato", nSEE);
			
		}
	}

	private Gate readGate(String tipoGate) throws MalformedFileException {
		// IN
		
		try {
			
			return Gate.valueOf(tipoGate);
			
		} catch(IllegalArgumentException e) {
			
			throw new MalformedFileException("tag gate non valido", e);
			
		}
		
	}

}
