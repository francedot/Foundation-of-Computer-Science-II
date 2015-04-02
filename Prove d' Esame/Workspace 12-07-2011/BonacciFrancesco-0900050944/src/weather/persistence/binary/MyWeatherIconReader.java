package weather.persistence.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.Map;

import weather.model.WeatherIcon;
import weather.persistence.WeatherIconException;

public class MyWeatherIconReader implements WeatherIconLoader {
	
	private ObjectInputStream innerStream;
	
	private Map<String, WeatherIcon> mappaNomeToIcon;
	
	@Override
	public WeatherIcon getIcon(String descrizione) {

		return mappaNomeToIcon.get(descrizione);
		
	}

	@SuppressWarnings("unchecked")
	public MyWeatherIconReader(InputStream inputStream) throws IOException, WeatherIconException {
		
		innerStream = new ObjectInputStream(inputStream);
		
		try {

			mappaNomeToIcon =  (Map<String, WeatherIcon>)innerStream.readObject();
		
		} catch (ClassNotFoundException e) {
		
			throw new WeatherIconException("Dati non validi", e);

		} catch (InvalidClassException e) {
		
			throw new WeatherIconException("Dati non validi", e);

		} finally {
			
			innerStream.close();
			
		}
		
	}

	
	
}
