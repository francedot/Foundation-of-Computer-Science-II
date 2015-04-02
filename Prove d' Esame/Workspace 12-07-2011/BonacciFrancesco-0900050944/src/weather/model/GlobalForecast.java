package weather.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * sintetizzata a partire dalle previsioni puntuali per 
 * tale località nel giorno dato
 * 
 * @author Francesco
 *
 */
public class GlobalForecast implements Forecast {
	
	//1=neve, 2=tempesta, 3=pioggia, 4=nebbia, 5=nuvoloso, 6=variabile, 7= sereno
	
	private final String[] weatherType = {"Nothing", "neve", "tempesta", "pioggia", "nebbia", "nuvoloso", "variabile", "sereno"};
	
	private Date date;
	private float maxTemp;
	private float minTemp;
	private String place;
	private String weather;
	
	private HashMap<Integer, String>mappaValueToWeather;
	private HashMap<String, Integer>mappaWeatherToValue;
	
	public static Collection<GlobalForecast> calcGlobalForecast(SortedSet<PunctualForecast> forecasts) {	//previsioni puntuali relative a più giorni ma per stessa città

		
		ArrayList<GlobalForecast> globals = new ArrayList<GlobalForecast>();
		TreeSet<PunctualForecast> forecastPuntualiAlGiorno = new TreeSet<PunctualForecast>();
		
		Date dataGiornoCorrente = forecasts.first().getDate();
		
		for (PunctualForecast p : forecasts) {
			
			if (sameDate(p.getDate(), dataGiornoCorrente)) {
				
				forecastPuntualiAlGiorno.add(p);
				
				if (p == forecasts.last()) {
					
					globals.add(new GlobalForecast(forecastPuntualiAlGiorno));
			
				}
				
			} else if (!sameDate(p.getDate(), dataGiornoCorrente)) {
				
				globals.add(new GlobalForecast(forecastPuntualiAlGiorno));
				
				forecastPuntualiAlGiorno = new TreeSet<PunctualForecast>();//concluso i forecast per il vecchio giorno
				
				dataGiornoCorrente = p.getDate();
				
				forecastPuntualiAlGiorno.add(p);
				
			}
			
		}
		
		return globals;

	}

	@Override
	public Date getDate() {
		
		return date;
	
	}
	
	public float getMaxTemp() {
		
		return maxTemp;
		
	}

	public float getMinTemp() {
		
		return minTemp;
		
	}

	@Override
	public String getPlace() {
		
		return place;
		
	}

	@Override
	public String getWeather() {
		
		return weather;
		
	}

	private GlobalForecast(SortedSet<PunctualForecast> punctualForecastsPerDay) {

		mappaValueToWeather = new HashMap<Integer, String>();
		mappaWeatherToValue = new HashMap<String, Integer>();
		
		for (int i = 1; i <= 7; i++) {
			
			mappaValueToWeather.put(i, weatherType[i]);
			mappaWeatherToValue.put(weatherType[i], i);
				
		}
		
		ArrayList<PunctualForecast> arrayPunctualForecast = new ArrayList<PunctualForecast>(punctualForecastsPerDay);
		
		PunctualForecast first = arrayPunctualForecast.get(0);
		
		GregorianCalendar cal = new GregorianCalendar();
	
		Date dataFirst = first.getDate();
		cal.setTime(dataFirst);
		clearHMS(cal);
		
		this.date = cal.getTime();
		this.place = first.getPlace();
		
		maxTemp = Float.MIN_VALUE;
		minTemp = Float.MAX_VALUE;
		
		long timeFirstForecast = first.getDate().getTime();
		cal.setTime(first.getDate());
		clearHMS(cal);
		long endCurrentDay = cal.getTimeInMillis() + 24 * 3600000l;

		long arcoDiTempo = endCurrentDay - timeFirstForecast;	//faccio tutto in ms
		
		long sum = 0;
		
		for (int i = 0; i < arrayPunctualForecast.size(); i++) {
		
			PunctualForecast p = arrayPunctualForecast.get(i);
			
			if (p.getTemperature() > maxTemp) {
				
				maxTemp = p.getTemperature();
				
			}
			
			if (p.getTemperature() < minTemp) {
				
				minTemp = p.getTemperature();
				
			}
			
			if (i != punctualForecastsPerDay.size() - 1) {
				
				long durataMs = (arrayPunctualForecast.get(i+1).getDate().getTime() - arrayPunctualForecast.get(i).getDate().getTime());
				
				int weatherValueCurrent = mappaWeatherToValue.get(p.getWeather());
				
				sum += durataMs * weatherValueCurrent;	
				
			} else if (i == punctualForecastsPerDay.size() - 1) {
				
				long durataMs = (endCurrentDay - arrayPunctualForecast.get(i).getDate().getTime());	//durata diversa perchè fine!
				
				int weatherValueCurrent = mappaWeatherToValue.get(p.getWeather());
				
				sum += durataMs * weatherValueCurrent;	
				
			}
			
		}
		
		int weatherValue = Math.round(sum / arcoDiTempo);
		
		this.weather = mappaValueToWeather.get(weatherValue);	
		
	}

	private static void clearHMS(Calendar cal) {	//n.b calendario non modifica la data!!! però se gli passi il calendario conviene agire su di lui!!!
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
	}

	public static boolean sameDate(Date d1, Date d2) {//occhio con i metodi statici a non far puntare i riferimenti al altri spazi!!!
		
		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(d1);
		clearHMS(cal);
		long td1 = cal.getTimeInMillis();

		cal.setTime(d2);
		clearHMS(cal);
		long td2 = cal.getTimeInMillis();

		return td1 == td2;
		
	}

	@Override
	public String toString() {
		return "GlobalForecast [date=" + date + ", maxTemp=" + maxTemp
				+ ", minTemp=" + minTemp + ", place=" + place + ", weather="
				+ weather + ", forecasts="  +"]";
	}

	
	
	
	

}
