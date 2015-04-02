package weather.persistence.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import weather.model.PunctualForecast;
import weather.persistence.ForecastException;

public class MyForecastReader implements ForecastReader {

	private final String[] weatherType = {"neve", "tempesta", "pioggia",
											"nebbia", "nuvoloso", "variabile", "sereno"};
	
	
	@Override
	public Map<String, SortedSet<PunctualForecast>> readAll(Reader reader)
			throws IOException, ForecastException {
	
		
		Map<String, SortedSet<PunctualForecast>> map = new HashMap<String, SortedSet<PunctualForecast>>();
		BufferedReader buf = new BufferedReader(reader);
		PunctualForecast pf;

		while ((pf = readForecastPuntuali(buf)) != null) {
		    SortedSet<PunctualForecast> set = map.get(pf.getPlace());
		    if (set != null) { // yes: then, add the punctual forecast to the city's forecasts
			set.add(pf);
		    } else // no, it's the first entry for that city: add the whole new entry
		    {
			set = new TreeSet<PunctualForecast>();
			set.add(pf);
			map.put(pf.getPlace(), set);
		    }
		}

		buf.close();
		return map;
	
		
		
	}

	private PunctualForecast readForecastPuntuali(BufferedReader bufferedReader) throws IOException, ForecastException {
		
		//Bologna; 12/11/2011; 14.00; 27°; 58%; tempesta
		
		String line = bufferedReader.readLine();	
		
		if (line == null || line.trim().isEmpty())
			return null;

		StringTokenizer stk = new StringTokenizer(line, ";");
		
		if (stk.countTokens() != 6) {
			
			throw new ForecastException("numero token errato");
			
		}
		
		String citta = stk.nextToken().trim();
		
		Date data = leggiData(stk.nextToken().trim(), stk.nextToken().trim());
		
		float temp = leggiTemperatura(stk.nextToken().trim());
		
		int umidita = leggiUmidita(stk.nextToken().trim());
		
		String weather = stk.nextToken().trim();
		
		boolean corretto = false;
		for (int i = 0; i < weatherType.length; i++) {
			
			if (weatherType[i].equals(weather))
				corretto = true;
			
		}
		
		if (!corretto)
			throw new ForecastException();
		
		return new PunctualForecast(citta, data, temp, umidita, weather);
		
	}

	private int leggiUmidita(String trim) throws ForecastException {
		
		try {
			
			return Integer.parseInt(trim.substring(0, trim.indexOf("%") - 1));
			
		} catch(IndexOutOfBoundsException e) {
			
			throw new ForecastException("");
			
		} catch (NumberFormatException e) {
			
			throw new ForecastException("errore parsing umidita");
			
		}
		
	}

	private float leggiTemperatura(String trim) throws ForecastException {
		// TODO 18°
		
		try {
			
			return Float.parseFloat(trim.substring(0, trim.indexOf("°") - 1));
			
		} catch(IndexOutOfBoundsException e) {
			
			throw new ForecastException("");
			
		} catch (NumberFormatException e) {
			
			throw new ForecastException("errore parsing temperatura");
			
		}
		
	}


	private Date leggiData(String GMA, String OM) throws ForecastException {
		
			try {
			StringTokenizer tok = new StringTokenizer(GMA, "/");
			GregorianCalendar cal = new GregorianCalendar();
			
			int giorno = Integer.parseInt(tok.nextToken().trim());
			int mese = Integer.parseInt(tok.nextToken().trim());
			int anno = Integer.parseInt(tok.nextToken().trim());
			
			tok = new StringTokenizer(OM, ".");
			
			int ore = Integer.parseInt(tok.nextToken().trim());
			int minuti = Integer.parseInt(tok.nextToken().trim());
			
			boolean oraCorretta = giorno > 0 && giorno <= 31 && mese> 0 && mese <= 12 &&
									anno >= 0 && anno <= 9999 && ore >= 0 && ore < 60 && minuti >= 0 && minuti < 60;
			
			if (!oraCorretta)
				throw new ForecastException();
			
			
			cal.set(GregorianCalendar.DATE, giorno);
			cal.set(GregorianCalendar.MONTH, mese);
			cal.set(GregorianCalendar.YEAR, anno);
			cal.set(GregorianCalendar.HOUR_OF_DAY, ore);
			cal.set(GregorianCalendar.MINUTE, minuti);
			cal.set(GregorianCalendar.SECOND, 0);
			cal.set(GregorianCalendar.MILLISECOND, 0);
			
			
			return cal.getTime();
			}
			catch (NumberFormatException e) {
				throw new ForecastException();
			}
			
	}

}
