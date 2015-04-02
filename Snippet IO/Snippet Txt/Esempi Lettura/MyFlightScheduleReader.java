package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.DayOfWeek;
import flights.model.FlightSchedule;
import flights.model.SimpleTime;

public class MyFlightScheduleReader implements FlightScheduleReader{

	
	@Override
	public Collection<FlightSchedule> read(Reader reader,
			Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap)
			throws IOException, BadFileFormatException {
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<FlightSchedule> flightScheduleList = new ArrayList<FlightSchedule>();
		FlightSchedule flightschedule;
		
		while ((flightschedule = readFlightSchedule(bufferedReader, airportMap, aircraftMap)) != null)
		{
			flightScheduleList.add(flightschedule);
		}
		return flightScheduleList;
		
	}

	private FlightSchedule readFlightSchedule(BufferedReader reader,
			Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap) throws IOException, BadFileFormatException {
		
		FlightSchedule target = null;
		
		String line = reader.readLine();		
		if (line == null || line.trim().isEmpty())
			return null;

		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		if (tokenizer.countTokens() < 7)
			throw new BadFileFormatException("Pochi tokens");
		
		try {
		
			String airplaneID = tokenizer.nextToken();
		
			String aereoportoDiPartenza = tokenizer.nextToken();
		
			SimpleTime oraDiPartenza = new SimpleTime(Integer.parseInt(tokenizer.nextToken(".")), Integer.parseInt(tokenizer.nextToken(",")));
		
			String aereoportoDiArrivo = tokenizer.nextToken();
		
			SimpleTime oraDiArrivo = new SimpleTime(Integer.parseInt(tokenizer.nextToken(".")), Integer.parseInt(tokenizer.nextToken(",")));
		
			Set<DayOfWeek> giorniDiServizio = new HashSet<DayOfWeek>();
		
			String dayOfService = tokenizer.nextToken();
		
			for (int i = 0; i < dayOfService.length(); i++)
			{
				//verifico se l'indice(giorno) compare nella stringa
				//se compare lo aggiungo alla lista dei giorni di servizio grazie al metodo statico fromFlightDay
				//che mi restituisce un dayofweek a partire da un flightday(intero)
			
				if (dayOfService.substring(i, i).equals("-")) continue;	//controllo per non lanciare la numbformexc quando c'è -
				
				if (i == Integer.parseInt(dayOfService.substring(i, i)))
					giorniDiServizio.add(DayOfWeek.fromFlightDay(i));
				
			}
	
			String tipoAereoMobile = tokenizer.nextToken();
		
			//Mappa già create!!!
		
			//Inizializzazionr FileSchedule
		
			String code = airplaneID;
			
			Airport departureAirport = airportMap.get(aereoportoDiPartenza);
			
			SimpleTime departureLocalTime = oraDiPartenza;
			
			Airport arrivalAirport = airportMap.get(aereoportoDiArrivo);
			
			SimpleTime arrivalLocalTime = oraDiArrivo;
				
			Aircraft aircraft = aircraftMap.get(tipoAereoMobile);
			
			target = new FlightSchedule(code, departureAirport, departureLocalTime, arrivalAirport, arrivalLocalTime, giorniDiServizio, aircraft);
					
		
		}
		catch(NumberFormatException | NoSuchElementException | IndexOutOfBoundsException e) {
			throw new BadFileFormatException(e);
		}
			
		return target;
		
	}

}
