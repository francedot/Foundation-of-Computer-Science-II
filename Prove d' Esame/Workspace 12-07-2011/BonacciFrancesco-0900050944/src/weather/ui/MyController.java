package weather.ui;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import weather.model.Forecast;
import weather.model.GlobalForecast;
import weather.model.PunctualForecast;
import weather.model.WeatherIcon;
import weather.persistence.binary.WeatherIconLoader;

public class MyController implements Controller {
	private WeatherIconLoader loader;
	private Map<String, SortedSet<PunctualForecast>> mappa;

	public MyController(WeatherIconLoader loader, Map<String, SortedSet<PunctualForecast>> mappa){
		this.loader = loader;
		this.mappa = mappa;
	}

	@Override
	public Set<String> getCities() {
		return mappa.keySet();
	}

	@Override
	public SortedSet<Forecast> searchForecast(String city, Date date, boolean globale) {
		return globale ? searchGlobal(city, date) : searchPunctual(city, date);
	}

	private SortedSet<Forecast> searchPunctual(String city, Date date) {
		SortedSet<Forecast> pfList = new TreeSet<Forecast>();
		for (PunctualForecast pf : mappa.get(city)) {
			if (GlobalForecast.sameDate(date, pf.getDate())) {
				pfList.add(pf);
			}
		}
		return pfList;
	}

	private SortedSet<Forecast> searchGlobal(String city, Date date) {
		SortedSet<Forecast> gfList = new TreeSet<Forecast>();

		Collection<GlobalForecast> prev = new TreeSet<GlobalForecast>();
		prev = GlobalForecast.calcGlobalForecast(mappa.get(city));
		for (GlobalForecast gf : prev) {
			if (GlobalForecast.sameDate(date, gf.getDate())) {
				gfList.add(gf);
			}
		}
		return gfList;
	}

	public WeatherIcon getIcon(String name) {
		return loader.getIcon(name);
	}

}
