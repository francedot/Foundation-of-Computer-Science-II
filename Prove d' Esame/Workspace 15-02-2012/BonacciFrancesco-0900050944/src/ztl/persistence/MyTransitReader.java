package ztl.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import ztl.model.Direction;
import ztl.model.Transit;

public class MyTransitReader implements TransitReader {

	@Override
	public List<Transit> read(Reader reader) throws IOException,
			MalformedFileException {
		// TODO ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP 10/01/2012 14.20\n
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Transit> transitList = new ArrayList<Transit>();
		Transit transit;
		
		while ((transit = readTransit(bufferedReader)) != null) {
			
			transitList.add(transit);
			
		}
		
		return transitList;
	
	}

	private Transit readTransit(BufferedReader bufferedReader) throws MalformedFileException, IOException {
		// TODO ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP 10/01/2012 14.20\n
		//ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP10/01/2012 14.20\n
		
		String line = bufferedReader.readLine();		
		
		if (line == null || line.trim().isEmpty())
			return null;

		StringTokenizer stk = new StringTokenizer(line, " \t");
		
		String gateName = stk.nextToken().trim();
		
		Direction direzione = leggiDirezione(stk.nextToken().trim());
		
		String plate = stk.nextToken().trim();
		
		if (stk.countTokens() != 2)
			throw new MalformedFileException("errore formattazione data");
		
		Date data = leggiData(stk.nextToken().trim(), stk.nextToken().trim());
		
		return new Transit(gateName, direzione, data, plate);
		
	}

	private Date leggiData(String gma, String om) throws MalformedFileException {
		
		// TODO 10/01/2012 12.34
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		Date rawDate;
		
		try {
			
			rawDate = dateFormat.parse(gma);
			
		} catch (ParseException e) {
			
			throw new MalformedFileException("impossibile convertiregiorno/mese/anno");
			
		}
		
		StringTokenizer tok = new StringTokenizer(om, ".");
		
		int ore, minuti;
		
		try {
			
			ore = Integer.parseInt(tok.nextToken());
			minuti = Integer.parseInt(tok.nextToken());
			
		} catch (NumberFormatException e) {
			
			throw new MalformedFileException("impossibile convertire ore.minuti");
			
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(rawDate);
		cal.add(GregorianCalendar.HOUR, ore);
		cal.add(GregorianCalendar.MINUTE, minuti);
		
		Date date = cal.getTime();
		
		return date;
	}

	private Direction leggiDirezione(String tokenDirezione) throws MalformedFileException {
		// TODO entrata
		
		if (tokenDirezione.equals("entrata")) {
			
			return Direction.ENTRY;
			
		} else if (tokenDirezione.equals("uscita")) {
			
			return Direction.EXIT;
			
		} else	throw new MalformedFileException("direzione non valida");
		
		
	}

}
