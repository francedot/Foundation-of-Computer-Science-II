package weather.persistence.text;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.SortedSet;

import weather.model.PunctualForecast;
import weather.persistence.ForecastException;

public interface ForecastReader {
	
	public Map<String, SortedSet<PunctualForecast>> readAll(Reader reader) throws IOException, ForecastException;

}
