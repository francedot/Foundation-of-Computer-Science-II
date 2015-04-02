package weather.model;

import java.text.DateFormat;
import java.util.Date;

public class PunctualForecast implements Forecast, Comparable<PunctualForecast> {
	private String place;
	private Date date;
	private float temperature;
	private int umidity;
	private String weather;

	public PunctualForecast(String place, Date date, float temp, int umidity,
			String weather) {
		this.place = place;
		this.date = date;
		this.temperature = temp;
		this.umidity = umidity;
		this.weather = weather;

	}

	public String getPlace() {
		return place;
	}

	public Date getDate() {
		return date;
	}

	public float getTemperature() {
		return temperature;
	}

	public int getUmidity() {
		return umidity;
	}

	public String getWeather() {
		return weather;
	}

	public boolean equals(Object o) {
		if (!(o instanceof PunctualForecast)) {
			return false;
		}
		PunctualForecast p = (PunctualForecast) o;

		return place.equalsIgnoreCase(p.getPlace()) && date.equals(p.getDate())
				&& (temperature == p.getTemperature())
				&& (umidity == p.getUmidity())
				&& weather.equalsIgnoreCase(p.getWeather());
	}

	public int compareTo(PunctualForecast pf) {
		if (this.equals(pf))
			return 0;
		else
			return place.equalsIgnoreCase(pf.getPlace()) ? date.compareTo(pf
					.getDate()) : place.compareToIgnoreCase(pf.getPlace());
	}

	@Override
	public String toString() {
		DateFormat dateFormatter = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.SHORT);

		return place + " " + dateFormatter.format(date) + " temperatura: "
				+ temperature + "¡" + " grado di umidità: " + umidity + "% "
				+ "tempo previsto: " + weather + "\n";

	}

}
