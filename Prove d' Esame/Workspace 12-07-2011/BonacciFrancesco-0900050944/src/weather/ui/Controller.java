package weather.ui;

import java.util.Date;
import java.util.Set;

import java.util.SortedSet;

import weather.model.Forecast;
import weather.model.WeatherIcon;

public interface Controller {

	public Set<String> getCities();
	public SortedSet<Forecast> searchForecast(String city, Date date, boolean globalRequired);
	public WeatherIcon getIcon(String name);
	
}
